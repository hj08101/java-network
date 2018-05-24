package com.hj.d_socket_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class ChatClient {
	private static final int SERVER_PORT = 30000;
	private Socket socket;
	private PrintStream ps;
	private BufferedReader br;
	private BufferedReader kbBr;//读键盘数据
	
	public static void main(String[] args) throws IOException {
		ChatClient client = new ChatClient();
		client.init();
		client.readKeyboardAndSend();
	}
	
	public void init() {
		try {
			//键盘输入
			kbBr = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			//连接服务器
			socket = new Socket("localhost", SERVER_PORT);
			ps = new PrintStream(socket.getOutputStream());
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			
			//提示输入用户名
			String tip = "";
			while (true) {
				String username = JOptionPane.showInputDialog(tip + "输入用户名");
				//按协议格式化, 并发送
				ps.println(ChatProtocol.USER_ROUND + username + ChatProtocol.USER_ROUND);
				//读取服务器响应
				String serverResponse = br.readLine();
				if (serverResponse.equals(ChatProtocol.NAME_REP)) {
					tip = "用户名重复, 请重新";
					continue;
				}
				if (serverResponse.equals(ChatProtocol.LOGIN_SUCCESS)) {
					break;
				}
			}
			
		} catch (UnknownHostException ex) {
			System.out.println("找不到远程服务器, 请确定该服务器已经启动!");
			closeRs();
			System.exit(1);
		} catch (IOException ex) {
			System.out.println("网络异常, 请重新登录!");
			closeRs();
			System.exit(1);
		}
		
		//启动一个线程,去读取处理服务端发过来的数据
		new ClientThread(br).start();
	}
	
	private void readKeyboardAndSend() {
		try {
			String line = null;
			while ((line = kbBr.readLine()) != null) {
				if (line.indexOf(":") > 0 && line.startsWith("//")) {
					line = line.substring(2);
					ps.println(ChatProtocol.PRIVATE_ROUND + line.split(":")[0] + ChatProtocol.PRIVATE_ROUND);
				} else {
					ps.println(ChatProtocol.MSG_ROUND + line + ChatProtocol.MSG_ROUND);
				}
			}
			
		} catch (IOException e) {
			System.out.println("网络通信异常, 请重新登录!");
			closeRs();
			System.exit(1);
		}
	}
	
	private void closeRs() {
		try {
			if (kbBr != null) kbBr.close();
			if (br != null) br.close();
			if (ps != null) ps.close();
			if (socket != null) socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
