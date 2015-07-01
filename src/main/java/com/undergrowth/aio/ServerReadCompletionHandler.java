package com.undergrowth.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

/**
 * 读完成处理器
 * @author Administrator
 *
 */
public class ServerReadCompletionHandler implements
		CompletionHandler<Integer,ByteBuffer> {

	private AsynchronousSocketChannel asynchronousSocketChannel;
	
	public ServerReadCompletionHandler(AsynchronousSocketChannel result) {
		// TODO Auto-generated constructor stub
		this.asynchronousSocketChannel=result;
	}

	public void completed(Integer result, ByteBuffer attachment) {
		// TODO Auto-generated method stub
		attachment.flip();
		byte[] str=new byte[attachment.remaining()];
		attachment.get(str);
		System.out.println("接收到的客户端的问题为:"+new String(str));
		//向客户端会写信息
		String content="服务器的现在时间为:"+new Date(System.currentTimeMillis());
		doWrite(content);
	}

	private void doWrite(String content) {
		// TODO Auto-generated method stub
		ByteBuffer buffer=ByteBuffer.allocate(content.getBytes().length);
		buffer.put(content.getBytes());
		buffer.flip();
		asynchronousSocketChannel.write(buffer, buffer, new ServerWriteCompletionHandler(asynchronousSocketChannel));
	}

	public void failed(Throwable exc, ByteBuffer attachment) {
		// TODO Auto-generated method stub
		exc.printStackTrace();
		try {
			this.asynchronousSocketChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
