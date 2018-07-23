package com.my.spring.model;

import java.util.Date;

public class CommonNotice {
	private Integer noticeType;
	private String title;
	private String createUserName;
	private Long aboutId;
	private String createDate;/////精确到天
	private String userIconUrl;
	private String content;
	private String projectName;
	private String imagUrl;
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
	
}
