package com.hj.a_inetaddress;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test1InetAddress {
	private static int i;
	
	/*--- InetAddress代表IP地址
	 * 
	 * */

	public static void main(String[] args) throws IOException {
		
	Runtime.getRuntime().addShutdownHook(new Thread(()->{
			System.out.println("退出了 i = " + i);
	}));
		
		while (true) {
			i++;
			System.out.println(i);
			if (i % 1000 == 0) {
				System.exit(1);
			}
		}
	
		
	
	
		
		
//		//根据主机名获取ip对象,即域名解析
//		InetAddress ip = InetAddress.getByName("www.baidu.com");
//		//是否可达
//		System.out.println(ip.isReachable(2000));
//		//获取ip地址,字符串
//		System.out.println(ip.getHostAddress());
//		//获取主机名,域名
//		System.out.println(ip.getHostName());
//		
//		//根据ip地址获取ip对象
//		InetAddress ip2 = InetAddress.getByAddress(new byte[] {127,0,0,1});
//		System.out.println(ip2.isReachable(1000));
//		System.out.println(ip2.getCanonicalHostName());
		
	}

}
