package com.hj.c_socket;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Test1Server {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(30000);
		while (true) {
			//生成一个与客户端对应的socket对象
			//accept是一个阻塞线程的方法
			Socket client = server.accept();
			//只有在成功连接某个客户端,才会往下执行.
			//通过输出流,向客户端发送数据
			PrintStream ps = new PrintStream(client.getOutputStream());
			ps.println("客户端你好, 我是服务端.");
			ps.close();
			
			//通过输入流,读取客户端发过来的数据
			byte[] bs = new byte[1024];
			int len = client.getInputStream().read(bs);
			System.out.println(new String(bs,0,len));
			client.close();
		}
	}
}
