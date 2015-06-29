package com.undergrowth.netty;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {
	
	
	public static void main(String[] args) throws UnknownHostException, InterruptedException{
		EventLoopGroup work=new NioEventLoopGroup();
		Bootstrap bootstrap=new Bootstrap();
		bootstrap.group(work);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// TODO Auto-generated method stub
				ch.pipeline().addLast(new TimeDecoder(),new TimeClientHandler());
			}
		});
		ChannelFuture future=bootstrap.connect(InetAddress.getLocalHost(), 7777).sync();
		future.channel().closeFuture().sync();
		work.shutdownGracefully();
	}
	
	
	
}
