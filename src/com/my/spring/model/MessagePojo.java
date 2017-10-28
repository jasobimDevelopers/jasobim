package com.my.spring.model;

import java.sql.Date;


public class MessagePojo {
	private Long id;
	private String content;
	private String messageDate;
	private Long questionId;
	private Long userId;
	private String userName;
	private String userIconUrl;
	private String[] fileList;
	private String[] fileNameList;
	private String realName;
	private String projectName;
	private Long projectId;
	
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
	
	
	public String getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(String messageDate) {
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
	public String getUserIconUrl() {
		return userIconUrl;
	}
	public void setUserIconUrl(String userIconUrl) {
		this.userIconUrl = userIconUrl;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	

}
