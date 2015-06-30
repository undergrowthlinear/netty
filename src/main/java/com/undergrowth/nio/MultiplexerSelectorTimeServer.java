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
 * ��ͨ�������� ��ͨ������ע��,��·���������������˿ڵ�����
 * 
 * @author Administrator
 * 
 */
public class MultiplexerSelectorTimeServer implements Runnable {

	// ��·������
	private Selector selector;
	private ServerSocketChannel serverSocketChannel1, serverSocketChannel2;

	public MultiplexerSelectorTimeServer(int port1, int port2)
			throws IOException {
		// TODO Auto-generated constructor stub
		// ��ѡ����
		selector = Selector.open();
		// ��ͨ��
		serverSocketChannel1 = ServerSocketChannel.open();
		// �󶨶˿�
		serverSocketChannel1.socket().bind(new InetSocketAddress(port1));
		// ����Ϊ������ģʽ
		serverSocketChannel1.configureBlocking(false);
		// ��ͨ��ע�ᵽ��·������
		serverSocketChannel1.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("�Ѿ�ע�ᵽ" + port1 + ",�ȴ�����");
		// �ڶ����˿�
		// ��ͨ��
		/*serverSocketChannel2 = ServerSocketChannel.open();
		// �󶨶˿�
		serverSocketChannel2.socket().bind(new InetSocketAddress(port2));
		// ����Ϊ������ģʽ
		serverSocketChannel2.configureBlocking(false);
		// ��ͨ��ע�ᵽ��·������
		serverSocketChannel2.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("�Ѿ�ע�ᵽ" + port2 + ",�ȴ�����");*/
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			// ����ѭ��
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));
			while (true) {
				/*System.out.println("�Ƿ�Ҫ����");
				String isGoString = reader.readLine();
				if ("N".equalsIgnoreCase(isGoString))
					break;*/
				System.out.println("�������ȴ��¼�Դ����");
				// �ȴ�ע����¼�Դ����
				int readyChannel = selector.select(1000);
				if (readyChannel == 0)
					continue;
				System.out.println("��" + readyChannel + "��׼������");
				// ��ȡ׼���õ�ͨ��
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> selectKeyIterator = selectionKeys.iterator();
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
	 * @param selectionKey
	 * @throws IOException
	 */
	private void handlerEvent(SelectionKey selectionKey) throws IOException {
		// TODO Auto-generated method stub
		if(selectionKey.isValid()){
			if(selectionKey.isAcceptable()){//��ʾ�пͻ������ӹ��� ע��ͻ��˵Ŀɶ��¼�
				ServerSocketChannel serverSocketChannel=(ServerSocketChannel) selectionKey.channel();
				SocketChannel socketChannel=serverSocketChannel.accept();
				socketChannel.configureBlocking(false);
				socketChannel.register(selector, SelectionKey.OP_READ);
			}
			if(selectionKey.isReadable()){//��ʾ�ͻ��������ݹ��� ��������
				SocketChannel socketChannel=(SocketChannel) selectionKey.channel();
				ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
				int bytesRead=socketChannel.read(byteBuffer);
				if(bytesRead>0){//��ʾ����������
					//����������ģʽת��
					byteBuffer.flip();
					//��ȡ������������
					byte[] dst=new byte[byteBuffer.remaining()];
					byteBuffer.get(dst);
					System.out.println("�ͻ��˵�����Ϊ:"+new String(dst));
					//��д������Ϣ���ͻ���
					String content="��������ʱ��Ϊ:"+new Date(System.currentTimeMillis());
					doWrite(socketChannel,content);
				}else if(bytesRead<0){//�ر���Դ
					selectionKey.cancel();
					socketChannel.close();
				}
			}
		}
	}

	/**
	 * ��ͻ��˻�д��Ϣ
	 * @param socketChannel
	 * @param content
	 * @throws IOException 
	 */
	private void doWrite(SocketChannel socketChannel, String content) throws IOException {
		// TODO Auto-generated method stub
		byte[] cont=content.getBytes();
		ByteBuffer src=ByteBuffer.allocate(cont.length);
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
