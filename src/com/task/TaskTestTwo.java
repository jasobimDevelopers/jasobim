package com.task;

import org.apache.log4j.Logger;

public class TaskTestTwo {
	
	private static Logger logger = Logger.getLogger(TaskTestTwo.class);  
	
	//配置文件的形式加载定时器
	public void taskTest(){
		logger.info("定时任务测试每隔5秒执行一次！");
		System.out.println("定时任务测试，每个5秒执行一次！");
	}
	
}
