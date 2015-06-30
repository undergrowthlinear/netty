package com.undergrowth.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ʹ���̳߳ؽ��д���ͻ��˵�����
 * ��Ϊ�ڽ���read����write��ʱ�� ��������IO�� ���Ի���ͬ������IO  ֻ���̵߳���Դ�����̳߳ؽ��п���
 * @author Administrator
 * 
 */
public class PseudoAsyncTimeServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new PseudoAsyncTimeServer(5555).run();
	}

	private int port;

	public PseudoAsyncTimeServer(int port) {
		this.port = port;
	}

	public void run() throws IOException {
		ServerSocket serverSocket = null;
		PseudoAsyncExecutorService executorService=new PseudoAsyncExecutorService();
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("��������" + port + "�ȴ�����");
			while (true) {
				// �ȴ��ͻ�������
				Socket socket = serverSocket.accept();
				//ʹ���̳߳ؽ��д���ͻ�������
				executorService.execute(new BIOTimeServerHandler(socket));
			}
		} finally {
			if (serverSocket != null)
				serverSocket.close();
			serverSocket = null;
		}
	}
}
