package com.my.spring.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="advanced_order")
public class ProjectFiles {
	private Long id;
	private Long fileIds;
	private Long projectId;
	private Date uploadDate;
	private Long uploadUserId;
	private Integer typeName;//0、图纸会审 1、施工组织设计 2、专项方案 3、签证资料 4、人员备案（特殊工种） 5、花名册 6、施工日志
							 //7、图纸深化及翻样 8、施工资料 9、竣工验收资料 10、竣工图 11、材料复检报告 12、第三方强制检测
							 //13、样板实施计划 14、材料进度计划
	private String test;
	private Integer fileType;///0、excel 1、png/jpg图片 2、word 3、pdf
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
	@Column(name = "file_ids")
	public Long getFileIds() {
		return fileIds;
	}
	public void setFileIds(Long fileIds) {
		this.fileIds = fileIds;
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
	@Column(name = "upload_date")
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
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
	@Column(name = "type_name")
	public Integer getTypeName() {
		return typeName;
	}
	public void setTypeName(Integer typeName) {
		this.typeName = typeName;
	}
	
	@Basic
	@Column(name = "test")
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	
	@Basic
	@Column(name = "file_type")
	public Integer getFileType() {
		return fileType;
	}
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
	
	
}
