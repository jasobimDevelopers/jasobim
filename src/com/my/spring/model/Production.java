package com.my.spring.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "production")
public class Production {
	private Long id;
	private Long projectId;
	private Long userId;
	private String buildingNum;//////楼栋号（1#、2#、3#或者地库）
	private String professionType;//专业（电气安装，给排水安装，消防水安装，地库通风安装）
	private String partName;
	private Integer planNum;
	private Integer finishedNum;
	private Date date;
	private String someThing;
	

	
	
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
    @Column(name = "building_num")
	public String getBuildingNum() {
		return buildingNum;
	}
	public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}
	
	@Basic
    @Column(name = "profession_type")
	public String getProfessionType() {
		return professionType;
	}
	public void setProfessionType(String professionType) {
		this.professionType = professionType;
	}
	
	
	@Basic
    @Column(name = "part_name")
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	
	@Basic
    @Column(name = "plan_num")
	public Integer getPlanNum() {
		return planNum;
	}
	public void setPlanNum(Integer planNum) {
		this.planNum = planNum;
	}
	
	@Basic
    @Column(name = "finished_num")
	public Integer getFinishedNum() {
		return finishedNum;
	}
	public void setFinishedNum(Integer finishedNum) {
		this.finishedNum = finishedNum;
	}
	
	@Basic
    @Column(name = "date")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
    @Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
