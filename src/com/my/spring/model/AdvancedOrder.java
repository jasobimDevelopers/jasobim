package com.my.spring.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="advanced_order")
public class AdvancedOrder {
	private Long id;
	private Long submitUserId;////预付单提交人
	private String createUserName;////预付单提交人姓名
	private String projectName;///项目部名称
	private String leader;////项目负责人
	private Integer month;////预付单月份
	private String constructPart;///施工部位
	private String quantityDes;//工程量及应付款
	private String nextApprovalPeopleType;//任务指派的下一个人的岗位
	private String nextReceivePeopleId;////任务指派下一个人的姓名
	
	private String approvalPeopleType;///审批人职位
	private String approvalPeopleName;///审批人姓名
	private String approvalPeopleNote;///审批人批注
	private String approvalPeopleIdea;///审批人意见
	private String approvalUpdateDate;///审批更新时间
	
	private String userProjectIdList;
	private String contentFilesId;///工程量描述图片idList
	private String photoOfFinished;///预算员上传的图片
	private Long projectId;///项目id
	private Date createDate;///预付单创建时间
	private Integer status;///预付单当前的进行状态
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
	@Column(name = "leader")
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	@Basic
	@Column(name = "month")
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	
	@Basic
	@Column(name = "construct_part")
	public String getConstructPart() {
		return constructPart;
	}
	public void setConstructPart(String constructPart) {
		this.constructPart = constructPart;
	}
	
	@Basic
	@Column(name = "quantity_des")
	public String getQuantityDes() {
		return quantityDes;
	}
	public void setQuantityDes(String quantityDes) {
		this.quantityDes = quantityDes;
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
	@Column(name= "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	@Basic
	@Column(name = "submit_user_id")
	public Long getSubmitUserId() {
		return submitUserId;
	}
	public void setSubmitUserId(Long submitUserId) {
		this.submitUserId = submitUserId;
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
	@Column(name = "project_name")
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	@Column(name = "next_receive_people_id")
	public String getNextReceivePeopleId() {
		return nextReceivePeopleId;
	}
	public void setNextReceivePeopleId(String nextReceivePeopleId) {
		this.nextReceivePeopleId = nextReceivePeopleId;
	}
	
	@Basic
	@Column(name = "approval_people_type")
	public String getApprovalPeopleType() {
		return approvalPeopleType;
	}
	public void setApprovalPeopleType(String approvalPeopleType) {
		this.approvalPeopleType = approvalPeopleType;
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
	@Column(name = "approval_people_note")
	public String getApprovalPeopleNote() {
		return approvalPeopleNote;
	}
	public void setApprovalPeopleNote(String approvalPeopleNote) {
		this.approvalPeopleNote = approvalPeopleNote;
	}
	
	@Basic
	@Column(name = "approval_people_idea")
	public String getApprovalPeopleIdea() {
		return approvalPeopleIdea;
	}
	public void setApprovalPeopleIdea(String approvalPeopleIdea) {
		this.approvalPeopleIdea = approvalPeopleIdea;
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
	@Column(name = "content_files_id")
	public String getContentFilesId() {
		return contentFilesId;
	}
	public void setContentFilesId(String contentFilesId) {
		this.contentFilesId = contentFilesId;
	}
	
	@Basic
	@Column(name = "photo_of_finished")
	public String getPhotoOfFinished() {
		return photoOfFinished;
	}
	public void setPhotoOfFinished(String photoOfFinished) {
		this.photoOfFinished = photoOfFinished;
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
	@Column(name = "approval_update_date")
	public String getApprovalUpdateDate() {
		return approvalUpdateDate;
	}
	public void setApprovalUpdateDate(String approvalUpdateDate) {
		this.approvalUpdateDate = approvalUpdateDate;
	}
	
	
}
