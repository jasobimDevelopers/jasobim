package com.my.spring.model;

import java.sql.Date;


public class MessagePojo {
	private Long id;
	private String content;
	private Date messageDate;
	private Long questionId;
	private Long userId;
	private String userName;
	private String[] fileList;
	private String[] fileNameList;
	
	
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
	
	
	public Date getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String[] getFileList() {
		return fileList;
	}
	public void setFileList(String[] fileList) {
		this.fileList = fileList;
	}
	public String[] getFileNameList() {
		return fileNameList;
	}
	public void setFileNameList(String[] fileNameList) {
		this.fileNameList = fileNameList;
	}
	

}
