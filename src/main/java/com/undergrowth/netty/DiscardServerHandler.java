package com.undergrowth.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * 处理服务器端抛弃协议
 * 
 * @author Administrator
 * 
 */
public class DiscardServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)

		// Discard the received data silently.
		//1、抛弃接收到的消息
		//((ByteBuf) msg).release(); // (3)
		//2、将接收到的消息输出到控制台
		/*ByteBuf buf=(ByteBuf) msg;
		try {
			while(buf.isReadable()){
				System.out.print((char)buf.readByte());
				System.out.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			ReferenceCountUtil.release(msg);
		}*/
		//3、将接收到的消息写回给客户端
		ctx.writeAndFlush(msg);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)

		// Close the connection when an exception is raised.

		cause.printStackTrace();

		ctx.close();

	}

}
