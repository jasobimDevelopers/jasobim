package com.my.spring.serviceImpl.ThreadServiceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.my.spring.model.QualityQuestion;
import com.my.spring.model.Question;

public class MainTest {
	private static QuestionThreadService questionService;
	private static QualityThreadService qualityService;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Question question = new Question();
		QualityQuestion quality = new QualityQuestion();
		
		////
		question.setIntro("线程异步测试");
		question.setName("线程异步测试");
		question.setPriority(0);
		question.setProjectId((long)79);
		question.setState(0);
		question.setUserId((long)33);
		question.setQuestionDate(new Date());
		question.setTrades("测试");
		question.setUserList("124,111");
		questionService = new QuestionThreadService(question);
		/////////
		quality.setIntro("线程异步测试");
		quality.setName("线程异步测试");
		quality.setPriority(0);
		quality.setProjectId((long)79);
		quality.setState(0);
		quality.setUserId((long)33);
		quality.setQuestionDate(new Date());
		quality.setTrades("测试");
		quality.setUserList("124,111");
		qualityService = new QualityThreadService(quality);
	}

}
