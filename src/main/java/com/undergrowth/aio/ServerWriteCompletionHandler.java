package com.undergrowth.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * д��ɴ�����
 * @author Administrator
 *
 */
public class ServerWriteCompletionHandler implements
		CompletionHandler<Integer,ByteBuffer> {

	private AsynchronousSocketChannel asynchronousSocketChannel;
	
	public ServerWriteCompletionHandler(
			AsynchronousSocketChannel asynchronousSocketChannel) {
		// TODO Auto-generated constructor stub
		this.asynchronousSocketChannel=asynchronousSocketChannel;
	}

	public void completed(Integer result, ByteBuffer attachment) {
		// TODO Auto-generated method stub
		if(attachment.hasRemaining()){ //���д����û�����  ���ŷ���
			asynchronousSocketChannel.write(attachment,attachment,this);
		}
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
