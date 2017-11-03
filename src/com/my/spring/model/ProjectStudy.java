package com.my.spring.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="project_study")
public class ProjectStudy {
	private Integer id;
	private Long projectId;///项目id
	private String content;////文件内容
	private String fileIdList;/////文件id列表
	private String fileTypeList;////文件类型列表（图片、word文档、pdf、视频、其他）
	private Date submitDate;////提交时间
	private Long submitUserId;///提交人id
	private String makeUserName;///学习内容制作人姓名
	private String title;////标题
	private String describe;////描述
	private String remark;//备注
	private Integer studyType;////0、质量   1、安全   2、技术
	
	@Id
	@GeneratedValue
    @Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Basic 
	@Column(name = "file_id_list")
	public String getFileIdList() {
		return fileIdList;
	}
	public void setFileIdList(String fileIdList) {
		this.fileIdList = fileIdList;
	}
	
	@Basic
	@Column(name = "file_type_list")
	public String getFileTypeList() {
		return fileTypeList;
	}
	public void setFileTypeList(String fileTypeList) {
		this.fileTypeList = fileTypeList;
	}
	
	@Basic 
	@Column(name = "submit_date")
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	
	@Basic 
	@Column(name = "submit_user_id")
	public Long getSubmitUserId() {
		return submitUserId;
	}
	public void setSubmitUserId(Long submitUserId) {
		this.submitUserId = submitUserId;
	}
	
	@Basic
	@Column(name = "make_user_name")
	public String getMakeUserName() {
		return makeUserName;
	}
	public void setMakeUserName(String makeUserName) {
		this.makeUserName = makeUserName;
	}
	
	@Basic
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Basic
	@Column(name = "describe")
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	@Basic 
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Basic 
	@Column(name = "study_type")
	public Integer getStudyType() {
		return studyType;
	}
	public void setStudyType(Integer studyType) {
		this.studyType = studyType;
	}
	
	
}
