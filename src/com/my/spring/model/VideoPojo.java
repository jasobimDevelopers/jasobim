package com.my.spring.model;
public class VideoPojo {
    private Long projectId;
    private Long id;
    private Integer buildingNum;
    private String url;
    private Integer professionType;
    private String originName;
    private Integer videoType;
    private String uploadDate;
    private String uploadUserName;
    private Integer videoGrade;
    private String intro; 
    private Long fileId;
    private Long size;
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Integer getBuildingNum() {
		return buildingNum;
	}
	public void setBuildingNum(Integer buildingNum) {
		this.buildingNum = buildingNum;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getProfessionType() {
		return professionType;
	}
	public void setProfessionType(Integer professionType) {
		this.professionType = professionType;
	}
	public VideoPojo(){
		
	}
	public VideoPojo(Long projectId,Integer buildingNum,String url,Integer professionType) {
		this.projectId=projectId;
		this.buildingNum=buildingNum;
		this.url=url;
		this.professionType=professionType;
	}
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public Integer getVideoType() {
		return videoType;
	}
	public void setVideoType(Integer videoType) {
		this.videoType = videoType;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getUploadUserName() {
		return uploadUserName;
	}
	public void setUploadUserName(String uploadUserName) {
		this.uploadUserName = uploadUserName;
	}
	public Integer getVideoGrade() {
		return videoGrade;
	}
	public void setVideoGrade(Integer videoGrade) {
		this.videoGrade = videoGrade;
	}
	
}
