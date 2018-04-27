package com.my.spring.model;

import java.util.Date;
import java.util.List;


import net.sf.json.JSONArray;



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
    private String registerDate;
    private String workName;
    private String teamInformation;////班组信息
    private String projectList;
    private String[] projectLists;
    private Object menuList;
    private Integer readState;///0 可读  1可写
    public String[] getProjectLists() {
		return projectLists;
	}
	public void setProjectLists(String[] projectLists) {
		this.projectLists = projectLists;
	}
	private Integer systemType;
    private String[] menuItemList;
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
	

	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
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
	public String[] getMenuItemList() {
		return menuItemList;
	}
	public void setMenuItemList(String[] menuItemList) {
		this.menuItemList = menuItemList;
	}
	public Object getMenuList() {
		return menuList;
	}
	public void setMenuList(Object object) {
		this.menuList = object;
	}
	public Integer getReadState() {
		return readState;
	}
	public void setReadState(Integer readState) {
		this.readState = readState;
	}
    
}
