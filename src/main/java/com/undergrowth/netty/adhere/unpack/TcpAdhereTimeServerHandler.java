/**
 * 
 */
package com.undergrowth.netty.adhere.unpack;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author u1
 * @Date  2015-7-6
 */
public class TcpAdhereTimeServerHandler extends ChannelHandlerAdapter {

	private int count=0;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		ByteBuf buf=(ByteBuf) msg;
		byte[] con=new byte[buf.readableBytes()];
		buf.readBytes(con);
		String conString=new String(con);
		System.out.println("服务器读到的数据为:"+conString.substring(0, conString.lastIndexOf(System.getProperty("line.separator")))+"\t 这是第"+(++count));
		String nowTime="现在时间为:"+new Date(System.currentTimeMillis())+System.getProperty("line.separator");
		ByteBuf rep=Unpooled.copiedBuffer(nowTime.getBytes());
		ctx.writeAndFlush(rep);
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
		cause.printStackTrace();
	}
	
	
	
}
