package com.hj.g_proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Test3ProxySelector {
	final String PROXY_ADDR = "139.82.12.188";
	final int PROXY_PORT = 3124;
	String urlStr = "http://www.crazyit.ort";
	
	public static void main(String[] args) throws IOException {
		new Test3ProxySelector().init();
	}
	
	private void init() throws IOException {
		//注册默认的代理选择器
		ProxySelector.setDefault(new ProxySelector() {
			@Override
			public List<Proxy> select(URI uri) {
				//根据业务需要返回特定的对应代理服务器
				//此处示例总是返回某个固定的代理服务器
				List<Proxy> result = new ArrayList<>();
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_ADDR, PROXY_PORT));
				result.add(proxy);
				return result;
			}
			
			@Override
			public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
				System.out.println("无法连接到指定的代理服务器!");
			}
		});
		
		URL url = new URL(urlStr);
		//因为没有指定代理服务器,所以直接打开连接
		//但依然会使用代理服务器
		URLConnection conn = url.openConnection();
		//...
	}
}
