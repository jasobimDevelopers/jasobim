package com.my.spring.model;


public class AdvancedOrderPojo {
	private Long id;
	private Long submitUserId;////预付单提交人
	private String createUserName;////预付单提交人姓名
	private String createUserIcon;////预付单提交人姓名
	private String projectName;///项目部名称
	private String leader;////项目负责人
	private Integer month;////预付单月份
	private String constructPart;///施工部位
	private String quantityDes;//工程量及应付款
	private String lastTime;
	private String nextApprovalPeopleType;//任务指派的下一个人的岗位
	private String nextReceivePeopleId;////任务指派下一个人的姓名
	
	private String[] approvalPeopleType;///审批人职位
	private String[] approvalPeopleName;///审批人姓名
	private String[] approvalPeopleNote;///审批人批注
	private String[] approvalPeopleIdea;///审批人意见
	private String[] approvalUpdateDate;///审批更新时间
	
	private String userProjectIdList;
	private String[] contentFilesId;///工程量描述图片idList
	private String photoOfFinished;///预算员上传的图片
	private Long projectId;///项目id
	private String createDate;///预付单创建时间
	private Integer status;///预付单当前的进行状态
	
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
	
	
	public String getQuantityDes() {
		return quantityDes;
	}
	public void setQuantityDes(String quantityDes) {
		this.quantityDes = quantityDes;
	}
	public Long getSubmitUserId() {
		return submitUserId;
	}
	public void setSubmitUserId(Long submitUserId) {
		this.submitUserId = submitUserId;
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
	public String getNextApprovalPeopleType() {
		return nextApprovalPeopleType;
	}
	public void setNextApprovalPeopleType(String nextApprovalPeopleType) {
		this.nextApprovalPeopleType = nextApprovalPeopleType;
	}
	public String getNextReceivePeopleId() {
		return nextReceivePeopleId;
	}
	public void setNextReceivePeopleId(String nextReceivePeopleId) {
		this.nextReceivePeopleId = nextReceivePeopleId;
	}
	public String[] getApprovalPeopleType() {
		return approvalPeopleType;
	}
	public void setApprovalPeopleType(String[] approvalPeopleType) {
		this.approvalPeopleType = approvalPeopleType;
	}
	public String[] getApprovalPeopleName() {
		return approvalPeopleName;
	}
	public void setApprovalPeopleName(String[] approvalPeopleName) {
		this.approvalPeopleName = approvalPeopleName;
	}
	public String[] getApprovalPeopleNote() {
		return approvalPeopleNote;
	}
	public void setApprovalPeopleNote(String[] approvalPeopleNote) {
		this.approvalPeopleNote = approvalPeopleNote;
	}
	public String[] getApprovalPeopleIdea() {
		return approvalPeopleIdea;
	}
	public void setApprovalPeopleIdea(String[] approvalPeopleIdea) {
		this.approvalPeopleIdea = approvalPeopleIdea;
	}
	public String[] getApprovalUpdateDate() {
		return approvalUpdateDate;
	}
	public void setApprovalUpdateDate(String[] approvalUpdateDate) {
		this.approvalUpdateDate = approvalUpdateDate;
	}
	public String getUserProjectIdList() {
		return userProjectIdList;
	}
	public void setUserProjectIdList(String userProjectIdList) {
		this.userProjectIdList = userProjectIdList;
	}
	public String[] getContentFilesId() {
		return contentFilesId;
	}
	public void setContentFilesId(String[] contentFilesId) {
		this.contentFilesId = contentFilesId;
	}
	public String getPhotoOfFinished() {
		return photoOfFinished;
	}
	public void setPhotoOfFinished(String photoOfFinished) {
		this.photoOfFinished = photoOfFinished;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public String getCreateUserIcon() {
		return createUserIcon;
	}
	public void setCreateUserIcon(String createUserIcon) {
		this.createUserIcon = createUserIcon;
	}
	
	

	

}
