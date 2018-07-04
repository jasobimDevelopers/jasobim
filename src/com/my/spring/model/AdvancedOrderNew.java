package com.my.spring.model;

import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="advanced_order_new")
public class AdvancedOrderNew {
	private Long id;
	private Long submitUserId;////预付单提交人
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
	private Date createDate;///预付单创建时间
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	@Column(name="submit_user_id")
	public Long getSubmitUserId() {
		return submitUserId;
	}
	public void setSubmitUserId(Long submitUserId) {
		this.submitUserId = submitUserId;
	}
	
	@Basic
	@Column(name="create_user_name")
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	@Basic
	@Column(name="project_name")
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Basic
	@Column(name="leader")
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	@Basic
	@Column(name="month")
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	
	@Basic
	@Column(name="construct_part")
	public String getConstructPart() {
		return constructPart;
	}
	public void setConstructPart(String constructPart) {
		this.constructPart = constructPart;
	}
	
	@Basic
	@Column(name="quantity_des")
	public String getQuantityDes() {
		return quantityDes;
	}
	public void setQuantityDes(String quantityDes) {
		this.quantityDes = quantityDes;
	}
	
	@Basic
	@Column(name="process_data_id")
	public Long getProcessDataId() {
		return processDataId;
	}
	public void setProcessDataId(Long processDataId) {
		this.processDataId = processDataId;
	}
	
	@Basic
	@Column(name="user_id_list")
	public String getUserIdList() {
		return userIdList;
	}
	public void setUserIdList(String userIdList) {
		this.userIdList = userIdList;
	}
	
	@Basic
	@Column(name="content_files_id")
	public String getContentFilesId() {
		return contentFilesId;
	}
	public void setContentFilesId(String contentFilesId) {
		this.contentFilesId = contentFilesId;
	}
	
	@Basic
	@Column(name="photo_of_finished")
	public String getPhotoOfFinished() {
		return photoOfFinished;
	}
	public void setPhotoOfFinished(String photoOfFinished) {
		this.photoOfFinished = photoOfFinished;
	}
	
	@Basic
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
