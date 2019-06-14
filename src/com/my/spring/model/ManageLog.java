package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="manage_log")
public class ManageLog {
	private Long id;
	private Integer manageType;//0、质量 1、安全 2、实测实量
	private Integer actionType;//0、整改回复  1、复检
	private Date actionDate;
	private Long operateUser;
	private Long aboutId;
	
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
	@Column(name="manage_type")
	public Integer getManageType() {
		return manageType;
	}
	public void setManageType(Integer manageType) {
		this.manageType = manageType;
	}
	
	@Basic
	@Column(name="action_type")
	public Integer getActionType() {
		return actionType;
	}
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
	
	@Basic
	@Column(name="action_date")
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	
	@Basic
	@Column(name="operate_user")
	public Long getOperateUser() {
		return operateUser;
	}
	public void setOperateUser(Long operateUser) {
		this.operateUser = operateUser;
	}
	
	@Basic
	@Column(name="about_id")
	public Long getAboutId() {
		return aboutId;
	}
	public void setAboutId(Long aboutId) {
		this.aboutId = aboutId;
	}
	
}
