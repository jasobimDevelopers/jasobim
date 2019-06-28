package com.my.spring.model;

public class PointDataInputItem {
	private Long checkTypeId;
	private Integer inputData;
	private String checkTypeName;
	private Integer state;//0、正常 1、异常
	public Long getCheckTypeId() {
		return checkTypeId;
	}
	public void setCheckTypeId(Long checkTypeId) {
		this.checkTypeId = checkTypeId;
	}
	public Integer getInputData() {
		return inputData;
	}
	public void setInputData(Integer inputData) {
		this.inputData = inputData;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getCheckTypeName() {
		return checkTypeName;
	}
	public void setCheckTypeName(String checkTypeName) {
		this.checkTypeName = checkTypeName;
	}
	
}
