package com.undergrowth.nio;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * 多通道复用器 将通道进行注册,多路复用器监听两个端口的连接
 * 
 * @author Administrator
 * 
 */
public class MultiplexerSelectorTimeServer implements Runnable {

	// 多路复用器
	private Selector selector;
	private ServerSocketChannel serverSocketChannel1, serverSocketChannel2;

	public MultiplexerSelectorTimeServer(int port1, int port2)
			throws IOException {
		// TODO Auto-generated constructor stub
		// 打开选择器
		selector = Selector.open();
		// 打开通道
		serverSocketChannel1 = ServerSocketChannel.open();
		// 绑定端口
		serverSocketChannel1.socket().bind(new InetSocketAddress(port1));
		// 设置为非阻塞模式
		serverSocketChannel1.configureBlocking(false);
		// 将通道注册到多路复用器
		serverSocketChannel1.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("已经注册到" + port1 + ",等待服务");
		// 第二个端口
		// 打开通道
		/*serverSocketChannel2 = ServerSocketChannel.open();
		// 绑定端口
		serverSocketChannel2.socket().bind(new InetSocketAddress(port2));
		// 设置为非阻塞模式
		serverSocketChannel2.configureBlocking(false);
		// 将通道注册到多路复用器
		serverSocketChannel2.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("已经注册到" + port2 + ",等待服务");*/
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			// 控制循环
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));
			while (true) {
				/*System.out.println("是否还要进行");
				String isGoString = reader.readLine();
				if ("N".equalsIgnoreCase(isGoString))
					break;*/
				System.out.println("服务器等待事件源发生");
				// 等待注册的事件源发生
				int readyChannel = selector.select(1000);
				if (readyChannel == 0)
					continue;
				System.out.println("有" + readyChannel + "个准备好了");
				// 获取准备好的通道
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> selectKeyIterator = selectionKeys.iterator();
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
	 * @param selectionKey
	 * @throws IOException
	 */
	private void handlerEvent(SelectionKey selectionKey) throws IOException {
		// TODO Auto-generated method stub
		if(selectionKey.isValid()){
			if(selectionKey.isAcceptable()){//表示有客户端连接过来 注册客户端的可读事件
				ServerSocketChannel serverSocketChannel=(ServerSocketChannel) selectionKey.channel();
				SocketChannel socketChannel=serverSocketChannel.accept();
				socketChannel.configureBlocking(false);
				socketChannel.register(selector, SelectionKey.OP_READ);
			}
			if(selectionKey.isReadable()){//表示客户端有数据过来 处理数据
				SocketChannel socketChannel=(SocketChannel) selectionKey.channel();
				ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
				int bytesRead=socketChannel.read(byteBuffer);
				if(bytesRead>0){//表示读到了数据
					//将缓冲区的模式转换
					byteBuffer.flip();
					//读取缓存区的数据
					byte[] dst=new byte[byteBuffer.remaining()];
					byteBuffer.get(dst);
					System.out.println("客户端的数据为:"+new String(dst));
					//会写返回信息给客户端
					String content="服务器的时间为:"+new Date(System.currentTimeMillis());
					doWrite(socketChannel,content);
				}else if(bytesRead<0){//关闭资源
					selectionKey.cancel();
					socketChannel.close();
				}
			}
		}
	}

	/**
	 * 向客户端会写信息
	 * @param socketChannel
	 * @param content
	 * @throws IOException 
	 */
	private void doWrite(SocketChannel socketChannel, String content) throws IOException {
		// TODO Auto-generated method stub
		byte[] cont=content.getBytes();
		ByteBuffer src=ByteBuffer.allocate(cont.length);
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
