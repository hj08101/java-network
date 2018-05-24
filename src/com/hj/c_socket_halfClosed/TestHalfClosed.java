package com.hj.c_socket_halfClosed;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestHalfClosed {
	
	/* ---- 半关闭Socket
	 * 
	 * 
	 * */
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(30000);
		Socket socket = serverSocket.accept();
		
		PrintStream ps = new PrintStream(socket.getOutputStream());
		ps.println("输出第一行");
		ps.println("输出第二行");
		
		//--关闭输出流
		//关半输出流后, 仍然可以读取输入流
		socket.shutdownOutput();
		//-- 但是, 如果ps关闭后, 则socket随之关闭.
		
		serverSocket.close();
		socket.close();
	}
}
