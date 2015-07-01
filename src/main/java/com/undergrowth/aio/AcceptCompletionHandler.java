package com.undergrowth.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 当连接完成时 进行处理客户端连接
 * @author Administrator
 *
 */
public class AcceptCompletionHandler
		implements
		CompletionHandler<AsynchronousSocketChannel,AIOAsyncTimeServerHandler> {

	public void completed(AsynchronousSocketChannel result,
			AIOAsyncTimeServerHandler attachment) {
		// TODO Auto-generated method stub
		//继续接收另外的客户端连接
		attachment.getAsynchronousServerSocketChannel().accept(attachment, this);
		ByteBuffer dst=ByteBuffer.allocate(1024);
		//处理读操作
		result.read(dst, dst, new ServerReadCompletionHandler(result));
	}

	public void failed(Throwable exc, AIOAsyncTimeServerHandler attachment) {
		// TODO Auto-generated method stub
		exc.printStackTrace();
		//使服务器线程结束
		attachment.getLatch().countDown();
	}

}
