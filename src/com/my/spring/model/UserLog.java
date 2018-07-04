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
	private Integer projectPart;///针对手机端的功能区域 //0.Model(模型) 1.Paper,//图纸   2.首页//登录  3.Disclose,//项目交底   4.Prefabricate,//进度管理  
								//5.Question,//安全问题 6.Notification,//消息通知 7.Production,//统计管理（产值）    8.UserInfo,//个人中心
								//9.NewsInfo 新闻资讯 10.质量问题  11.实测实量 12.云盘协同  13.物资管理 14.劳动力监测 15.工程量变更 16.考勤管理 17.规范查阅 18.流程管理 
	private String version;
	private Date actionDate;
	private Integer systemType;//0.苹果系统  1.安卓系统 2.web
	private Long fileId;//////模块区域下的文件id
	private Integer actionType;//0.浏览  1.增加 2.登录 3.修改
	
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
	@Column(name = "file_id")
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
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
	
	@Basic
	@Column(name="action_type")
	public Integer getActionType() {
		return actionType;
	}
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
	

}
