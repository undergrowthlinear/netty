package com.undergrowth.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ClientReadCompletionHandler implements
		CompletionHandler<Integer, ByteBuffer> {
	
	private AsynchronousSocketChannel asynchronousSocketChannel;
	private AIOAsyncTimeClientHandler handler;
	
	public ClientReadCompletionHandler(
			AsynchronousSocketChannel asynchronousSocketChannel,AIOAsyncTimeClientHandler handler) {
		// TODO Auto-generated constructor stub
		this.asynchronousSocketChannel=asynchronousSocketChannel;
		this.handler=handler;
	}

	public void completed(Integer result, ByteBuffer attachment) {
		// TODO Auto-generated
		// method stub
		// 从buffer中读取字节数据
		attachment.flip();
		byte[] read = new byte[attachment.remaining()];
		attachment.get(read);
		System.out.println("服务端收到的信息为:" + new String(read));
		handler.getLatch().countDown();
	}

	public void failed(Throwable exc, ByteBuffer attachment) {
		// TODO Auto-generated
		// method stub
		exc.printStackTrace();
		try {
			asynchronousSocketChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
