package com.hj.c_socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Test1Client {
	public static void main(String[] args) throws IOException {
		//--本机ip可以使用: "127.0.0.1" 
		//--本机域名可以使用: "localhost"
		
		Socket socket = new Socket("127.0.0.1", 30000);
		
		//如果需要设置超时, 则应该这么做:
		Socket socket2 = new Socket();
		socket2.connect(new InetSocketAddress("localhost", 30000), 1000);
		
		
		
		//通过输入流,获取服务端发过来的数据
		try (Scanner scanner = new Scanner(socket.getInputStream())) {
			String line = scanner.nextLine();
			System.out.println("收到服务器的数据:\n" + line);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		/*
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		String line = br.readLine();
		System.out.println("收到服务器的数据:\n" + line);
		br.close();
		*/
		
		//通过输出流,向服务端发送数据
		try (
				OutputStream outStream = socket.getOutputStream();
				) {
			outStream.write("服务端你好, 我是客户端.".getBytes());
		}
		
		socket.close();
	}
}
