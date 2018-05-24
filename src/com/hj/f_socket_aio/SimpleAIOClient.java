package com.hj.f_socket_aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;

public class SimpleAIOClient {
	static final int PORT = 30001;
	public static void main(String[] args) throws Exception{
		ByteBuffer buff = ByteBuffer.allocate(1024);
		Charset utf = Charset.forName("UTF-8");
		try (
				AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
				
				) {
			//连接远程服务器
			//必须要调用连接方法返回future对象的get()方法
			socketChannel.connect(new InetSocketAddress("localhost", PORT)).get();
			
			//读取数据
			//必须要调用future的get()方法
			socketChannel.read(buff).get();
			buff.flip();
			
			String content = utf.decode(buff).toString();
			System.out.println("服务器信息: " + content);
		}
	}
}
