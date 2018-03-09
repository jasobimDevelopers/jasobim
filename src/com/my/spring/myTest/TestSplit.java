package com.my.spring.myTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
 
public class TestSplit {
	Process process;
	public static void main(String[] args) {
		String aString="张三丰";
		System.out.println(aString.substring(aString.length()-2, aString.length()));
	}
	public void sendParams(String params) {
		
	}
	public void exec(String cmd) {
		Runtime run = Runtime.getRuntime();
		try {
		process= run.exec(cmd);
		InputStream in=process.getInputStream();  //获取DOS下的输出
		int temp,i=0;
		byte b[]=new byte[10240];
		while((temp=in.read())!=-1){
		b[i]=(byte)temp;
		i++;
		}
		System.out.println(new String(b));
		} catch (IOException e) {
		e.printStackTrace();
		} 
	}
}