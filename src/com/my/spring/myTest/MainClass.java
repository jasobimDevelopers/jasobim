package com.my.spring.myTest;

public class MainClass {
	 /**
	   * java多线程同步锁的使用
	   * 示例：三个售票窗口同时出售10张票
	   * */
	  public static void main(String[] args) {
	    //实例化站台对象，并为每一个站台取名字
	     Station station1=new Station("窗口1");
	     Station station2=new Station("窗口2");
	     Station station3=new Station("窗口3");
	     Station station4=new Station("窗口4");
	     Station station5=new Station("窗口5");
	    // 让每一个站台对象各自开始工作
	     station1.start();
	     station2.start();
	     station3.start();
	     station4.start();
	     station5.start();
	  }
}
