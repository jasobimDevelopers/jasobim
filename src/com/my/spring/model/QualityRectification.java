package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="quality_rectification")
public class QualityRectification {
	private Long id;
	private Long projectId;
	private Long createUser;
	private String natureId;
	private Integer noticeType;
	private String checkList;
	private String checkContent;
	private Integer status;/*0、待整改（默认值，提交时设置 ） 0、待复检  1、已通过*/
	private String rectificationContent;
	private String pictures;
	private String copyUser;/*抄送人*/
	private String voices;
	private Integer level;
	private Date createDate;
	private Date finishedDate;
	private Date updateDate;
	private Integer score;/*评分（默认-1）*/
	private Long qualityCheckId;
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
	@Column(name="level")
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
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
	@Column(name="finished_date")
	public Date getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
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
	@Column(name="score")
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
	@Basic
	@Column(name="copy_user")
	public String getCopyUser() {
		return copyUser;
	}
	public void setCopyUser(String copyUser) {
		this.copyUser = copyUser;
	}
	
	@Basic
	@Column(name="rectification_content")
	public String getRectificationContent() {
		return rectificationContent;
	}
	public void setRectificationContent(String rectificationContent) {
		this.rectificationContent = rectificationContent;
	}
	@Basic
	@Column(name="quality_check_id")
	public Long getQualityCheckId() {
		return qualityCheckId;
	}
	public void setQualityCheckId(Long qualityCheckId) {
		this.qualityCheckId = qualityCheckId;
	}

	
}
