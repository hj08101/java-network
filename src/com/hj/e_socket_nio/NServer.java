package com.hj.e_socket_nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NServer {
	//用于检测所有Channel状态的Selector
	private Selector selector = null;
	static final int PORT = 30000;
	private Charset charset = Charset.forName("UTF-8");
	
	public static void main(String[] args) throws IOException {
		new NServer().init();
	}
	
	public void init() throws IOException {
		selector = Selector.open();
		//打开一个未绑定的channel
		ServerSocketChannel server = ServerSocketChannel.open();
		//将channel与ip地址绑定
		server.bind(new InetSocketAddress("localhost", PORT));
		//设置非阻塞
		server.configureBlocking(false);
		//注册到指定的selector
		server.register(selector, SelectionKey.OP_ACCEPT);
		
		//循环监听selector的select()方法的返回值
		while (selector.select() > 0) {
			//依次处理选择器上每个已选择的key
			for (SelectionKey sk : selector.selectedKeys()) {
				//??删除??, 可以进行删除操作吗??
				selector.selectedKeys().remove(sk);
				
				//--sk对应的channel包含客户端的连接请求
				if (sk.isAcceptable()) {
					//接受连接
					SocketChannel sc = server.accept();
					//设置非阻塞
					sc.configureBlocking(false);
					//注册到selector
					sc.register(selector, SelectionKey.OP_READ);
					//将sk对应的channel设置成准备接收其他请求
					sk.interestOps(SelectionKey.OP_ACCEPT);
				}
				
				//--sk对应的channel有数据需要读取
				if (sk.isReadable()) {
					SocketChannel sc = (SocketChannel)sk.channel();
					//读数据
					ByteBuffer buff = ByteBuffer.allocate(1024);
					String content = "";
					try {
						while (sc.read(buff) > 0) {
							buff.flip();//先反转再取数据
							content += charset.decode(buff);
						}
						System.out.println("读取的数据: " + content);
						//将sk对应的channel设置成准备下一次读取
						sk.interestOps(SelectionKey.OP_READ);
					} catch (IOException e) {
						//从sk对应的channel中读取数据出现异常
						//表明对应的client出现了问题
						//所以从selector中取消sk的注册
						sk.cancel();
						if (sk.channel() != null) {
							sk.channel().close();
						}
					}
					
					//聊天信息
					if (content.length() > 0) {
						//将聊天信息写入所有key的channel中
						for (SelectionKey key : selector.keys()) {
							Channel targetChannel = key.channel();
							if (targetChannel instanceof SocketChannel) {
								//将聊天内容定入该channel
								SocketChannel dst = (SocketChannel)targetChannel;
								dst.write(charset.encode(content));
							}
						}
					}
				}
			}
		}
	}
	
	
	
}
