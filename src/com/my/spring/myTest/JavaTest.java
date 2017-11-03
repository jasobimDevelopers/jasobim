package com.my.spring.myTest;

public class JavaTest {
	public static String reverse(String originStr){
		if(originStr == null || originStr.length() <=1){
			return originStr;
		}
		return reverse(originStr.substring(1))+originStr.charAt(0);
		
	}
	public static void main(String[] arg){
		String str="adwqytbn123";
		System.out.println(reverse(str));
	}
}
