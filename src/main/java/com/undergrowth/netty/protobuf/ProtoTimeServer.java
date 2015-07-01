/**
 * 
 */
package com.undergrowth.netty.protobuf;

import com.google.protobuf.MessageLite;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * ʹ��google��protobufЭ�����ͨ�� //netty���5���� //1�������̳߳� //2��ʹ�������������̳߳ء�ͨ����ͨ�ﴦ����������ִ�в���
 * //3���󶨶˿�ͬ������ //4�������˿ڹر� //5���ͷ���Դ
 * 
 * @author u1
 * @Date 2015-7-1
 */
public class ProtoTimeServer {

	private int port;

	public static void main(String[] args) {

	}

	public ProtoTimeServer(int port) {
		this.port = port;
	}

	public void run() {
		// netty���5����
		// 1�������̳߳�
		NioEventLoopGroup boss = new NioEventLoopGroup();
		NioEventLoopGroup work = new NioEventLoopGroup();
		// 2��ʹ�������������̳߳ء�ͨ����ͨ��������������ִ�в���
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boss, work).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 100).option(ChannelOption.SO_KEEPALIVE, true)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch)
							throws Exception {
						// TODO Auto-generated method stub
						// ���ý��봦�����Ͱ��������
						ch.pipeline().addLast(
								new ProtobufVarint32FrameDecoder());
						ch.pipeline().addLast(new ProtobufDecoder(prototype));
						// ���ñ������Ͱ��������
						ch.pipeline().addLast(
								new ProtobufVarint32LengthFieldPrepender());
						ch.pipeline().addLast(new ProtobufEncoder());
						// ���ô�����
						ch.pipeline().addLast(new ProtoTimeServerHandler());
					}
				});
		// 3���󶨶˿�ͬ������
		ChannelFuture future = bootstrap.bind(port).sync();
		// 4�������˿ڹر�
		future.channel().closeFuture().sync();
		// 5���ͷ���Դ
		boss.shutdownGracefully();
		work.shutdownGracefully();
	}
}
