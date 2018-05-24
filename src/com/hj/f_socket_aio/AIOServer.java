package com.hj.f_socket_aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AIOServer {
	static final int PORT = 30000;
	static final String UTF_8 = "UTF-8";
	static List<AsynchronousSocketChannel> channelList = new ArrayList<>();
	
	public void startListen() throws InterruptedException, Exception {
		//创建一个线程池
		ExecutorService executor = Executors.newFixedThreadPool(20);
		//创建一个channel组
		AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);
		//创建一个服务端socket channel
		AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open(channelGroup);
		//绑定
		serverChannel.bind(new InetSocketAddress(PORT));
		//completionHandler
		serverChannel.accept(null, new AcceptHandler(serverChannel));
		
		
	}
}

class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object> {
	private AsynchronousServerSocketChannel serverChannel;
	
	public AcceptHandler(AsynchronousServerSocketChannel sc) {
		this.serverChannel = sc;
	}
	
	ByteBuffer buffer = ByteBuffer.allocate(1024);
	
	@Override
	public void completed(AsynchronousSocketChannel sc, Object attachment) {
		//记录新连接的客户端
		AIOServer.channelList.add(sc);
		
		//准备接收客户端的下一次连接
		serverChannel.accept(null, this);
		
		//读取客户端数据
		sc.read(buffer, null, new CompletionHandler<Integer, Object>() {
			@Override
			public void completed(Integer result, Object attachment) {
				buffer.flip();
				String content = StandardCharsets.UTF_8.decode(buffer).toString();
				//将收到的信息,写入所有客户端对应的channel中
				for (AsynchronousSocketChannel c : AIOServer.channelList) {
					try {
						c.write(ByteBuffer.wrap(content.getBytes(AIOServer.UTF_8))).get();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//读取下一次数据
				buffer.clear();
				sc.read(buffer, null, this);
			}
			
			@Override
			public void failed(Throwable exc, Object attachment) {
				System.out.println("读取数据失败: " + exc);
				//失败后就删除
				AIOServer.channelList.remove(sc);
			}
		});
	}
	
	@Override
	public void failed(Throwable exc, Object attachment) {
		System.out.println("连接失败: " + exc);
	}
}
