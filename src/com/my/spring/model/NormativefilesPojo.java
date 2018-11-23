package com.my.spring.model;

public class NormativefilesPojo {
	private Long id;
	private String projectName;///项目id
	private String content;////文件内容
	private String[] fileUrlList;/////文件id列表
	private String[] fileNameList;///文件名称列表
	private String submitDate;////提交时间
	private String submitUserName;///提交人id
	private String title;////标题
	private String describe;////描述
	private String remark;//备注
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

	public String[] getFileUrlList() {
		return fileUrlList;
	}
	public void setFileUrlList(String[] fileIdList) {
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
	public String[] getFileNameList() {
		return fileNameList;
	}
	public void setFileNameList(String[] fileNameList) {
		this.fileNameList = fileNameList;
	}
	public NormativefilesPojo(){
		
	}
	public NormativefilesPojo(String projectName,String content,String[] fileUrlList,
			String[] fileNameList,String submitDate,
			String submitUserName,String title,String describe,
			String remark,Integer studyType,Long size){
		this.content=content;
		this.projectName=projectName;
		this.fileUrlList=fileUrlList;
		this.remark=remark;
		this.size=size;
		this.studyType=studyType;
		this.submitDate=submitDate;
		this.submitUserName=submitUserName;
		this.describe=describe;
		this.title=title;		
	}
	
}
