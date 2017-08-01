package com.my.spring.myTest;

public class PrimeGenerator extends Thread{////继承多线程
	
	@Override
	public void run() {
	  long number=1L;
	  while (true) {
	     if (isPrime(number)) {
	        System.out.printf("Number %d is Prime",number);
	     }
	     if (isInterrupted()) {
	    	   System.out.printf("The Prime Generator has been Interrupted");
	    	   return;
	     }
	    	number++;
	  	}
    }
	public boolean isPrime(long number){
		if(number<2){
			return true;
		}
		for(int i=2;i<number;i++){
			if(number%i==0){
				return false;
			}
		}
		return true;
	}
	public static void main(String[] args){
		Thread thread = new PrimeGenerator();
		thread.start();
		try{
			Thread.sleep(5000);
		}catch(Exception e){
			e.printStackTrace();
		}
		thread.interrupt();
	}
}
