/**
 * 
 */
package com.undergrowth.netty.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author u1
 * @Date  2015-7-1
 */
public class ProtoTimeServerHandler extends ChannelHandlerAdapter {

	@Override
	@Skip
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		//将目标msg进行类型转换
		
		//回写服务器信息
		ctx.writeAndFlush("");
	}

	@Override
	@Skip
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
		
	
	
}
