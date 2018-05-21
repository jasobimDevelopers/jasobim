package com.my.spring.model;

public class BudgetPojo {
	private Long id;
	private String selfId;
	private String projectCode;
	private String projectName;
	private String projectDescription;
	private String unit;
	private Double quantity;
	private Double onePrice;
	private Double priceNum;
	private Double maybePrice;
	private Long projectId;
	private Long userId;
	private String uploadDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getSelfId() {
		return selfId;
	}
	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	public Double getOnePrice() {
		return onePrice;
	}
	public void setOnePrice(Double onePrice) {
		this.onePrice = onePrice;
	}
	
	public Double getPriceNum() {
		return priceNum;
	}
	public void setPriceNum(Double priceNum) {
		this.priceNum = priceNum;
	}
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	public Double getMaybePrice() {
		return maybePrice;
	}
	public void setMaybePrice(Double maybePrice) {
		this.maybePrice = maybePrice;
	}
	
	
	
	
}
