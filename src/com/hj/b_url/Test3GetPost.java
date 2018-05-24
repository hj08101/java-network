package com.hj.b_url;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class Test3GetPost {
	
	public static void name() {
		
	}
	
	public static String postTest(String url, String param) {
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			//--POST必须设置这两项:
			conn.setDoOutput(true);
			conn.setDoInput(true);
			try (
					//通过输出流来发送参数
					PrintWriter outPw = new PrintWriter(conn.getOutputStream());
					) {
				outPw.println(param);
				outPw.flush();
				
				try (
						BufferedReader inBr = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
						) {
					String line;
					while ((line = inBr.readLine()) != null) {
						result += "\n" + line;
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("POST请求异常");
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static String getTest(String url, String param) {
		String result = "";
		String urlName = url + "?" + param;
		try {
			//--1.创建URL对象
			URL realUrl = new URL(urlName);
			//--2.打开与URL之间的连接
			URLConnection conn = realUrl.openConnection();
			//--3.设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent"
				, "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			//--4.建立连接
			conn.connect();
			//获取所有响应头字段
			Map<String, List<String>> map = conn.getHeaderFields();
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			//利用输入流来读取响应内容
			try (
					BufferedReader inBr = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
					) {
				String line;
				while ((line = inBr.readLine()) != null) {
					result += "\n" + line;
				}
				
			}
		} catch (Exception e) {
			System.out.println("GET请求异常");
			e.printStackTrace();
		}
		return result;
	}
}
