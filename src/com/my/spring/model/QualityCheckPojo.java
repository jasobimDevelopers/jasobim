package com.my.spring.model;

import java.util.List;

public class QualityCheckPojo {
	private Long id;
	private String projectName;
	private String createUserName;
	private List<String> natureId;
	private Integer noticeType;
	private String checkList;
	private String checkContent;
	private Integer status;/*0、已发整改（默认值，提交时设置 ） 1、未发整改  2、空*/
	private List<String> pictures;
	private List<String> voices;
	private List<String> informUser;/*通知人*/
	private String createDate;
	private String updateDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
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
	public List<String> getNatureId() {
		return natureId;
	}
	public void setNatureId(List<String> natureId) {
		this.natureId = natureId;
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
	public List<String> getInformUser() {
		return informUser;
	}
	public void setInformUser(List<String> informUser) {
		this.informUser = informUser;
	}
	
}
