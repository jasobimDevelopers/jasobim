package com.my.spring.model;
/////预付单流程：创建人(项目负责人)-->施工员-->预算员-->项目经理
public class AdvancedOrderNewPojo {
	private Long id;
	private String createUserName;////预付单提交人姓名
	private String projectName;///项目部名称
	private String leader;////项目负责人
	private Integer month;////预付单月份
	private String constructPart;///施工部位
	private String quantityDes;//工程量及应付款
	private Long processDataId;////关联流程id
	private String userIdList;
	private String contentFilesId;///工程量描述图片idList
	private String photoOfFinished;///预算员上传的图片
	private String createDate;///预付单创建时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	
	public String getQuantityDes() {
		return quantityDes;
	}
	public void setQuantityDes(String quantityDes) {
		this.quantityDes = quantityDes;
	}
	
	public Long getProcessDataId() {
		return processDataId;
	}
	public void setProcessDataId(Long processDataId) {
		this.processDataId = processDataId;
	}
	
	public String getUserIdList() {
		return userIdList;
	}
	public void setUserIdList(String userIdList) {
		this.userIdList = userIdList;
	}
	
	public String getContentFilesId() {
		return contentFilesId;
	}
	public void setContentFilesId(String contentFilesId) {
		this.contentFilesId = contentFilesId;
	}
	
	public String getPhotoOfFinished() {
		return photoOfFinished;
	}
	public void setPhotoOfFinished(String photoOfFinished) {
		this.photoOfFinished = photoOfFinished;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
