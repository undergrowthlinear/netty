package com.undergrowth.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter{

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf buf=ctx.alloc().buffer(4);
		buf.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
		final ChannelFuture f=ctx.writeAndFlush(buf);
		f.addListener(new ChannelFutureListener() {
			
			public void operationComplete(ChannelFuture future) throws Exception {
				// TODO Auto-generated method stub
				assert future==f;
				ctx.close();
			}
		});
	}
	
	
	
}
