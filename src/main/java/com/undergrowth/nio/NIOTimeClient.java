package com.undergrowth.nio;

import java.io.IOException;

public class NIOTimeClient {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new Thread(new MultiplexerSelectorTimeClient(1111)).start();
	}

}
