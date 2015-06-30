package com.undergrowth.nio;

import java.io.IOException;


/**
 * 使用NIO方式 异步IO
 * 使用多路复用器关联通道 当通道中有事件时 即通知处理 不用阻塞IO
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
