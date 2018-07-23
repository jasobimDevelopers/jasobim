package com.my.spring.model;

public class AllItemData {
	private Long id;
	private Integer which;
	private String name;
	private Long approve_user;
	public Integer getWhich() {
		return which;
	}
	public void setWhich(Integer which) {
		this.which = which;
	}
	public Long getApprove_user() {
		return approve_user;
	}
	public void setApprove_user(Long approve_user) {
		this.approve_user = approve_user;
	}
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
	
}
