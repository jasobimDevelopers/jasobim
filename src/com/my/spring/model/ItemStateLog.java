package com.my.spring.model;

import java.util.Date;
import javax.persistence.*;
/**
* @author 徐雨祥
* @version 创建时间：2018年10月25日 上午8:56:44
* 类说明
*/
@Entity
@Table(name="item_state_log")
public class ItemStateLog {
	private Long id;
	private Long projectId;
	private Integer status;//0.安装 1.未安装
	private Date actionDate;
	private Long userId;
	private Long selfId;
	
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
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@Basic
	@Column(name="status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Basic
	@Column(name="self_id")
	public Long getSelfId() {
		return selfId;
	}
	public void setSelfId(Long selfId) {
		this.selfId = selfId;
	}
	
}
