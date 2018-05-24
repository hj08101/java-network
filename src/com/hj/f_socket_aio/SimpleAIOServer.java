package com.hj.f_socket_aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SimpleAIOServer {
	static final int PORT = 30001;
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		try (
				AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
				
				) {
			serverChannel.bind(new InetSocketAddress(PORT));
			while (true) {
				Future<AsynchronousSocketChannel> future = serverChannel.accept();
				//获取连接完成后返回的,与客户端相对应的channel
				//get()方法将会阻塞
				AsynchronousSocketChannel socketChannel = future.get();
				
				//最后调get()是要干什么???
				socketChannel.write(ByteBuffer.wrap("欢迎来到AIO的世界!".getBytes("UTF-8"))).get();
			}
		}
	}
}
