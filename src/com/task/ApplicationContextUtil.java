package com.task;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		ApplicationContextUtil.applicationContext = applicationContext;
	}
	public static Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}
}
