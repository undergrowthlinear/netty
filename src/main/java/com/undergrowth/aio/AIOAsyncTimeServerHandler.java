package com.undergrowth.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * 采用AIO 真正的异步非阻塞IO方式
 * 比NIO编写代码要简单
 * @author Administrator
 *
 */
public class AIOAsyncTimeServerHandler implements Runnable {

	//服务器端的异步监听通道
	private AsynchronousServerSocketChannel  asynchronousServerSocketChannel;
	
	public AsynchronousServerSocketChannel getAsynchronousServerSocketChannel() {
		return asynchronousServerSocketChannel;
	}
	//用于使服务器线程等待
	private CountDownLatch latch;

	
	
	public CountDownLatch getLatch() {
		return latch;
	}

	public AIOAsyncTimeServerHandler(int port) throws IOException {
		// TODO Auto-generated constructor stub
		asynchronousServerSocketChannel=AsynchronousServerSocketChannel.open();
		//绑定端口
		asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
		System.out.println("异步非阻塞服务监听在"+port);
	}

	public void run() {
		// TODO Auto-generated method stub
		latch=new CountDownLatch(1);
		//接收连接
		doAccept();
		try {
			//等待操作完成
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 接收连接
	 */
	private void doAccept() {
		// TODO Auto-generated method stub
		asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
	}

}
