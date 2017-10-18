package com.my.spring.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "construction_task")
public class ConstructionTask {
	private Long id;
	private String companyName;//劳务公司名称
	private String workPeopleNameList;
	private Date createDate;//任务单创建时间
	private Long userId;////任务单创建人id
	private String createUserName;///签发人
	private Long receiveUserId;////签收人id
	private String teamName;///班组名称
	private String taskContent;///任务单内容
	private String finishedDate;////完成时间
	private String rewards;////奖惩
	private String detailContent;////任务单交底内容
	private String nextApprovalPeopleType;//任务指派的下一个人的岗位
	private String nextReceivePeopleId;////任务指派下一个人的姓名
	///////审批流程
	private String approvalDateList;///审批时间
	private String approvalPeopleIdeaList;///审批人意见（同意、不同意）
	private String approvalPeopleTypeList;///班组长   →   质量员  →    安全员或其他   →    施工员  →   预算员   →  经理
	private String approvalPeopleNoteList;//审批人批注
	private String approvalPeopleName;///审批人姓名
	private Integer taskFlag;////任务单状态指标(2.待审批  3.已审批 1.已完成  0.未完成)
	private String othersAttention;////任务单其他需要注意的事情
	private Long projectId;////项目id
	private String userProjectIdList;
	
	//////图片
	private String fileIdList;
	
	@Id
    @GeneratedValue
    @Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
    @Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Basic
    @Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Basic
    @Column(name = "finished_date")
	public String getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(String finishedDate) {
		this.finishedDate = finishedDate;
	}
	
	@Basic
    @Column(name = "receive_user_id")
	public Long getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(Long receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	
	@Basic
    @Column(name = "task_content")
	public String getTaskContent() {
		return taskContent;
	}
	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}
	
	@Basic
    @Column(name = "detail_content")
	public String getDetailContent() {
		return detailContent;
	}
	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}
	
	
	@Basic
    @Column(name = "company_name")
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Basic
    @Column(name = "team_name")
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	@Basic
    @Column(name = "rewards")
	public String getRewards() {
		return rewards;
	}
	public void setRewards(String rewards) {
		this.rewards = rewards;
	}
	
	
	
	@Basic
    @Column(name = "task_flag")
	public Integer getTaskFlag() {
		return taskFlag;
	}
	public void setTaskFlag(Integer taskFlag) {
		this.taskFlag = taskFlag;
	}
	
	@Basic
    @Column(name = "others_attention")
	public String getOthersAttention() {
		return othersAttention;
	}
	public void setOthersAttention(String othersAttention) {
		this.othersAttention = othersAttention;
	}
	
	
	@Basic
	@Column(name = "project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@Basic
	@Column(name = "approval_date_list")
	public String getApprovalDateList() {
		return approvalDateList;
	}
	public void setApprovalDateList(String approvalDateList) {
		this.approvalDateList = approvalDateList;
	}
	
	@Basic
	@Column(name = "approval_people_idea_list")
	public String getApprovalPeopleIdeaList() {
		return approvalPeopleIdeaList;
	}
	public void setApprovalPeopleIdeaList(String approvalPeopleIdeaList) {
		this.approvalPeopleIdeaList = approvalPeopleIdeaList;
	}
	
	@Basic
	@Column(name = "approval_people_type_list")
	public String getApprovalPeopleTypeList() {
		return approvalPeopleTypeList;
	}
	public void setApprovalPeopleTypeList(String approvalPeopleTypeList) {
		this.approvalPeopleTypeList = approvalPeopleTypeList;
	}
	
	@Basic
	@Column(name = "approval_people_note_list")
	public String getApprovalPeopleNoteList() {
		return approvalPeopleNoteList;
	}
	public void setApprovalPeopleNoteList(String approvalPeopleNoteList) {
		this.approvalPeopleNoteList = approvalPeopleNoteList;
	}
	
	@Basic
	@Column(name = "user_project_id_list")
	public String getUserProjectIdList() {
		return userProjectIdList;
	}
	public void setUserProjectIdList(String userProjectIdList) {
		this.userProjectIdList = userProjectIdList;
	}
	
	@Basic
	@Column(name = "create_user_name")
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	@Basic
	@Column(name = "approval_people_name")
	public String getApprovalPeopleName() {
		return approvalPeopleName;
	}
	public void setApprovalPeopleName(String approvalPeopleName) {
		this.approvalPeopleName = approvalPeopleName;
	}
	
	@Basic
	@Column(name = "work_people_name_list")
	public String getWorkPeopleNameList() {
		return workPeopleNameList;
	}
	public void setWorkPeopleNameList(String workPeopleNameList) {
		this.workPeopleNameList = workPeopleNameList;
	}
	
	@Basic
	@Column(name = "next_receive_people_id")
	public String getNextReceivePeopleId() {
		return nextReceivePeopleId;
	}
	public void setNextReceivePeopleId(String nextReceivePeopleId) {
		this.nextReceivePeopleId = nextReceivePeopleId;
	}
	
	@Basic
	@Column(name = "next_approval_people_type")
	public String getNextApprovalPeopleType() {
		return nextApprovalPeopleType;
	}
	public void setNextApprovalPeopleType(String nextApprovalPeopleType) {
		this.nextApprovalPeopleType = nextApprovalPeopleType;
	}
	
	@Basic
	@Column(name = "file_id_list")
	public String getFileIdList() {
		return fileIdList;
	}
	public void setFileIdList(String fileIdList) {
		this.fileIdList = fileIdList;
	}
	
	
	
	
	
}
