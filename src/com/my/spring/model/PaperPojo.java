package com.my.spring.model;

public class PaperPojo {
	
    private Long projectId;
    private Integer buildingNum;
    private Integer floorNum;
    private String url;
    private Integer professionType;
    private String originName;
    
    
	
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
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
	public PaperPojo(){
		
	}
	public PaperPojo(Long projectId,Integer buildingNum,String url,Integer professionType,Integer floorNum) {
		this.projectId=projectId;
		this.buildingNum=buildingNum;
		this.url=url;
		this.professionType=professionType;
		this.floorNum=floorNum;
	}
	public Integer getFloorNum() {
		return floorNum;
	}
	public void setFloorNum(Integer floorNum) {
		this.floorNum = floorNum;
	}
	
}
