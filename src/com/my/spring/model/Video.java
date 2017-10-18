package com.my.spring.model;

import java.util.Date;

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
	private Integer professionType;//0、质量 1、安全 2、技术
	private String originName;
	private Integer videoType;////0(视频)、1(PDF文档)、2 文本文档（word） 3、其他
	private String intro;
	private Long size;
	private Integer videoGrade;/////交底的等级（0、公司总交底 1、项目交底 2、留底资料 3、通用交底）
	private Long uploadUserId;//////交底上传人id
	private Date uploadDate;///交底上传时间
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
    @Column(name = "size")
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
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
	
	@Basic
    @Column(name = "video_type")
	public Integer getVideoType() {
		return videoType;
	}
	public void setVideoType(Integer videoType) {
		this.videoType = videoType;
	}
	
	@Basic
    @Column(name = "intro")
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	@Basic
	@Column(name = "video_grade")
	public Integer getVideoGrade() {
		return videoGrade;
	}
	public void setVideoGrade(Integer videoGrade) {
		this.videoGrade = videoGrade;
	}
	
	@Basic 
	@Column(name = "upload_user_id")
	public Long getUploadUserId() {
		return uploadUserId;
	}
	public void setUploadUserId(Long uploadUserId) {
		this.uploadUserId = uploadUserId;
	}
	
	@Basic
	@Column(name = "upload_date")
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	

}
