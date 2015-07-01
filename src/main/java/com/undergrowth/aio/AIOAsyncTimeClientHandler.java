package com.undergrowth.aio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AIOAsyncTimeClientHandler implements Runnable {

	private AsynchronousSocketChannel asynchronousSocketChannel;
	// 多线程同步资源
	private CountDownLatch latch;
	private int port;

	public AsynchronousSocketChannel getAsynchronousSocketChannel() {
		return asynchronousSocketChannel;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public AIOAsyncTimeClientHandler(int port) throws IOException {
		// TODO Auto-generated constructor stub
		asynchronousSocketChannel = AsynchronousSocketChannel.open();
		this.port = port;
	}

	public void run() {
		// TODO Auto-generated method stub
		latch = new CountDownLatch(1);

		try {
			doConnect();
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 连接服务器
	 * 
	 * @throws UnknownHostException
	 */
	private void doConnect() throws UnknownHostException {
		// TODO Auto-generated method stub
		asynchronousSocketChannel.connect(
				new InetSocketAddress(InetAddress.getLocalHost(), port), this,
				new ConnectCompletionHandler());
	}

}
