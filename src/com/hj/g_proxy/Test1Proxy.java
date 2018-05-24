package com.hj.g_proxy;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Test1Proxy {
	final String PROXY_ADDR = "129.82.12.188";
	final int PROXY_PORT = 3124;
	String urlStr = "http://www.crazyit.ort";
	
	public static void main(String[] args) throws IOException {
		
		new Test1Proxy().init();
	}
	
	public void init() throws IOException {
		URL url = new URL(urlStr);
		//创建一个代理服务器对象
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_ADDR, PROXY_PORT));
		//使用代理服务器来打开连接
		URLConnection conn = url.openConnection(proxy);
		conn.setConnectTimeout(3000);
		try (
				Scanner scanner = new Scanner(conn.getInputStream());
				PrintStream ps = new PrintStream("index.htm");
				) {
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				System.out.println(line);
				ps.println(line);
			}
		}
	}
}
