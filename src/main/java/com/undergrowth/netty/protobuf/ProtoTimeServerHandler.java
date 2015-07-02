/**
 * 
 */
package com.undergrowth.netty.protobuf;

import com.undergrowth.netty.protobuf.message.MessageReqProto;
import com.undergrowth.netty.protobuf.message.MessageReqProto.MessageReq;
import com.undergrowth.netty.protobuf.message.MessageRespProto;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author u1
 * @Date  2015-7-1
 */
public class ProtoTimeServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		//
		MessageReqProto.MessageReq messageReq=(MessageReq) msg;
		System.out.println("服务端接收到的消息为:"+messageReq);
		//会写客户端信息
		ctx.writeAndFlush(resp(messageReq.getReqId()));
	}

	/**
	 * 
	 * @param reqId
	 * @return
	 */
	private MessageRespProto.MessageResp resp(int reqId) {
		// TODO Auto-generated method stub
		MessageRespProto.MessageResp.Builder builder=MessageRespProto.MessageResp.newBuilder();
		builder.setReqId(reqId);
		builder.setRespCode(0);
		builder.setDesc("服务器接收到客户端的消息");
		return builder.build();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
		
	
	
}
