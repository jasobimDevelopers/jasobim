package com.my.spring.model;

public class InputLog {
	private Long logId;
	private Integer inputData;//测量数据
	private String userName;
	private String createDate;
	private Integer state;//0、正常 1、异常

	
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public Integer getInputData() {
		return inputData;
	}
	public void setInputData(Integer inputData) {
		this.inputData = inputData;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
