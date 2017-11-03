package com.my.spring.model;


public class ProjectStudyPojo {
	private Integer id;
	private String projectName;///项目id
	private String content;////文件内容
	private String fileIdList;/////文件id列表
	private String fileTypeList;////文件类型列表（图片、word文档、pdf、视频、其他）
	private String submitDate;////提交时间
	private String submitUserName;///提交人id
	private String makeUserName;///学习内容制作人姓名
	private String title;////标题
	private String describe;////描述
	private String remark;//备注
	private Integer studyType;////0、质量   1、安全   2、技术
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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

	public String getFileIdList() {
		return fileIdList;
	}
	public void setFileIdList(String fileIdList) {
		this.fileIdList = fileIdList;
	}
	
	
	public String getFileTypeList() {
		return fileTypeList;
	}
	public void setFileTypeList(String fileTypeList) {
		this.fileTypeList = fileTypeList;
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
	
	public String getMakeUserName() {
		return makeUserName;
	}
	public void setMakeUserName(String makeUserName) {
		this.makeUserName = makeUserName;
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
	
	
}
