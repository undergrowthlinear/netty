package com.undergrowth.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ClientWriteCompletionHandler implements
		CompletionHandler<Integer, ByteBuffer> {

	private AsynchronousSocketChannel asynchronousSocketChannel;
	private AIOAsyncTimeClientHandler handler;
	
	public ClientWriteCompletionHandler(
			AsynchronousSocketChannel asynchronousSocketChannel,AIOAsyncTimeClientHandler handler) {
		// TODO Auto-generated constructor stub
		this.asynchronousSocketChannel=asynchronousSocketChannel;
		this.handler=handler;
	}

	public void completed(Integer result, ByteBuffer attachment) {
		// TODO Auto-generated method stub
		if (attachment.hasRemaining()) {// 如果未发送完 接着发送
			asynchronousSocketChannel.write(attachment,
					attachment, this);
		} else {// 进行读操作
			ByteBuffer readBuffer = ByteBuffer.allocate(1024);
			asynchronousSocketChannel
					.read(readBuffer,
							readBuffer,
							new ClientReadCompletionHandler(asynchronousSocketChannel,handler));
		}
	}

	public void failed(Throwable exc, ByteBuffer attachment) {
		// TODO Auto-generated method stub
		exc.printStackTrace();
		try {
			asynchronousSocketChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
