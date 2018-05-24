package com.hj.b_url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class Test2URL {
	
	/* -- URL, 统一资源定位器
	 * 
	 * */

	public static void main(String[] args) throws IOException {
		URL url = new URL("xxx");
		//获取资源名
		String srcName = url.getFile();
		//获取主机名
		String hostName = url.getHost();
		//获取对应资源的路径
		String srcPath = url.getPath();
		//协议名称
		String protocol = url.getProtocol();
		//查询字符串
		String query = url.getQuery();
		//打开连接
		URLConnection conn = url.openConnection();
		//用于读取资源的输入流
		InputStream inputStream = url.openStream();
		
	
	}
	
	public static void test1() {
		String path = "http://img.mp.itc.cn/upload/20161115/6163765431c44d538b37d6efb32ee885_th.jpg";
//		URL url = new URL(path);
//		URLConnection conn = url.openConnection();
		
		//请求头相关设置:
		/*
		conn.setAllowUserInteraction(boolean);
		conn.setDoInput(boolean);
		conn.setIfModifiedSince(long);
		conn.setUseCaches(boolean);
		 */
		
		//设置其它通用头字段(会覆盖已经设置的)
//		conn.setRequestProperty(String key, String value);
		
		//设置其它通用头字段(不会覆盖已经设置的)
//		conn.addRequestProperty(String key, String value);
		
		
		//----当远程资源可用后,就可以获取头字段和内容了
//		Object obj = conn.getContent();
//		String respHeaderField = conn.getHeaderField("");
//		InputStream inStream = conn.getInputStream();
//		OutputStream outStream = conn.getOutputStream();
		//....
		
		
		//-----如果既要使用输入流读取URLConnection的响应内容,又要使用输出流发送请求参数, 那么一定要先使用输出流, 再使用输入流.
	}
	
	

}
