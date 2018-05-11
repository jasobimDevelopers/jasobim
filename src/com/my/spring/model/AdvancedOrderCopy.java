package com.my.spring.model;

import java.util.Date;

public class AdvancedOrderCopy {
	private Long id;
	private Long submit_user_id;////预付单提交人
	private String create_user_name;////预付单提交人姓名
	private String project_name;///项目部名称
	private String leader;////项目负责人
	private Integer month;////预付单月份
	private String construct_part;///施工部位
	private String quantity_des;//工程量及应付款
	private Long project_id;///项目id
	private Date create_date;///预付单创建时间
	private Integer status;///预付单当前的进行状态
	private String content_files_id;
	
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
		return construct_part;
	}
	public void setConstructPart(String construct_part) {
		this.construct_part = construct_part;
	}
	
	public String getQuantityDes() {
		return quantity_des;
	}
	public void setQuantityDes(String quantity_des) {
		this.quantity_des = quantity_des;
	}
	
	public Date getCreateDate() {
		return create_date;
	}
	public void setCreateDate(Date create_date) {
		this.create_date = create_date;
	}
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Long getSubmitUserId() {
		return submit_user_id;
	}
	public void setSubmitUserId(Long submit_user_id) {
		this.submit_user_id = submit_user_id;
	}
	
	public String getCreateUserName() {
		return create_user_name;
	}
	public void setCreateUserName(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	
	public String getProjectName() {
		return project_name;
	}
	public void setProjectName(String project_name) {
		this.project_name = project_name;
	}
	
	
	public Long getProjectId() {
		return project_id;
	}
	public void setProjectId(Long project_id) {
		this.project_id = project_id;
	}
	public String getContentFilesId() {
		return content_files_id;
	}
	public void setContentFilesId(String contentFilesId) {
		this.content_files_id = contentFilesId;
	}
	
	
	
}
