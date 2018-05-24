package com.hj.h_udp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpServer {
	public static final int PORT = 30000;
	//每个数据报最大字节数据
	private static final int DATA_LEN = 4096;
	//保存接收的数据
	byte[] inBuff = new byte[DATA_LEN];
	//用于接收数据的数据报
	private DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
	//用于发送数据的数据报
	private DatagramPacket outPacket;
	
	String[] books = {"红楼梦", "西游记", "三国演义", "水浒传"};
	
	public static void main(String[] args) {
		new UdpServer().init();
	}
	
	public void init() {
		try (
				DatagramSocket socket = new DatagramSocket(PORT);
				) {
			//循环接收1000个客户端发送的数据
			for (int i = 0; i < 1000; i++) {
				//读取数据,存入数据报内部的数组中
				socket.receive(inPacket);
				System.out.println(inBuff == inPacket.getData());
				String content = new String(inBuff, 0, inPacket.getLength());
				System.out.println("收到消息: " + content);
				
				//回发数据
				byte[] reply = "收到你的消息了!".getBytes();
				outPacket = new DatagramPacket(reply, reply.length, inPacket.getSocketAddress());
				socket.send(outPacket);
			}
		} catch (IOException e) {
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
