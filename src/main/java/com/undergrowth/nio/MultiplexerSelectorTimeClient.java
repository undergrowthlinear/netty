package com.undergrowth.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 客户端多路复用器 用于监听连接、监听可读事件
 * 
 * @author Administrator
 * 
 */
public class MultiplexerSelectorTimeClient implements Runnable {

	// 多路复用器
	private Selector selector;
	private SocketChannel socketChannel;
	private int port;

	public MultiplexerSelectorTimeClient(int port) throws IOException {
		// TODO Auto-generated constructor stub
		// 打开多路复用器
		selector = Selector.open();
		// 打开通道
		socketChannel = SocketChannel.open();
		// 配置为非阻塞模式
		socketChannel.configureBlocking(false);
		this.port = port;
	}

	public void run() {
		// TODO Auto-generated method stub

		try {
			// 连接
			doConnect();
			// 控制循环
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));
			while (true) {
				/*System.out.println("是否还要进行");
				String isGoString = reader.readLine();
				if ("N".equalsIgnoreCase(isGoString))
					break;
				System.out.println("等待事件源发生");*/
				System.out.println("客户端等待事件源发生");
				int readyChannel = selector.select(1000);
				if (readyChannel == 0)
					continue;
				System.out.println("有" + readyChannel + "个准备好了");
				// 获取准备好的通道
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> selectKeyIterator = selectionKeys
						.iterator();
				while (selectKeyIterator.hasNext()) {
					SelectionKey selectionKey = (SelectionKey) selectKeyIterator
							.next();
					//遍历注册中准备好的事件源  
	                interestSet(selectionKey.interestOps()); 
					// 处理发生的事件源
					handlerEvent(selectionKey);
					// 移除已经处理的事件源
					selectKeyIterator.remove();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * 处理时间源的事件
	 * 
	 * @param selectionKey
	 * @throws IOException
	 */
	private void handlerEvent(SelectionKey selectionKey) throws IOException {
		// TODO Auto-generated method stub
		if (selectionKey.isValid()) {
			if (selectionKey.isConnectable()) {// 表示客户端连接上服务器 注册可读事件
				SocketChannel socketChannel = (SocketChannel) selectionKey
						.channel();
				if (socketChannel.finishConnect()) {//表示完成了连接
					socketChannel.register(selector, SelectionKey.OP_READ);
					socketChannel.configureBlocking(false);
					doWrite(socketChannel, "现在时间是多少");
				}
			}
			if (selectionKey.isReadable()) {// 表示服务器有数据过来 处理数据
				SocketChannel socketChannel = (SocketChannel) selectionKey
						.channel();
				ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
				int bytesRead = socketChannel.read(byteBuffer);
				if (bytesRead > 0) {// 表示读到了数据
					// 将缓冲区的模式转换
					byteBuffer.flip();
					// 读取缓存区的数据
					byte[] dst = new byte[byteBuffer.remaining()];
					byteBuffer.get(dst);
					System.out.println("服务器的数据为:" + new String(dst));
				} else if (bytesRead < 0) {// 关闭资源
					selectionKey.cancel();
					socketChannel.close();
					
				}
			}
		}
	}

	/**
	 * 连接客户端
	 * 
	 * @throws IOException
	 */
	private void doConnect() throws IOException {
		// TODO Auto-generated method stub
		boolean flag = socketChannel.connect(new InetSocketAddress(InetAddress
				.getLocalHost(), port));
		System.out.println("是否连接到服务器" + flag);
		if (flag) { // 如果连接成功
					// 注册可读事件
					// 发送数据
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel, "现在时间是多少");
		} else { // 没有连接的话 则注册连接事件
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}

	/**
	 * 向服务器写信息
	 * 
	 * @param socketChannel
	 * @param content
	 * @throws IOException
	 */
	private void doWrite(SocketChannel socketChannel, String content)
			throws IOException {
		// TODO Auto-generated method stub
		byte[] cont = content.getBytes();
		ByteBuffer src = ByteBuffer.allocate(cont.length);
		src.put(cont);
		src.flip();//put相当于读模式  position位于写入的字节数目  limit为capacity  当写的时候 需要翻转
		socketChannel.write(src);
	}
	
	 /** 
     * 遍历注册中准备好的事件源 
     * @param interestOps 
     */  
    private void interestSet(int interestSet) {  
        // TODO Auto-generated method stub  
        if((interestSet&SelectionKey.OP_ACCEPT)!=0) System.out.println("注册的可接受");  
        if((interestSet&SelectionKey.OP_CONNECT)!=0) System.out.println("注册的可连接");  
        if((interestSet&SelectionKey.OP_READ)!=0) System.out.println("注册的可读");  
        if((interestSet&SelectionKey.OP_WRITE)!=0) System.out.println("注册的可写");  
    }  
}
