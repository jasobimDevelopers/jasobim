package com.my.spring.model;


public class AdvancedOrderCollectPojo {
	private Long id;
	private String createDate;///创建时间
	private String submitUserName;////预付单提交人
	private String createUserName;////预付单提交人姓名
	private String projectName;///项目部名称
	private String leader;////承包人姓名
	private Integer month;////预付单月份
	private String constructPart;///施工部位
	private Long currentFinished;//本期完成额
	private Long beforeFinished;//上期累计已付结额
	private String leaderName;//项目经理签字
	private String constructorName;///施工员签字
	private String quantityerName;//预算员签字
	
	private Long advancedOrderId;///预付单id
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getCurrentFinished() {
		return currentFinished;
	}
	public void setCurrentFinished(Long currentFinished) {
		this.currentFinished = currentFinished;
	}
	public Long getBeforeFinished() {
		return beforeFinished;
	}
	public void setBeforeFinished(Long beforeFinished) {
		this.beforeFinished = beforeFinished;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getConstructorName() {
		return constructorName;
	}
	public void setConstructorName(String constructorName) {
		this.constructorName = constructorName;
	}
	public String getQuantityerName() {
		return quantityerName;
	}
	public void setQuantityerName(String quantityerName) {
		this.quantityerName = quantityerName;
	}
	public Long getAdvancedOrderId() {
		return advancedOrderId;
	}
	public void setAdvancedOrderId(Long advancedOrderId) {
		this.advancedOrderId = advancedOrderId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	
	public String getConstructPart() {
		return constructPart;
	}
	public void setConstructPart(String constructPart) {
		this.constructPart = constructPart;
	}
		
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getSubmitUserName() {
		return submitUserName;
	}
	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	

	

}
