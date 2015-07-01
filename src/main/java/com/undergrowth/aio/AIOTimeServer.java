package com.undergrowth.aio;

import java.io.IOException;

/**
 * 使用AIO方式，异步非阻塞IO
 * 相当于NIO的升级版 编码思路上相比于NIO而言更加的简单明了
 * @author Administrator
 *
 */
public class AIOTimeServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new Thread(new AIOAsyncTimeServerHandler(9999)).start();
	}

}
