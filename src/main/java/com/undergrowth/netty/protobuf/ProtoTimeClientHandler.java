/**
 * 
 */
package com.undergrowth.netty.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.undergrowth.netty.protobuf.message.MessageReqProto;

/**
 * @author u1
 * @Date  2015-7-1
 */
public class ProtoTimeClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.writeAndFlush(req());
	}

	/**
	 * 
	 * @return
	 */
	private MessageReqProto.MessageReq req() {
		// TODO Auto-generated method stub
		MessageReqProto.MessageReq.Builder builder=MessageReqProto.MessageReq.newBuilder();
		builder.setReqId(1);
		builder.setUserName("undergrowth");
		builder.setAddress("广州天河区");
		return builder.build();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		//锟斤拷目锟斤拷msg锟斤拷锟斤拷锟斤拷锟斤拷转锟斤拷
		System.out.println("接收到服务器的消息为:"+msg);
	}

	@Override
	@Skip
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
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
