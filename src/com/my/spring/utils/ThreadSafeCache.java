package com.my.spring.utils;

public class ThreadSafeCache {
	int result;
	public int getResult(){
		return result;
	}
	public synchronized void setResult(int result){
		this.result=result;
	}
	public static void main(String[] arg){
		ThreadSafeCache threadSafeCache = new ThreadSafeCache();
		for(int i=0;i<8;i++){
		}
		try{
			Thread.sleep(1000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		threadSafeCache.setResult(200);
	}
}
