package com.undergrowth.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Skip;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.sql.Date;

import com.undergrowth.netty.po.UnixTime;

/**
 * 客户端事件处理器
 * 
 * @author Administrator
 * 
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

	
	
	@Override
	@Skip
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.handlerAdded(ctx);
	}

	@Override
	@Skip
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.handlerRemoved(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		/*ByteBuf buf = (ByteBuf) msg;
		try {
			long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
			System.out.println(new Date(currentTimeMillis));
			ctx.close();
		} finally {
			buf.release();
		}*/
		UnixTime time = (UnixTime) msg;
		System.out.println(time);
		ctx.close();
	}

}
