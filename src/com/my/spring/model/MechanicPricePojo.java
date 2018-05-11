package com.my.spring.model;

import java.util.List;

public class MechanicPricePojo {
	private Long id;
	private String userName;
	private String workName;
	private Integer hour;
	private String editDate;
	private String createDate;
	private List<MechanicPrice> mp;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Integer getHour() {
		return hour;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
	}
	
	public String getEditDate() {
		return editDate;
	}
	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public List<MechanicPrice> getMp() {
		return mp;
	}
	public void setMp(List<MechanicPrice> mp) {
		this.mp = mp;
	}
	
	
}
