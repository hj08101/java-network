package com.hj.e_socket_nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class NClient {
	private Selector selector = null;
	static final int PORT = 30000;
	private Charset charset = Charset.forName("UTF-8");
	private SocketChannel sc = null;
	
	public static void main(String[] args) throws IOException {
		new NClient().init();
	}
	
	public void init() throws IOException {
		selector = Selector.open();
		sc = SocketChannel.open(new InetSocketAddress("localhost", PORT));
		sc.configureBlocking(false);
		sc.register(selector, SelectionKey.OP_READ);
		new ClientThread().start();
		
		//从键盘读取数据, 并写入SocketChannel中.
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			sc.write(charset.encode(line));
		}
		scanner.close();
	}
	
	private class ClientThread extends Thread {
		@Override
		public void run() {
			try {
				while (selector.select() > 0) {
					for (SelectionKey sk : selector.selectedKeys()) {
						//删除
						selector.selectedKeys().remove(sk);
						
						//sk对应的channel中有数据可读
						if (sk.isReadable()) {
							SocketChannel sc = (SocketChannel)sk.channel();
							ByteBuffer buff = ByteBuffer.allocate(1024);
							String content = "";
							while (sc.read(buff) > 0) {
								buff.flip();
								content += charset.decode(buff);
							}
							System.out.println("聊天信息: " + content);
							//为下一次读取做准备
							sk.interestOps(SelectionKey.OP_READ);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
