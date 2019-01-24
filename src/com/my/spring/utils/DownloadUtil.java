package com.my.spring.utils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class DownloadUtil {
	public static void main(String[] args) throws Exception{
		String url = "http://jasobim.com:8080/fileUploads/folderFiles/205/cc5003a431835d173036fce33e826ca7.jpg";
		URL u = new URL(url);
		InputStream is = u.openStream();
		OutputStream os = new FileOutputStream("D://test.jpg");
		int buff = 0;
		while((buff = is.read()) != -1){
			os.write(buff);
		}
		os.close();
		is.close();
		}
}
