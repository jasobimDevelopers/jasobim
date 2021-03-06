package com.my.spring.model;

import java.util.List;

public class ConstructionTaskNewPojo {
	private Long id;
	private String name;
	private String createUser;
	private Long createUserId;
	private String createDate;
	private String constructionTaskDate;///施工任务单日期
	private Long processDataId;
	private String constructContent;//施工内容
	private String constructType;//施工类型
	private String createUserIcon;
	private Integer teamType;//0、自有技工(自有技工)  1、班组技工（班组信息里面的成员）
	private String teamUserIds;//班组技工人员（12,15,16）
	private List<Long> teamUserIdList;
	private Integer dayWorkHours;//白天工作时间 10小时等于一个工日
	private Integer nightWorkHours;//夜班时间  6小时等于一个工日
	private String tenders;///标段id
	private String constructionName;
	private String constructPart;//施工部位
	private String[] imgs;//(url,url)
	private Integer status;////0、未完成 1、已完成 2、待修改
	private String approvalUser;
	private Long approvalUserId;
	private String currentNodeName;
	private Long currentNodeId;
	private Double salary;
	private Long pid;
	private Long teamId;
	private String teamName;
	private Double dayNums;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public Long getProcessDataId() {
		return processDataId;
	}
	public void setProcessDataId(Long processDataId) {
		this.processDataId = processDataId;
	}
	
	public String getConstructPart() {
		return constructPart;
	}
	public void setConstructPart(String constructPart) {
		this.constructPart = constructPart;
	}
	
	public String getConstructionTaskDate() {
		return constructionTaskDate;
	}
	public void setConstructionTaskDate(String constructionTaskDate) {
		this.constructionTaskDate = constructionTaskDate;
	}
	
	public String getConstructContent() {
		return constructContent;
	}
	public void setConstructContent(String constructContent) {
		this.constructContent = constructContent;
	}
	
	public String getConstructType() {
		return constructType;
	}
	public void setConstructType(String constructType) {
		this.constructType = constructType;
	}
	
	public Integer getTeamType() {
		return teamType;
	}
	public void setTeamType(Integer teamType) {
		this.teamType = teamType;
	}
	
	public String getTeamUserIds() {
		return teamUserIds;
	}
	public void setTeamUserIds(String teamUserIds) {
		this.teamUserIds = teamUserIds;
	}
	
	public Integer getDayWorkHours() {
		return dayWorkHours;
	}
	public void setDayWorkHours(Integer dayWorkHours) {
		this.dayWorkHours = dayWorkHours;
	}
	
	public Integer getNightWorkHours() {
		return nightWorkHours;
	}
	public void setNightWorkHours(Integer nightWorkHours) {
		this.nightWorkHours = nightWorkHours;
	}
	
	public String getTenders() {
		return tenders;
	}
	public void setTenders(String tenders) {
		this.tenders = tenders;
	}
	
	public String getConstructionName() {
		return constructionName;
	}
	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}
	
	public String[] getImgs() {
		return imgs;
	}
	public void setImgs(String[] imgs) {
		this.imgs = imgs;
	}

	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getApprovalUser() {
		return approvalUser;
	}
	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}
	public String getCurrentNodeName() {
		return currentNodeName;
	}
	public void setCurrentNodeName(String currentNodeName) {
		this.currentNodeName = currentNodeName;
	}
	public Long getCurrentNodeId() {
		return currentNodeId;
	}
	public void setCurrentNodeId(Long currentNodeId) {
		this.currentNodeId = currentNodeId;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getApprovalUserId() {
		return approvalUserId;
	}
	public void setApprovalUserId(Long approvalUserId) {
		this.approvalUserId = approvalUserId;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public List<Long> getTeamUserIdList() {
		return teamUserIdList;
	}
	public void setTeamUserIdList(List<Long> teamUserIdList) {
		this.teamUserIdList = teamUserIdList;
	}
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Double getDayNums() {
		return dayNums;
	}
	public void setDayNums(Double dayNums) {
		this.dayNums = dayNums;
	}
	public String getCreateUserIcon() {
		return createUserIcon;
	}
	public void setCreateUserIcon(String createUserIcon) {
		this.createUserIcon = createUserIcon;
	}
	
}
