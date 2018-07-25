package com.my.spring;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.my.spring.parameters.Parameters;
import com.my.spring.utils.SessionManager;
import com.my.spring.wechat.WechatSpider;

@Component("Taskaa") 
public class TaskTest {

	 private static Logger logger = Logger.getLogger(TaskTest.class);  
	 //注解的形式加载定时器
	@Scheduled //间隔5秒执�?  
	public void test()
	{
		logger.info("定时任务测试每隔5秒执行一次！");
		WechatSpider wechatSpider = new WechatSpider("gh_c886b7f1ee09");
		String listUrl = wechatSpider.getListUrl();
		Date dates = new Date();
		String data_str = Parameters.getSdfs().format(dates);
		if(listUrl!=null && !listUrl.equals("")){
			SessionManager.newWechatSession(data_str, listUrl);
		}
		//String wechat_url = SessionManager.getWechatSession(data_str);
		//System.out.println(wechat_url);
	}
}

