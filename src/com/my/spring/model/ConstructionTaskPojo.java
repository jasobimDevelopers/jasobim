package com.my.spring.model;


public class ConstructionTaskPojo {
	private Long id;
	private String companyName;//劳务公司名称
	private String createDate;//任务单创建时间
	private String createUserName;////任务单创建人姓名
	private String receiveUserName;////签收人姓名
	private String teamName;///班组名称
	private String taskContent;///任务单内容、施工部位
	private String finishedDate;////完成时间
	private String rewards;////奖惩
	private String detailContent;////任务单交底内容
	private String nextReceivePeopleName;
	private String nextApprovalPeopleType;
	private String currentUserName;
	private Integer taskFlag;////任务单状态(0、未完成 1、已完成)
	private String[] approvalPeopleNameList;//审批人姓名
	private String[] approvalDateList;///每次审批的时间数组
	private String[] approvalPeopleIdeaList;//审批人意见数组
	private String[] approvalPeopleNoteList;//审批人批注数组
	private String[] approvalPeopleTypeList;//审批人数组
	private String lastDate;///历时
	private String workPeopleNameList;
	private Integer state;///(0、未审批 1、已审批)
	private String[] fileUrlList;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getReceiveUserName() {
		return receiveUserName;
	}
	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTaskContent() {
		return taskContent;
	}
	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}
	public String getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(String finishedDate) {
		this.finishedDate = finishedDate;
	}
	public String getRewards() {
		return rewards;
	}
	public void setRewards(String rewards) {
		this.rewards = rewards;
	}
	public String getDetailContent() {
		return detailContent;
	}
	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Integer getTaskFlag() {
		return taskFlag;
	}
	public void setTaskFlag(Integer taskFlag) {
		this.taskFlag = taskFlag;
	}
	
	public String getLastDate() {
		return lastDate;
	}
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	public String[] getApprovalDateList() {
		return approvalDateList;
	}
	public void setApprovalDateList(String[] approvalDateList) {
		this.approvalDateList = approvalDateList;
	}
	public String[] getApprovalPeopleIdeaList() {
		return approvalPeopleIdeaList;
	}
	public void setApprovalPeopleIdeaList(String[] approvalPeopleIdeaList) {
		this.approvalPeopleIdeaList = approvalPeopleIdeaList;
	}
	public String[] getApprovalPeopleNoteList() {
		return approvalPeopleNoteList;
	}
	public void setApprovalPeopleNoteList(String[] approvalPeopleNoteList) {
		this.approvalPeopleNoteList = approvalPeopleNoteList;
	}
	public String[] getApprovalPeopleTypeList() {
		return approvalPeopleTypeList;
	}
	public void setApprovalPeopleTypeList(String[] approvalPeopleTypeList) {
		this.approvalPeopleTypeList = approvalPeopleTypeList;
	}
	public String[] getApprovalPeopleNameList() {
		return approvalPeopleNameList;
	}
	public void setApprovalPeopleNameList(String[] approvalPeopleNameList) {
		this.approvalPeopleNameList = approvalPeopleNameList;
	}
	public String getCurrentUserName() {
		return currentUserName;
	}
	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}
	public String getNextReceivePeopleName() {
		return nextReceivePeopleName;
	}
	public void setNextReceivePeopleName(String nextReceivePeopleName) {
		this.nextReceivePeopleName = nextReceivePeopleName;
	}
	public String getNextApprovalPeopleType() {
		return nextApprovalPeopleType;
	}
	public void setNextApprovalPeopleType(String nextApprovalPeopleType) {
		this.nextApprovalPeopleType = nextApprovalPeopleType;
	}
	public String getWorkPeopleNameList() {
		return workPeopleNameList;
	}
	public void setWorkPeopleNameList(String workPeopleNameList) {
		this.workPeopleNameList = workPeopleNameList;
	}
	public String[] getFileUrlList() {
		return fileUrlList;
	}
	public void setFileUrlList(String[] fileUrlList) {
		this.fileUrlList = fileUrlList;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	
	
	
	
}
