package com.my.spring.myTest;

import java.util.Scanner;

public class Mains {
	private static Scanner sc;

	public static void main(String[] arg){
		int a,b;
		Scanner sc = new Scanner(System.in); 
		a = sc.nextInt();
		b = sc.nextInt();
		if(a>=0 && b<100){
			System.out.println(a+b);
		}	
	}
}
