package com.my.spring.model;

public class UserTeamPojo {
	private Long id;
	private String name;
	private String createUser;
	private String createDate;
	private String remark;
	private String teamUserName;
	private Long projectId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getTeamUserName() {
		return teamUserName;
	}
	public void setTeamUserName(String teamUserName) {
		this.teamUserName = teamUserName;
	}
	
	

}
