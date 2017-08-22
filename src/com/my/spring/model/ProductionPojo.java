package com.my.spring.model;

import javax.persistence.Basic;
import javax.persistence.Column;


public class ProductionPojo {
	private Long id;
	private String projectName;
	private String userName;
	private String buildingNum;//////楼栋号（1#、2#、3#或者地库）
	private String professionType;//专业（电气安装，给排水安装，消防水安装，地库通风安装）
	private String partName;
	private Integer planNum;
	private Integer finishedNum;
	private String date;
	private String someThing;
	

	
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getBuildingNum() {
		return buildingNum;
	}
	public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}
	
	public String getProfessionType() {
		return professionType;
	}
	public void setProfessionType(String professionType) {
		this.professionType = professionType;
	}
	
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	
	public Integer getPlanNum() {
		return planNum;
	}
	public void setPlanNum(Integer planNum) {
		this.planNum = planNum;
	}
	
	public Integer getFinishedNum() {
		return finishedNum;
	}
	public void setFinishedNum(Integer finishedNum) {
		this.finishedNum = finishedNum;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	

	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Basic
    @Column(name = "some_thing")
	public String getSomeThing() {
		return someThing;
	}
	public void setSomeThing(String someThing) {
		this.someThing = someThing;
	}
	
	
	
}
