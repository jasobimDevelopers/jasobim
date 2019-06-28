package com.my.spring.model;

public class PointConditionsCountNums {
	private String bName;
	private Long bfmId;
	private Long projectId;
	private Integer pointNums;//总点位
	private Integer doneNums;//已测点位
	private Integer problemNums;//爆点点位
	private String buildingNames;//楼栋筛选条件
	private String checkTypes;//检查项筛选条件
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	public Long getBfmId() {
		return bfmId;
	}
	public void setBfmId(Long bfmId) {
		this.bfmId = bfmId;
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
	
}
