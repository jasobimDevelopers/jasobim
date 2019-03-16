package com.my.spring.model;

import java.util.List;

public class QualityRectificationPojo {
	private Long id;
	private String projectName;
	private String createUserName;
	private Long createUserId;
	private List<String> nature;
	private Integer noticeType;
	private String checkList;
	private String checkContent;
	private Integer status;
	private List<String> pictures;
	private List<String> voices;
	private Integer level;
	private String createDate;
	private String finishedDate;
	private String rectificationContent;
	private List<Long> rectifyUserIds;
	private List<String> rectifyUser;//整改人
	private List<String> copyUser;/*抄送人*/
	private String updateDate;
	private Integer score;/*评分（默认-1）*/
	public String getRectificationContent() {
		return rectificationContent;
	}
	public void setRectificationContent(String rectificationContent) {
		this.rectificationContent = rectificationContent;
	}
	public List<String> getCopyUser() {
		return copyUser;
	}
	public void setCopyUser(List<String> copyUser) {
		this.copyUser = copyUser;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public List<String> getNature() {
		return nature;
	}
	public void setNature(List<String> nature) {
		this.nature = nature;
	}
	public Integer getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}
	public String getCheckList() {
		return checkList;
	}
	public void setCheckList(String checkList) {
		this.checkList = checkList;
	}
	public String getCheckContent() {
		return checkContent;
	}
	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<String> getPictures() {
		return pictures;
	}
	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}
	public List<String> getVoices() {
		return voices;
	}
	public void setVoices(List<String> voices) {
		this.voices = voices;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(String finishedDate) {
		this.finishedDate = finishedDate;
	}
	public List<String> getRectifyUser() {
		return rectifyUser;
	}
	public void setRectifyUser(List<String> rectifyUser) {
		this.rectifyUser = rectifyUser;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public List<Long> getRectifyUserIds() {
		return rectifyUserIds;
	}
	public void setRectifyUserIds(List<Long> rectifyUserIds) {
		this.rectifyUserIds = rectifyUserIds;
	}
	
}
