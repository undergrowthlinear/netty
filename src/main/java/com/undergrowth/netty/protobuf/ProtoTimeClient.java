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
		// netty���5����
		// 1�������̳߳�
		NioEventLoopGroup work=new NioEventLoopGroup();
		// 2��ʹ�������������̳߳ء�ͨ����ͨ�ﴦ����������ִ�в���
		Bootstrap bootstrap=new Bootstrap();
		bootstrap.group(work).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// TODO Auto-generated method stub
				//���ý��봦�����Ͱ��������
				ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
				ch.pipeline().addLast(new ProtobufDecoder(prototype));
				//���ñ������Ͱ��������
				ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
				ch.pipeline().addLast(new ProtobufEncoder());
				//���ô�����
				ch.pipeline().addLast(new ProtoTimeClientHandler());
			}

			
		});
		// 3���󶨶˿�ͬ������
		ChannelFuture future=bootstrap.connect(new InetSocketAddress(port)).sync();
		// 4�������˿ڹر�
		future.channel().closeFuture().sync();
		// 5���ͷ���Դ
		work.shutdownGracefully();
	}

}
