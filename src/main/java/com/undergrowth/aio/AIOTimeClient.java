package com.undergrowth.aio;

import java.io.IOException;

public class AIOTimeClient {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new Thread(new AIOAsyncTimeClientHandler(9999)).start();
	}

}
