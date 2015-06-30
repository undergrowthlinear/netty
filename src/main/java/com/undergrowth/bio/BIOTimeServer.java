package com.undergrowth.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 使用BIO方式
 * 使用ServerSocket绑定IP地址，启动端口，使用Socket进行握手连接，连接成功后，双方通过输入输出流进行同步阻塞式通信
 * 每当有客户端的请求后，即启动一个线程进行处理
 * @author Administrator
 *
 */
public class BIOTimeServer {
	
	private int port ;
	
	public static void main(String[] args) throws IOException{
		new BIOTimeServer(5555).run();
	}
	
	public BIOTimeServer(int port){
		this.port=port;
	}
	
	public void run() throws IOException{
		ServerSocket serverSocket=null;
		try{
			 serverSocket=new ServerSocket(port);
			 System.out.println("服务器在"+port+"等待监听");
			while(true){
				//等待客户端连接
				Socket socket=serverSocket.accept();
				//启动线程执行客户端连接
				new Thread(new BIOTimeServerHandler(socket)).start();
			}
		}finally{
			if(serverSocket!=null) serverSocket.close();
			serverSocket=null;
		}
	}
	
	
}
