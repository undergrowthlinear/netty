/**
 * 
 */
package com.undergrowth.netty.adhere.unpack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * //netty编程5步骤
		//1、创建NIO线程池，接收连接、处理连接
		//2、使用引导类管理线程池、NIO通道、事件处理器、参数配置
		//3、绑定端口，同步等待操作完成
		//4、同步等待关闭通道,防止main进程退出
		//5、关闭线程池资源
 * @author u1
 * @Date  2015-7-6
 */
public class TcpAdhereTimeServer {

	private int port;
	
	/**
	 * @param i
	 */
	public TcpAdhereTimeServer(int port) {
		// TODO Auto-generated constructor stub
		this.port=port;
	}


	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		new TcpAdhereTimeServer(3333).run();
	}
    
	
	public void run() throws InterruptedException{
		//netty编程5步骤
				//1、创建NIO线程池，接收连接、处理连接
				NioEventLoopGroup boss=new NioEventLoopGroup();
				NioEventLoopGroup work=new NioEventLoopGroup();
				//2、使用引导类管理线程池、NIO通道、事件处理器、参数配置
				ServerBootstrap bootstrap=new ServerBootstrap();
				bootstrap.group(boss, work).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						// TODO Auto-generated method stub
						/*ch.pipeline().addLast(new LineBasedFrameDecoder(1024));*/
						ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(System.getProperty("line.separator").getBytes())));
						ch.pipeline().addLast(new StringDecoder());
						ch.pipeline().addLast(new TcpLineBasedFrameDecoderTimeServerHandler());
					}
				}).childOption(ChannelOption.SO_KEEPALIVE, true);
				//3、绑定端口，同步等待操作完成
				ChannelFuture future=bootstrap.bind(port).sync();
				//4、同步等待关闭通道,防止main进程退出
				future.channel().closeFuture().sync();
				//5、关闭线程池资源
				boss.shutdownGracefully();
				work.shutdownGracefully();
	}
}
