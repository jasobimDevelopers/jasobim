package com.task;

import org.apache.log4j.Logger;

import com.my.spring.wechat.WechatSpider;
import com.my.spring.wechat.models.Topic;

public class TaskOfGetWeChat {
	
	private static Logger logger = Logger.getLogger(TaskOfGetWeChat.class);  
	
	//配置文件的形式加载定时器
	public void taskTest(){
		logger.info("定时任务测试每隔5秒执行一次！");
		System.out.println("定时任务测试，每个5秒执行一次！");
		WechatSpider spider = new WechatSpider("gh_c886b7f1ee09");//嘉实集团有限公司
        String listUrl = spider.getListUrl();
        System.out.println("链接地址："+listUrl);
		Topic topic = spider.getTopicByUrl(listUrl);
		System.out.println("链接地址标题："+topic.getContent());
	}
	
}
