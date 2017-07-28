package com.my.spring.model;

import java.sql.Date;



public class UserPojo {
	private Long id;
    private String userName;
    private String password;
    private String realName;
    private Integer userType;
    private String email;
    private String tel;
    private Long userIcon;
    private String userIconUrl;
    private String[] projectName;
    private Date registerDate;
    private String workName;
    private String teamInformation;////班组信息
    private String projectList;
    private Integer systemType;
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
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	

	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	

	public Long getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(Long userIcon) {
		this.userIcon = userIcon;
	}
	public String getUserIconUrl() {
		return userIconUrl;
	}
	public void setUserIconUrl(String userIconUrl) {
		this.userIconUrl = userIconUrl;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public String[] getProjectName() {
		return projectName;
	}
	public void setProjectName(String[] projectName) {
		this.projectName = projectName;
	}
	public String getTeamInformation() {
		return teamInformation;
	}
	public void setTeamInformation(String teamInformation) {
		this.teamInformation = teamInformation;
	}
	public String getProjectList() {
		return projectList;
	}
	public void setProjectList(String projectList) {
		this.projectList = projectList;
	}
	public Integer getSystemType() {
		return systemType;
	}
	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	}
    
}
