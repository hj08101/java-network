package com.hj.d_socket_chat;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	private static final int SERVER_PORT = 30000;
	public static ChatMap<String, PrintStream> clients = new ChatMap<>();
	
	public static void main(String[] args) throws IOException {
		ChatServer server = new ChatServer();
		server.init();
	}
	
	public void init() {
		try (
				ServerSocket serverSocket = new ServerSocket(SERVER_PORT)
				) {
			while (true) {
				Socket socket = serverSocket.accept();
				//每连接一个客户端, 就创建一个子线程去处理相关业务
				new ServerThread(socket);
			}
		} catch (IOException e) {
			System.out.println("服务器启动失效, 是否商品" + SERVER_PORT + "已经被占用? ");
		}
	}
}
