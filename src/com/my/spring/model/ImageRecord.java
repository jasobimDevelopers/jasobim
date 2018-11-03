package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
/**
* @author 徐雨祥
* @version 创建时间：2018年10月31日 下午12:53:43
* 类说明
*/
@Entity
@Table(name="image_record")
public class ImageRecord {
	private Long id;
	private Long createUser;
	private Long projectId;
	private Long buildingId;//楼栋信息id
	private Date createDate;
	private Integer projectPart;//分部工程
	private Integer unitPart;//分项工程
	private Date projectPartTime;
	private String projectPartDate;
	private Integer month;
	private String content;
	
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
	@Column(name="create_user")
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
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
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Basic
	@Column(name="project_part")
	public Integer getProjectPart() {
		return projectPart;
	}
	public void setProjectPart(Integer projectPart) {
		this.projectPart = projectPart;
	}
	
	@Basic
	@Column(name="unit_part")
	public Integer getUnitPart() {
		return unitPart;
	}
	public void setUnitPart(Integer unitPart) {
		this.unitPart = unitPart;
	}
	
	
	@Basic
	@Column(name="month")
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	
	@Basic
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Basic
	@Column(name="building_id")
	public Long getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}
	
	@Basic
	@Column(name="project_part_date")
	public String getProjectPartDate() {
		return projectPartDate;
	}
	public void setProjectPartDate(String projectPartDate) {
		this.projectPartDate = projectPartDate;
	}
	
	@Basic
	@Column(name="project_part_time")
	public Date getProjectPartTime() {
		return projectPartTime;
	}
	public void setProjectPartTime(Date projectPartTime) {
		this.projectPartTime = projectPartTime;
	}
	
	
	
}
