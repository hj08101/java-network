package com.hj.g_proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

public class Test2Proxy {
	public static void main(String[] args) {
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("", 8888));
		Socket socket = new Socket(proxy);
	}
}
