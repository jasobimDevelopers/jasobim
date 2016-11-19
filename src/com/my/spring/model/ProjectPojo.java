package com.my.spring.model;



//@Entity
//@Table(name="")
public class ProjectPojo {
	
	private Long id;//工程id
    private String name;//工程名
    private String num;//工程編碼
    private String constructionUnit;//施工單位
    private String leader;//项目负责人
    private String buildingUnit;
    private String picUrl;
    private String modelUrl;
    private String place;//施工地點
    private String description;//項目工程描述
    private String designUnit;//设计单位
    private String version;//版本
    private String startDate;//施工時間
    private String phase;//施工周期
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getConstructionUnit() {
		return constructionUnit;
	}
	public void setConstructionUnit(String constructionUnit) {
		this.constructionUnit = constructionUnit;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getBuildingUnit() {
		return buildingUnit;
	}
	public void setBuildingUnit(String buildingUnit) {
		this.buildingUnit = buildingUnit;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getModelUrl() {
		return modelUrl;
	}
	public void setModelUrl(String string) {
		this.modelUrl = string;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDesignUnit() {
		return designUnit;
	}
	public void setDesignUnit(String designUnit) {
		this.designUnit = designUnit;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
/*	public ProjectPojo(){
		
	}
	public ProjectPojo(Long id,){
		
	}*/
}
