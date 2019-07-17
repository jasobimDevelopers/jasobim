package com.my.spring.model;

import java.util.Date;
import java.util.List;

public class MeasuredInfoPojo {
	private Long id;
	private Long userId;
	private Long projectId;
	private Integer pointNums;//总点位
	private Integer doneNums;//已测点位
	private Integer problemNums;//爆点点位
	private String buildingNames;//楼栋筛选条件
	private String checkTypes;//检查项筛选条件
	private Date createDate;
	private List<MeasuredFirstChild> child;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Integer getPointNums() {
		return pointNums;
	}
	public void setPointNums(Integer pointNums) {
		this.pointNums = pointNums;
	}
	public Integer getDoneNums() {
		return doneNums;
	}
	public void setDoneNums(Integer doneNums) {
		this.doneNums = doneNums;
	}
	public Integer getProblemNums() {
		return problemNums;
	}
	public void setProblemNums(Integer problemNums) {
		this.problemNums = problemNums;
	}
	public String getBuildingNames() {
		return buildingNames;
	}
	public void setBuildingNames(String buildingNames) {
		this.buildingNames = buildingNames;
	}
	public String getCheckTypes() {
		return checkTypes;
	}
	public void setCheckTypes(String checkTypes) {
		this.checkTypes = checkTypes;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<MeasuredFirstChild> getChild() {
		return child;
	}
	public void setChild(List<MeasuredFirstChild> child) {
		this.child = child;
	}
	
}
