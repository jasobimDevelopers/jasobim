package com.my.spring.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="measured_data_new")
public class MeasuredDataNew {
	private Long id;
	private Long projectId;///项目id
	private Date createDate;///创建时间
	private Integer measuredNum;//测量点位总数
	private String buildingNum;//楼栋号
	private Integer troubleNum;//爆点个数
	private Integer measuredDone;//已测点位数
	
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
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	@Column(name = "measured_num")
	public Integer getMeasuredNum() { 
		return measuredNum;
	}
	public void setMeasuredNum(Integer measuredNum) {
		this.measuredNum = measuredNum;
	}
	
	@Basic
	@Column(name = "building_num")
	public String getBuildingNum() {
		return buildingNum;
	}
	public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}
}
