package com.my.spring.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.ByteArrayInputStream;

public class Reader implements Runnable {
	public Reader(String filename) {
		this.filename = filename;
	}
	
	private String filename;
	private long filelength = 0;
	private int count=0;
	
	@Override
	public void run() {
		while (true) {
			try {
				File f = new File(filename);
				long nowlength = f.length();
				long readlength = nowlength - filelength;
				if (readlength == 0) {
					Thread.sleep(1000);
					continue;
				}
				RandomAccessFile rf = new RandomAccessFile(f, "r");
				// 移动文件指针到上次读的最后
				rf.seek(filelength);
				filelength=nowlength;
				byte[] b = new byte[(int) readlength];
				rf.read(b, 0, b.length);
				rf.close();
				BufferedReader br=new BufferedReader(new InputStreamReader(new ByteArrayInputStream(b)));
				String str=null;
				count++;
				System.out.println("第"+count+"次读到的内容:");
				while((str=br.readLine())!=null){
					System.out.println(str);
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
	
	