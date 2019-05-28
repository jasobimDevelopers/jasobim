package com.my.spring.model;
public class PaperOfMeasuredPojo {
	private Long paperId;
	private Long projectId;
	private String paperName;
	private String fileUrl;
	private Integer measuredNum;
	private Integer paperStatus;//0、已标注 1、未标注
	private String createDate;
	private String createUserName;
	
	public Long getPaperId() {
		return paperId;
	}
	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public Integer getMeasuredNum() {
		return measuredNum;
	}
	public void setMeasuredNum(Integer measuredNum) {
		this.measuredNum = measuredNum;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Integer getPaperStatus() {
		return paperStatus;
	}
	public void setPaperStatus(Integer paperStatus) {
		this.paperStatus = paperStatus;
	}
	
	
}

