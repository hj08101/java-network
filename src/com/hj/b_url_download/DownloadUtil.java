package com.hj.b_url_download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadUtil {
	
	public static void download(String srcUrl, String dstPath) {
		URL url = null;
		try {
			url = new URL(srcUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (url == null) {
			return;
		}
		
		URLConnection connection = null;
		try {
			 connection = url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (connection == null) {
			return;
		}
		
		File dstFile = new File(dstPath);
		File parentDir = dstFile.getParentFile();
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		
		try (
				InputStream inputStream = connection.getInputStream();
				RandomAccessFile outRaf = new RandomAccessFile(dstFile, "rw");
				) {
			byte[] bs = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(bs)) > 0) {
				outRaf.write(bs, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
