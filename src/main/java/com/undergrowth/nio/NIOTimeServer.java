package com.undergrowth.nio;

import java.io.IOException;


/**
 * ʹ��NIO��ʽ �첽IO
 * ʹ�ö�·����������ͨ�� ��ͨ�������¼�ʱ ��֪ͨ���� ��������IO
 * @author Administrator
 *
 */
public class NIOTimeServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new Thread(new MultiplexerSelectorTimeServer(1111,2222)).start();
	}

}
