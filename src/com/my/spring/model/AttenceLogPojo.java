package com.my.spring.model;

public class AttenceLogPojo {
	private Long id;
	private Integer lateNum;
	private Integer leaveEarlyNum;
	private Integer forgetClockNum;
	private Integer notWorkNum;
	private String userName;
	private Integer workDays;
	private String userIconurl;
	private String workName;
	private Long userId;
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
	

	
	public Integer getLateNum() {
		return lateNum;
	}
	public void setLateNum(Integer lateNum) {
		this.lateNum = lateNum;
	}
	
	
	public Integer getLeaveEarlyNum() {
		return leaveEarlyNum;
	}
	
	public void setLeaveEarlyNum(Integer leaveEarlyNum) {
		this.leaveEarlyNum = leaveEarlyNum;
	}
	
	public Integer getForgetClockNum() {
		return forgetClockNum;
	}
	public void setForgetClockNum(Integer forgetClockNum) {
		this.forgetClockNum = forgetClockNum;
	}
	
	public Integer getNotWorkNum() {
		return notWorkNum;
	}
	public void setNotWorkNum(Integer notWork) {
		this.notWorkNum = notWork;
	}
	public Integer getWorkDays() {
		return workDays;
	}
	public void setWorkDays(Integer workDays) {
		this.workDays = workDays;
	}
	public String getUserIconurl() {
		return userIconurl;
	}
	public void setUserIconurl(String userIconurl) {
		this.userIconurl = userIconurl;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
