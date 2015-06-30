package com.undergrowth.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 使用线程池进行处理客户端的请求
 * 因为在进行read或者write的时候 都是阻塞IO的 所以还是同步阻塞IO  只是线程的资源交由线程池进行控制
 * @author Administrator
 * 
 */
public class PseudoAsyncTimeServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new PseudoAsyncTimeServer(5555).run();
	}

	private int port;

	public PseudoAsyncTimeServer(int port) {
		this.port = port;
	}

	public void run() throws IOException {
		ServerSocket serverSocket = null;
		PseudoAsyncExecutorService executorService=new PseudoAsyncExecutorService();
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("服务器在" + port + "等待监听");
			while (true) {
				// 等待客户端连接
				Socket socket = serverSocket.accept();
				//使用线程池进行处理客户端连接
				executorService.execute(new BIOTimeServerHandler(socket));
			}
		} finally {
			if (serverSocket != null)
				serverSocket.close();
			serverSocket = null;
		}
	}
}
