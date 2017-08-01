package com.my.spring.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_log")
public class UserLog {
	private Long id;
	private Long userId;
	private Long projectId;
	private Integer projectPart; //0.Model(模型) 1.Paper,//图纸   2.Login,//登录  3.Disclose,//交底   4.Prefabricate,//预制化   
								//5.Question,//紧急事项（问题）6.Notification,//通知 7.Production,//产值    8.Member,//班组信息 
	private String version;
	private Date actionDate;
	private Integer systemType;//0.苹果系统  1.安卓系统
	
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
	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	@Column(name = "project_part")
	public Integer getProjectPart() {
		return projectPart;
	}
	public void setProjectPart(Integer projectPart) {
		this.projectPart = projectPart;
	}
	@Basic
	@Column(name = "version")
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Basic
	@Column(name = "action_date")
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	@Basic
	@Column(name = "system_type")
	public Integer getSystemType() {
		return systemType;
	}
	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	}
	

}
