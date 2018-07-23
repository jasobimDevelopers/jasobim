package com.my.spring.model;

public class ProcessDataPojo {
	private Long id;
	private String name;
	private Integer itemNum;
	private String createDate;
	private String createUser;
	private String projectId;
	private Long currentApproveUser;
	private Integer state;
	private String currentItemName;
	
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
	
	public Integer getItemNum() {
		return itemNum;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public Long getCurrentApproveUser() {
		return currentApproveUser;
	}
	public void setCurrentApproveUser(Long currentApproveUser) {
		this.currentApproveUser = currentApproveUser;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getCurrentItemName() {
		return currentItemName;
	}
	public void setCurrentItemName(String currentItemName) {
		this.currentItemName = currentItemName;
	}
	
	
	
}
