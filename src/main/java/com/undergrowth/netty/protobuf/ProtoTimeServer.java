/**
 * 
 */
package com.undergrowth.netty.protobuf;

import com.undergrowth.netty.protobuf.message.MessageReqProto;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * 使用google的protobuf协议进行通信 //netty编程5步骤 //1、创建线程池 //2、使用引导器关联线程池、通道、通达处理器、设置执行参数
 * //3、绑定端口同步操作 //4、监听端口关闭 //5、释放资源
 * 
 * @author u1
 * @Date 2015-7-1
 */
public class ProtoTimeServer {

	private int port;

	public static void main(String[] args) {
       new ProtoTimeServer(6666).run();
	}

	public ProtoTimeServer(int port) {
		this.port = port;
	}

	public void run() {
		NioEventLoopGroup boss = null, work = null;
		try {
			// netty编程5步骤
			// 1、创建线程池
			boss = new NioEventLoopGroup();
			work = new NioEventLoopGroup();
			// 2、使用引导器关联线程池、通道、通道处理器、设置执行参数
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(boss, work).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							// TODO Auto-generated method stub
							// 设置解码处理器和半包处理器
							/*ch.pipeline().addLast(
									new ProtobufVarint32FrameDecoder());*/
							ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1048576, 0, 4,0,4));
							ch.pipeline().addLast(
									new ProtobufDecoder( //设置解码器的目标类型
											MessageReqProto.MessageReq
													.getDefaultInstance()));
							// 设置编码器和半包处理器
							/*ch.pipeline().addLast(
									new ProtobufVarint32LengthFieldPrepender());*/
							ch.pipeline().addLast(new LengthFieldPrepender(4));
							ch.pipeline().addLast(new ProtobufEncoder());
							// 设置处理器
							ch.pipeline().addLast(new ProtoTimeServerHandler());
						}
					});
			// 3、绑定端口同步操作
			ChannelFuture future = bootstrap.bind(port).sync();
			// 4、监听端口关闭
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			// 5、释放资源
			boss.shutdownGracefully();
			work.shutdownGracefully();
		}

	}
}
