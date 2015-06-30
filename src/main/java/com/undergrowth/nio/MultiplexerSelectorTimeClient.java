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
 * �ͻ��˶�·������ ���ڼ������ӡ������ɶ��¼�
 * 
 * @author Administrator
 * 
 */
public class MultiplexerSelectorTimeClient implements Runnable {

	// ��·������
	private Selector selector;
	private SocketChannel socketChannel;
	private int port;

	public MultiplexerSelectorTimeClient(int port) throws IOException {
		// TODO Auto-generated constructor stub
		// �򿪶�·������
		selector = Selector.open();
		// ��ͨ��
		socketChannel = SocketChannel.open();
		// ����Ϊ������ģʽ
		socketChannel.configureBlocking(false);
		this.port = port;
	}

	public void run() {
		// TODO Auto-generated method stub

		try {
			// ����
			doConnect();
			// ����ѭ��
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));
			while (true) {
				/*System.out.println("�Ƿ�Ҫ����");
				String isGoString = reader.readLine();
				if ("N".equalsIgnoreCase(isGoString))
					break;
				System.out.println("�ȴ��¼�Դ����");*/
				System.out.println("�ͻ��˵ȴ��¼�Դ����");
				int readyChannel = selector.select(1000);
				if (readyChannel == 0)
					continue;
				System.out.println("��" + readyChannel + "��׼������");
				// ��ȡ׼���õ�ͨ��
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> selectKeyIterator = selectionKeys
						.iterator();
				while (selectKeyIterator.hasNext()) {
					SelectionKey selectionKey = (SelectionKey) selectKeyIterator
							.next();
					//����ע����׼���õ��¼�Դ  
	                interestSet(selectionKey.interestOps()); 
					// ���������¼�Դ
					handlerEvent(selectionKey);
					// �Ƴ��Ѿ�������¼�Դ
					selectKeyIterator.remove();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * ����ʱ��Դ���¼�
	 * 
	 * @param selectionKey
	 * @throws IOException
	 */
	private void handlerEvent(SelectionKey selectionKey) throws IOException {
		// TODO Auto-generated method stub
		if (selectionKey.isValid()) {
			if (selectionKey.isConnectable()) {// ��ʾ�ͻ��������Ϸ����� ע��ɶ��¼�
				SocketChannel socketChannel = (SocketChannel) selectionKey
						.channel();
				if (socketChannel.finishConnect()) {//��ʾ���������
					socketChannel.register(selector, SelectionKey.OP_READ);
					socketChannel.configureBlocking(false);
					doWrite(socketChannel, "����ʱ���Ƕ���");
				}
			}
			if (selectionKey.isReadable()) {// ��ʾ�����������ݹ��� ��������
				SocketChannel socketChannel = (SocketChannel) selectionKey
						.channel();
				ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
				int bytesRead = socketChannel.read(byteBuffer);
				if (bytesRead > 0) {// ��ʾ����������
					// ����������ģʽת��
					byteBuffer.flip();
					// ��ȡ������������
					byte[] dst = new byte[byteBuffer.remaining()];
					byteBuffer.get(dst);
					System.out.println("������������Ϊ:" + new String(dst));
				} else if (bytesRead < 0) {// �ر���Դ
					selectionKey.cancel();
					socketChannel.close();
					
				}
			}
		}
	}

	/**
	 * ���ӿͻ���
	 * 
	 * @throws IOException
	 */
	private void doConnect() throws IOException {
		// TODO Auto-generated method stub
		boolean flag = socketChannel.connect(new InetSocketAddress(InetAddress
				.getLocalHost(), port));
		System.out.println("�Ƿ����ӵ�������" + flag);
		if (flag) { // ������ӳɹ�
					// ע��ɶ��¼�
					// ��������
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel, "����ʱ���Ƕ���");
		} else { // û�����ӵĻ� ��ע�������¼�
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}

	/**
	 * �������д��Ϣ
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
		src.flip();//put�൱�ڶ�ģʽ  positionλ��д����ֽ���Ŀ  limitΪcapacity  ��д��ʱ�� ��Ҫ��ת
		socketChannel.write(src);
	}
	
	 /** 
     * ����ע����׼���õ��¼�Դ 
     * @param interestOps 
     */  
    private void interestSet(int interestSet) {  
        // TODO Auto-generated method stub  
        if((interestSet&SelectionKey.OP_ACCEPT)!=0) System.out.println("ע��Ŀɽ���");  
        if((interestSet&SelectionKey.OP_CONNECT)!=0) System.out.println("ע��Ŀ�����");  
        if((interestSet&SelectionKey.OP_READ)!=0) System.out.println("ע��Ŀɶ�");  
        if((interestSet&SelectionKey.OP_WRITE)!=0) System.out.println("ע��Ŀ�д");  
    }  
}
