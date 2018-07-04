package com.my.spring.model;

public class UserLogPojo {
	private Long id;
	private String userName;
	private String projectName;
	private String projectPart;//#0.模型区域 1.图纸区域 2.登录区域 3.交底区域 4.预制化区域 5.没有
	private String version;
	private String actionDate;
	private Integer actionType;//0.浏览 1.新增 2.登录 3.更新
	private String systemType;//0.苹果系统  1.安卓系统
	private String fileName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectPart() {
		return projectPart;
	}
	public void setProjectPart(String projectPart) {
		this.projectPart = projectPart;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}

	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getActionType() {
		return actionType;
	}
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
	

}
