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
    //private Long[] modelId;
    private String modelUrl;
    private String picName;///项目图片文件名
    private String modelName;///项目模型文件名
    private String place;//施工地點
    private String description;//項目工程描述
    private String designUnit;//设计单位
    private String version;//版本
    private String startDate;//施工時間
    private String phase;//施工周期
    private String state;//状态
    private Integer isIos;///1.ios 0.安卓   2.pad模型
    private String[] modelPart;//all.整体	rf.顶层     standard.标准层     B1.地下一层     B2.地下二层      B3.地下三层
    private String[] teamList;
    private String[] teamId;
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
	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Integer getIsIos() {
		return isIos;
	}
	public void setIsIos(Integer isIos) {
		this.isIos = isIos;
	}
	public String[] getModelPart() {
		return modelPart;
	}
	public void setModelPart(String[] modelPart) {
		this.modelPart = modelPart;
	}
	/*public Long[] getModelId() {
		return modelId;
	}
	public void setModelId(Long[] modelId) {
		this.modelId = modelId;
	}*/
	public String[] getTeamList() {
		return teamList;
	}
	public void setTeamList(String[] teamList) {
		this.teamList = teamList;
	}
	public String[] getTeamId() {
		return teamId;
	}
	public void setTeamId(String[] teamId) {
		this.teamId = teamId;
	}
}
