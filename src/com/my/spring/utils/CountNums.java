package com.my.spring.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CountNums {
	private static List<Integer> moneys=new ArrayList<Integer>();
	private static List<Integer> counts = new ArrayList<Integer>();
	private static Integer strs=0;
	public CountNums(){
		moneys.add(64);
		moneys.add(16);
		moneys.add(4);
		moneys.add(1);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		moneys.add(64);
		moneys.add(16);
		moneys.add(4);
		moneys.add(1);
		List<Integer> tests = new ArrayList<Integer>();
		tests.add(64);
		tests.add(16);
		tests.add(4);
		tests.add(1);
		test2(1024-sc.nextInt(),moneys);
		int numst=0;
		for(int i=0;i<counts.size();i++){
			numst=numst+counts.get(i);
			System.out.println(tests.get(i)+"元:"+counts.get(i)+"个");
		}
		System.out.println(numst);
		
	}
	
	public static int test2(Integer ns,List<Integer> teststr){
		if(ns==0){
			return 0;
		}else{
			strs=ns%(teststr.get(0));
			counts.add(ns/(teststr.get(0)));
			teststr.remove(0);
		}
		return test2(strs,teststr);
	}
	
}
