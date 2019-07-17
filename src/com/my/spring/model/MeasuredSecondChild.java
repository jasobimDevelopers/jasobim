package com.my.spring.model;

import java.util.List;

public class MeasuredSecondChild {
	private String siteName;
	private Long siteId;
	private Long projectId;
	private Integer pointNums;//总点位
	private Integer doneNums;//已测点位
	private Integer problemNums;//爆点点位
	private String buildingNames;//楼栋筛选条件
	private String checkTypes;//检查项筛选条件
	private MeasuredThirdChild child;
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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
	public MeasuredThirdChild getChild() {
		return child;
	}
	public void setChild(MeasuredThirdChild child) {
		this.child = child;
	}
	
}
