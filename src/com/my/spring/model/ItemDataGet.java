package com.my.spring.model;

public class ItemDataGet {

	private String name;
	private Long id;
	private Integer workName;
	private Long approveUser;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getWorkName() {
		return workName;
	}
	public void setWorkName(Integer workName) {
		this.workName = workName;
	}
	public Long getApproveUser() {
		return approveUser;
	}
	public void setApproveUser(Long approveUser) {
		this.approveUser = approveUser;
	}
	
}
