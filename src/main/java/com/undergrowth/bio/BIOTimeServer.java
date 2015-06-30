package com.undergrowth.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * ʹ��BIO��ʽ
 * ʹ��ServerSocket��IP��ַ�������˿ڣ�ʹ��Socket�����������ӣ����ӳɹ���˫��ͨ���������������ͬ������ʽͨ��
 * ÿ���пͻ��˵�����󣬼�����һ���߳̽��д���
 * @author Administrator
 *
 */
public class BIOTimeServer {
	
	private int port ;
	
	public static void main(String[] args) throws IOException{
		new BIOTimeServer(5555).run();
	}
	
	public BIOTimeServer(int port){
		this.port=port;
	}
	
	public void run() throws IOException{
		ServerSocket serverSocket=null;
		try{
			 serverSocket=new ServerSocket(port);
			 System.out.println("��������"+port+"�ȴ�����");
			while(true){
				//�ȴ��ͻ�������
				Socket socket=serverSocket.accept();
				//�����߳�ִ�пͻ�������
				new Thread(new BIOTimeServerHandler(socket)).start();
			}
		}finally{
			if(serverSocket!=null) serverSocket.close();
			serverSocket=null;
		}
	}
	
	
}
