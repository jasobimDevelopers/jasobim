package com.my.spring.model;

import java.util.Date;

public class MessageCopy {
	private Long id;
	private String content;
	private Date message_date;
	private Long about_id;
	private Long user_id;
	private Long project_id;
	private Integer question_type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getMessage_date() {
		return message_date;
	}
	public void setMessage_date(Date message_date) {
		this.message_date = message_date;
	}
	public Long getAbout_id() {
		return about_id;
	}
	public void setAbout_id(Long about_id) {
		this.about_id = about_id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getProject_id() {
		return project_id;
	}
	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}
	public Integer getQuestion_type() {
		return question_type;
	}
	public void setQuestion_type(Integer question_type) {
		this.question_type = question_type;
	}
	
	
	

}
