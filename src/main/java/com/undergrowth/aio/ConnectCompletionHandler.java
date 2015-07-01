package com.undergrowth.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 连接完成处理器
 * 
 * @author Administrator
 * 
 */
public class ConnectCompletionHandler implements
		CompletionHandler<Void, AIOAsyncTimeClientHandler> {

	private AsynchronousSocketChannel asynchronousSocketChannel;

	public void completed(Void result, final AIOAsyncTimeClientHandler handler) {
		// TODO Auto-generated method stub
		// 进行写操作
		asynchronousSocketChannel = handler.getAsynchronousSocketChannel();
		String content = "现在时间是多少";
		byte[] conBytes = content.getBytes();
		ByteBuffer src = ByteBuffer.allocate(conBytes.length);
		src.put(conBytes);
		src.flip();
		asynchronousSocketChannel.write(src, src,
				new ClientWriteCompletionHandler(asynchronousSocketChannel,handler));

	}

	public void failed(Throwable exc, AIOAsyncTimeClientHandler attachment) {
		// TODO Auto-generated method stub
		exc.printStackTrace();
		attachment.getLatch().countDown();
	}

}
