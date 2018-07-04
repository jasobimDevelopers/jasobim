package com.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.my.spring.DAO.WechatUrlDao;
import com.my.spring.model.WechatUrl;
import com.my.spring.service.NewsService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.wechat.WechatSpider;

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
//@Component("Taskaa") 
public class TaskTest {
	@Autowired
	WechatUrlDao wechatDao;
	 private static Logger logger = Logger.getLogger(TaskTest.class);  
	 //注解的形式加载定时器
	//@Scheduled //间隔5秒执�?  
	public void test()
	{
		logger.info("定时任务测试每隔5秒执行一次！");
		WechatSpider wechatSpider = new WechatSpider("gh_c886b7f1ee09");
		String listUrl = wechatSpider.getListUrl();
		if(listUrl!=null && !listUrl.equals("")){
			WechatUrl news = new WechatUrl();
			news.setUrl(listUrl);
			news.setCreateDate(new Date());
			wechatDao.addWechatUrl(news);
		}
		/*NewsService newsService = (NewsService) ApplicationContextUtil.getBean("NewsService");
		newsService.saveWechatUrl(null);*/
	}
}
