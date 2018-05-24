package com.hj.h_udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UdpClient {
	public static final int DEST_PORT = 30000;
	public static final String DEST_IP = "127.0.0.1";
	private static final int DATA_LEN = 4096;
	byte[] inBuff = new byte[DATA_LEN];
	private DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
	private DatagramPacket outPacket = null;
	
	public static void main(String[] args) {
		new UdpClient().init();
	}
	
	public void init() {
		try (
				DatagramSocket socket = new DatagramSocket();
				) {
			//用于发送的数据报
			outPacket = new DatagramPacket(new byte[0], 0, InetAddress.getByName(DEST_IP), DEST_PORT);
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextLine()) {
				byte[] buff = scanner.nextLine().getBytes();
				outPacket.setData(buff);
				socket.send(outPacket);
				//再接收一次
				socket.receive(inPacket);
				System.out.println(new String(inBuff, 0, inPacket.getLength()));
			}
			
			
			
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
