package com.my.spring.model;
public class BudgetPojo {
	private Long id;
	private String selfId;//序号
	private String projectCode;///项目编号
	private String projectName;///项目名称
	private String unit;//计量单位
	private Double quantity;///工程数量
	private Double quotaOne;//定额单价
	private Double quotaNum;//定额合价
	private Double sumOfMoneyOne;//金额单价
	private Double sumOfMoneyNum;//金额合价
	private Double artificialCostOne;//人工费单价
	private Double artificialCostNum;//人工费合价
	private Double materialsExpensesOne;//材料费单价
	private Double materialsExpensesNum;//材料费合价
	private Double mechanicalFeeOne;//机械费单价
	private Double mechanicalFeeNum;//机械费合价
	private Long projectId;
	private String userId;
	private String buildingInfo;
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
	
	
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Double getQuotaOne() {
		return quotaOne;
	}
	public void setQuotaOne(Double quotaOne) {
		this.quotaOne = quotaOne;
	}
	public Double getQuotaNum() {
		return quotaNum;
	}
	public void setQuotaNum(Double quotaNum) {
		this.quotaNum = quotaNum;
	}
	public Double getSumOfMoneyOne() {
		return sumOfMoneyOne;
	}
	public void setSumOfMoneyOne(Double sumOfMoneyOne) {
		this.sumOfMoneyOne = sumOfMoneyOne;
	}
	public Double getSumOfMoneyNum() {
		return sumOfMoneyNum;
	}
	public void setSumOfMoneyNum(Double sumOfMoneyNum) {
		this.sumOfMoneyNum = sumOfMoneyNum;
	}
	public Double getArtificialCostOne() {
		return artificialCostOne;
	}
	public void setArtificialCostOne(Double artificialCostOne) {
		this.artificialCostOne = artificialCostOne;
	}
	public Double getArtificialCostNum() {
		return artificialCostNum;
	}
	public void setArtificialCostNum(Double artificialCostNum) {
		this.artificialCostNum = artificialCostNum;
	}
	public Double getMaterialsExpensesOne() {
		return materialsExpensesOne;
	}
	public void setMaterialsExpensesOne(Double materialsExpensesOne) {
		this.materialsExpensesOne = materialsExpensesOne;
	}
	public Double getMaterialsExpensesNum() {
		return materialsExpensesNum;
	}
	public void setMaterialsExpensesNum(Double materialsExpensesNum) {
		this.materialsExpensesNum = materialsExpensesNum;
	}
	public Double getMechanicalFeeOne() {
		return mechanicalFeeOne;
	}
	public void setMechanicalFeeOne(Double mechanicalFeeOne) {
		this.mechanicalFeeOne = mechanicalFeeOne;
	}
	public Double getMechanicalFeeNum() {
		return mechanicalFeeNum;
	}
	public void setMechanicalFeeNum(Double mechanicalFeeNum) {
		this.mechanicalFeeNum = mechanicalFeeNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBuildingInfo() {
		return buildingInfo;
	}
	public void setBuildingInfo(String buildingInfo) {
		this.buildingInfo = buildingInfo;
	}
	
	
	
	
	
}
