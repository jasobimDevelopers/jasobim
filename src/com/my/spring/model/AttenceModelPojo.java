package com.my.spring.model;

public class AttenceModelPojo {
	private Long id;
	private String startTime;
	private String endTime;
	private String place;
	private String attenceDay;
	private Integer attenceRange;
	private String userName;
	private String createDate;
	private String projectName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getAttenceDay() {
		return attenceDay;
	}
	public void setAttenceDay(String attenceDay) {
		this.attenceDay = attenceDay;
	}
	
	public Integer getAttenceRange() {
		return attenceRange;
	}
	public void setAttenceRange(Integer attenceRange) {
		this.attenceRange = attenceRange;
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
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}
