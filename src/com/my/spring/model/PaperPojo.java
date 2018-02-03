package com.my.spring.model;

public class PaperPojo {
	private Long id;
    private Long projectId;
    private String buildingNum;
    private Integer floorNum;
    private String url;
    private Integer professionType;
    private String originName;
    private Long size;
    
    
	
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
	public String getBuildingNum() {
		return buildingNum;
	}
	public void setBuildingNum(String buildingNum) {
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
	public PaperPojo(Long projectId,String buildingNum,String url,Integer professionType,Integer floorNum) {
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	
}
