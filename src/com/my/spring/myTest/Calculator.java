package com.my.spring.myTest;

public class Calculator implements Runnable{///实现Runnable接口
	
	private int number;
	public Calculator(int number){
		this.number=number;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=1;i<=10;i++){
			System.out.printf("%s: %d*%d=%d\n",Thread.currentThread().getName(),number,i,number*i);
		}
	}
	public static void main(String []args){
		for(int i=1;i<=10;i++){
			Calculator calculator = new Calculator(i);
			Thread thread = new Thread(calculator);
			thread.start();
		}
	}

}
