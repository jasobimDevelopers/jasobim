package com.task;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**  
 * Filename: TaskTest.java  <br>
 *
 * Description:   <br>
 * 
 * @author: NQY <br> 
 * @version: 1.0 <br> 
 * @Createtime: 2015�?5�?29�? <br>
 *
 * @Copyright: Copyright (c)2015 by nqy <br>
 *  
 */
@Component 
public class TaskTest {

	 private static Logger logger = Logger.getLogger(TaskTest.class);  
	 //注解的形式加载定时器
	//@Scheduled(cron="0/5 * * * * ? ") //间隔5秒执�?  
	public void test()
	{
		logger.info("定时任务测试每隔5秒执行一次！");
	}
}
