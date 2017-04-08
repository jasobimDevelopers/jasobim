  package com.my.spring.model;

import javax.persistence.Basic;
import javax.persistence.Column;





public class DuctLogPojo {
	private Long id;
	private String state;///0.未指定状态  1.出库 2.安装 3.完成 
	private String date;/////当时的时间
	private String userName;///用户id
	
	
    @Column(name = "id")
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Basic
    @Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	
}
