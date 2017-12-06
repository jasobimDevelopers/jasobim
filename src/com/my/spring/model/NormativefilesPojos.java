package com.my.spring.model;


public class NormativefilesPojos {
	private Long id;
	private String projectName;///项目id
	private String content;////文件内容
	private String fileUrlList;/////文件id
	private String fileNameList;///文件名称
	private String submitDate;////提交时间
	private String submitUserName;///提交人id
	private String title;////标题
	private String describe;////描述
	private String remark;//备注
	private Long fileId;
	private Integer studyType;////0、质量   1、安全   2、技术
	private Long size;

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

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getFileUrlList() {
		return fileUrlList;
	}
	public void setFileUrlList(String fileIdList) {
		this.fileUrlList = fileIdList;
	}
	
	
	
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	
	
	public String getSubmitUserName() {
		return submitUserName;
	}
	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}
	

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Integer getStudyType() {
		return studyType;
	}
	public void setStudyType(Integer studyType) {
		this.studyType = studyType;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getFileNameList() {
		return fileNameList;
	}
	public void setFileNameList(String fileNameList) {
		this.fileNameList = fileNameList;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	
	
}
