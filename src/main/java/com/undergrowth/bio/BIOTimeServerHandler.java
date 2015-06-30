package com.undergrowth.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;

public class BIOTimeServerHandler implements Runnable {

	private Socket socket;

	public BIOTimeServerHandler(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}

	
	public void run() {
		// TODO Auto-generated method stub
		BufferedReader in = null;
		PrintWriter out = null;
		// 获取输入、输出流
		try {
			in = new BufferedReader(new InputStreamReader(
					this.socket.getInputStream()));
			out = new PrintWriter(this.socket.getOutputStream(),true);
			// 读取客户端传递的数据
			String string = in.readLine();
			System.out.println("客户端传递的数据为:" + string);
			out.println("服务器现在时间为:" + new Date(System.currentTimeMillis()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (out != null)
				out.close();

			try {
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
