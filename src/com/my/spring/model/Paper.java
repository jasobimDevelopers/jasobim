package com.my.spring.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "paper")
public class Paper {
	
	private Long id;
	private Long projectId;
	private String buildingNum;
	private Integer floorNum;
	private Integer professionType;//////专业类型：0. 电气  1.暖通  2.给排水  3.消防  4.建筑 5.装饰 6.结构 7.其他自己定义的专业
	private String diyProfessionType;///自定义的图纸专业
	private Long fileId;
	private String originName;
	private Long size;///图纸文件大小
	
	@Basic
    @Column(name = "origin_name")
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	
	@Basic
    @Column(name = "diy_profession_type")
	public String getDiyProfessionType() {
		return diyProfessionType;
	}
	public void setDiyProfessionType(String diyProfessionType) {
		this.diyProfessionType = diyProfessionType;
	}
	@Basic
    @Column(name = "size")
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
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
    @Column(name = "project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
    @Column(name = "floor_num")
	public Integer getFloorNum() {
		return floorNum;
	}
	public void setFloorNum(Integer floorNum) {
		this.floorNum = floorNum;
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
    @Column(name = "file_id")
	public Long getFileId() {
		return fileId;
	}
	public Long setFileId(Long fileId) {
		return this.fileId = fileId;
	}
	
	
}
