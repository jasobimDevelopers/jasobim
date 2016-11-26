package com.my.spring.model;



//@Entity
//@Table(name="")
public class VideoPojo {
	
    private Long projectId;
    private Integer buildingNum;
    private String url;
    private Integer professionType;
    private String originName;
	
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
	
}
