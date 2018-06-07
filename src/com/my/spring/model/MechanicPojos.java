package com.my.spring.model;

public class MechanicPojos {
	private Long id;
	private Long projectId;
	private String realName;
	private String workName;////工种
	private Integer workHours;
	private String userIcon;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public Integer getWorkHours() {
		return workHours;
	}
	public void setWorkHours(Integer workHours) {
		this.workHours = workHours;
	}
	public String getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}
	
	
}
