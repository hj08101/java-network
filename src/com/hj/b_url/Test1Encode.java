package com.hj.b_url;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Test1Encode {
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		
		String cnStr = "张三李四";
		
		//编码:
		String encodeStr = URLEncoder.encode(cnStr, "UTF-8");
		System.out.println(encodeStr);
		
		//解码
		String decodeStr = URLDecoder.decode(encodeStr, "UTF-8");
		System.out.println(decodeStr);
	}
}
