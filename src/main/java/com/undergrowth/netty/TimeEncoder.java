/**
 * 
 */
package com.undergrowth.netty;

import com.undergrowth.netty.po.UnixTime;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author u1
 * @Date  2015-6-29
 */
public class TimeEncoder extends MessageToByteEncoder<UnixTime> {

	/* (non-Javadoc)
	 * @see io.netty.handler.codec.MessageToByteEncoder#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.buffer.ByteBuf)
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, UnixTime unixTime, ByteBuf buf)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("SERVER--"+unixTime);
		buf.writeInt(unixTime.getValue());
	}

}
