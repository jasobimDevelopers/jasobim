package com.my.spring.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer implements Runnable{
	public Writer(String filename) {
		this.filename = filename;
	}
	private String filename;
	private int count=0;
	
	@Override
	public void run() {
		while (count++<100){
			try {
				PrintWriter pw=new PrintWriter(new FileWriter(filename,true));
				pw.append(""+count).append("\t").append(""+System.currentTimeMillis()).append("写入的内容").append("\r\n");
				pw.close();
				Thread.sleep(100);
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