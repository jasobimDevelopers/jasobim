package com.my.spring.myTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
 
public class TestSplit {
	Process process;
	public static void main(String[] args) {
		String test="123,124,125,126";
		test = test.replace("126,","");
		System.out.println(test);
		for(int j=0;j<test.split(",").length;j++){
			System.out.println(test.split(",").length);
			System.out.println(test.split(",")[j]);
		}
		
		/*String sa="0a1:3,1a2:4,2a4:5";
		String[] str=sa.split(",");
		for(int i=0;i<str.length;i++){
			System.out.println(str[i]);
			String[] strs=str[i].split("a");
			for(int j=0;j<strs.length;j++){
				System.out.println(strs[j]);
			}
			
		}*/
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