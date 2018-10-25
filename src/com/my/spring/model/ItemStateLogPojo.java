package com.my.spring.model;
/**
* @author 徐雨祥
* @version 创建时间：2018年10月25日 上午8:56:44
* 类说明
*/
public class ItemStateLogPojo {
	private Long id;
	private Long projectId;
	private Integer status;//0.安装 1.未安装
	private String actionDate;
	private String realName;
	private Long selfId;
	private String percent;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public Long getSelfId() {
		return selfId;
	}
	public void setSelfId(Long selfId) {
		this.selfId = selfId;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	
}
