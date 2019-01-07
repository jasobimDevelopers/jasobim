package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="quality_check")
public class QualityCheck {
	private Long id;
	private Long projectId;
	private Long createUser;
	private String natureId;
	private Integer noticeType;
	private String checkList;
	private String checkContent;
	private Integer status;/*0、未发整改（默认值，提交时设置 ） 1、已发整改  2、空*/
	private String pictures;
	private String voices;
	private String informUser;/*通知人*/
	private Date createDate;
	private Date updateDate;
	private Long qualityRectificationId;//整改单id
	
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
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
	@Column(name="nature_id")
	public String getNatureId() {
		return natureId;
	}
	public void setNatureId(String natureId) {
		this.natureId = natureId;
	}
	
	@Basic
	@Column(name="notice_type")
	public Integer getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}
	
	@Basic
	@Column(name="check_list")
	public String getCheckList() {
		return checkList;
	}
	public void setCheckList(String checkList) {
		this.checkList = checkList;
	}
	
	@Basic
	@Column(name="check_content")
	public String getCheckContent() {
		return checkContent;
	}
	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
	
	@Basic
	@Column(name="status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Basic
	@Column(name="pictures")
	public String getPictures() {
		return pictures;
	}
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	
	@Basic
	@Column(name="voices")
	public String getVoices() {
		return voices;
	}
	public void setVoices(String voices) {
		this.voices = voices;
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
	@Column(name="update_date")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Basic
	@Column(name="inform_user")
	public String getInformUser() {
		return informUser;
	}
	public void setInformUser(String informUser) {
		this.informUser = informUser;
	}
	
	@Basic
	@Column(name="quality_rectification_id")
	public Long getQualityRectificationId() {
		return qualityRectificationId;
	}
	public void setQualityRectificationId(Long qualityRectificationId) {
		this.qualityRectificationId = qualityRectificationId;
	}
	
	
}
