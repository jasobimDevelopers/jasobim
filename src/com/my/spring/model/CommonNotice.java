package com.my.spring.model;

import java.util.List;

public class CommonNotice {
	private Integer noticeType;
	private String title;
	private String createUserName;
	private Long aboutId;
	private Long aboutCreateUserId;
	private String createDate;/////精确到天
	private String userIconUrl;
	private String content;
	private String projectName;
	private String imagUrl;
	private String name;
	private List<String> sendUserList;
	private Integer messageType;//0.质量留言 1.安全留言
	private Integer readState;//0、未读  1、已读
	public Integer getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUserIconUrl() {
		return userIconUrl;
	}
	public void setUserIconUrl(String userIconUrl) {
		this.userIconUrl = userIconUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getImagUrl() {
		return imagUrl;
	}
	public void setImagUrl(String imagUrl) {
		this.imagUrl = imagUrl;
	}
	public Long getAboutId() {
		return aboutId;
	}
	public void setAboutId(Long aboutId) {
		this.aboutId = aboutId;
	}
	public Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getSendUserList() {
		return sendUserList;
	}
	public void setSendUserList(List<String> sendUserList) {
		this.sendUserList = sendUserList;
	}
	public Long getAboutCreateUserId() {
		return aboutCreateUserId;
	}
	public void setAboutCreateUserId(Long aboutCreateUserId) {
		this.aboutCreateUserId = aboutCreateUserId;
	}
	public Integer getReadState() {
		return readState;
	}
	public void setReadState(Integer readState) {
		this.readState = readState;
	}
	
}
