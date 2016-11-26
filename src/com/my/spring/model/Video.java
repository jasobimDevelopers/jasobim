package com.my.spring.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "video")
public class Video {
	private Long id;
	private Long fileId;
	private Long projectId;
	private Integer buildingNum;
	private Integer professionType;
	private String originName;
	
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
    @Column(name = "file_id")
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
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
    @Column(name = "profession_type")
	public Integer getProfessionType() {
		return professionType;
	}
	public void setProfessionType(Integer professionType) {
		this.professionType = professionType;
	}
	
	@Basic
    @Column(name = "building_num")
	public Integer getBuildingNum() {
		return buildingNum;
	}
	public void setBuildingNum(Integer buildingNum) {
		this.buildingNum = buildingNum;
	}
	
	@Basic
    @Column(name = "origin_name")
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	

}
