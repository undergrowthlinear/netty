package com.undergrowth.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 时间处理的服务端
 * 连接服务器--telnet localhost 7777
 * @author Administrator
 * 
 */
public class TimeServer {

	private int port;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TimeServer(7777).run();
	}

	public TimeServer(int port) {
		this.port = port;
	}

	public void run() {
		// 1、构建事件处理池
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			
			// 2、使用引导程序关联事件处理池、通道、事件处理器
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap
					.group(boss, worker)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							// TODO Auto-generated method stub
							ch.pipeline().addLast(new TimeEncoder(),new TimeServerHandler());
						}

					}).option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			// 3、绑定端口服务
			ChannelFuture future = bootstrap.bind(port).sync();
			//4、等待操作完成
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}finally{
			//5、关闭事件处理池
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}

	}
}
