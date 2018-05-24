package com.hj.b_url_download;

public class Client {
	public static void main(String[] args) {
		String urlStr = "http://localhost:8888/Test/hj/day21_01.exe";
		String dstPath = "E:\\hj\\temp\\day21_01.exe";
		DownloadUtil.download(urlStr, dstPath);
	}
}
