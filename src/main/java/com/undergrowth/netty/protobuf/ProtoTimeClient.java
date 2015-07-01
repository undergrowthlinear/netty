/**
 * 
 */
package com.undergrowth.netty.protobuf;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author u1
 * @Date 2015-7-1
 */
public class ProtoTimeClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ProtoTimeClient(6666).run();
	}

	private int port;

	public ProtoTimeClient(int port) {
		this.port = port;
	}

	public void run() {
		// netty编程5步骤
		// 1、创建线程池
		NioEventLoopGroup work=new NioEventLoopGroup();
		// 2、使用引导器关联线程池、通道、通达处理器、设置执行参数
		Bootstrap bootstrap=new Bootstrap();
		bootstrap.group(work).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// TODO Auto-generated method stub
				//设置解码处理器和半包处理器
				ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
				ch.pipeline().addLast(new ProtobufDecoder(prototype));
				//设置编码器和半包处理器
				ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
				ch.pipeline().addLast(new ProtobufEncoder());
				//设置处理器
				ch.pipeline().addLast(new ProtoTimeClientHandler());
			}

			
		});
		// 3、绑定端口同步操作
		ChannelFuture future=bootstrap.connect(new InetSocketAddress(port)).sync();
		// 4、监听端口关闭
		future.channel().closeFuture().sync();
		// 5、释放资源
		work.shutdownGracefully();
	}

}
