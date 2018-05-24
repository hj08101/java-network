package com.hj.d_socket_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket socket;
	BufferedReader br = null;
	PrintStream ps = null;
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			ps = new PrintStream(socket.getOutputStream());
			String line = null;
			while ((line = br.readLine()) != null) {
				//读到不同的信息
				//读到尝试登录的用户名
				if (line.startsWith(ChatProtocol.USER_ROUND) && line.endsWith(ChatProtocol.USER_ROUND)) {
					String username = parseMsgLine(line);
					if (ChatServer.clients.map.containsKey(username)) {
						System.out.println("重复");
						ps.println(ChatProtocol.NAME_REP);
					} else {
						System.out.println("成功");
						ps.println(ChatProtocol.LOGIN_SUCCESS);
						ChatServer.clients.put(username, ps);
					}
				}
				//读到私聊信息
				else if (line.startsWith(ChatProtocol.PRIVATE_ROUND) && line.endsWith(ChatProtocol.PRIVATE_ROUND)) {
					String userAndMsg = parseMsgLine(line);
					String[] arr = userAndMsg.split(ChatProtocol.SPLIT_SIGN);
					String user = arr[0];
					String msg = arr[1];
					ChatServer.clients.map.get(user).println(ChatServer.clients.getKeyByValue(ps) + "悄悄对你说: " + msg);
				}
				//否则就是群聊
				else {
					String msg = parseMsgLine(line);
					for (PrintStream clientPs : ChatServer.clients.valueSet()) {
						clientPs.println(ChatServer.clients.getKeyByValue(ps) + "说: " + msg);
					}
				}
			}
			
		} catch (Exception e) {
			//有异常,说明对应客户端出现了问题,将其删除
			ChatServer.clients.removeByValue(ps);
			System.out.println(ChatServer.clients.map.size());
			//关闭资源
			try {
				if (br != null) br.close();
				if (ps != null) ps.close();
				if (socket != null) socket.close();
				if (br != null) br.close();
				if (br != null) br.close();
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	private String parseMsgLine(String line) {
		return line.substring(ChatProtocol.PROTOCOL_LEN,  line.length() - ChatProtocol.PROTOCOL_LEN);
	}
}
