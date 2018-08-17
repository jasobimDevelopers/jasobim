package com.my.spring.serviceImpl.ThreadServiceImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.model.Question;

public class QuestionThreadService extends BaseDao<Question>{
	
	private Question question;
	
	public QuestionThreadService(Question question){
		this.setQuestion(question);
	}
	/*public void run() {
		if(save(this.question)){
			System.out.println("添加安全问题完成！");
		}
		
	}*/
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
}
