/**
 * 
 */
package com.undergrowth.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.undergrowth.netty.po.UnixTime;

/**
 * @author u1
 * @Date  2015-6-29
 */
public class TimeDecoder extends ByteToMessageDecoder {

	/* (non-Javadoc)
	 * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
	 */
	@Override
	protected void decode(ChannelHandlerContext arg0, ByteBuf arg1,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		if(arg1.readableBytes()<4){return;}
		out.add(new UnixTime(arg1.readInt()));
	}

	

}
