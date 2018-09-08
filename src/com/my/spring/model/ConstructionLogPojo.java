package com.my.spring.model;

import java.util.List;

public class ConstructionLogPojo {
	private Long id;
	private Long createUserId;
	private String createDate;
	private String constructionDate;//施工日志日期
	private Integer emergencyState;//0、无 1、有(突发事件)
	private String weather;//天气
	private String temperature;//温度
	private String windForce;//风力
	private Integer technologyDiscloseState;//0、无  1、有
	private String technologyDiscloseContent;//交底内容
	private Integer qualityDiscloseState;// 0、无  1、有
	private String qualityDiscloseContent;//质量交底内容
	private Integer safetyDiscloseState;//0、无  1、有
	private String safetyDiscloseContent;//安全交底内容
	private Integer materialDiscloseState;//0、无  1、有
	private String materialDiscloseContent;//材料出、进场记录
	private String createUserName;
	private Long projectId;
	private List<ProductionRecords> productionRecordsList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getConstructionDate() {
		return constructionDate;
	}
	public void setConstructionDate(String constructionDate) {
		this.constructionDate = constructionDate;
	}
	public Integer getEmergencyState() {
		return emergencyState;
	}
	public void setEmergencyState(Integer emergencyState) {
		this.emergencyState = emergencyState;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getWindForce() {
		return windForce;
	}
	public void setWindForce(String windForce) {
		this.windForce = windForce;
	}
	public Integer getTechnologyDiscloseState() {
		return technologyDiscloseState;
	}
	public void setTechnologyDiscloseState(Integer technologyDiscloseState) {
		this.technologyDiscloseState = technologyDiscloseState;
	}
	public String getTechnologyDiscloseContent() {
		return technologyDiscloseContent;
	}
	public void setTechnologyDiscloseContent(String technologyDiscloseContent) {
		this.technologyDiscloseContent = technologyDiscloseContent;
	}
	public Integer getQualityDiscloseState() {
		return qualityDiscloseState;
	}
	public void setQualityDiscloseState(Integer qualityDiscloseState) {
		this.qualityDiscloseState = qualityDiscloseState;
	}
	public String getQualityDiscloseContent() {
		return qualityDiscloseContent;
	}
	public void setQualityDiscloseContent(String qualityDiscloseContent) {
		this.qualityDiscloseContent = qualityDiscloseContent;
	}
	public Integer getSafetyDiscloseState() {
		return safetyDiscloseState;
	}
	public void setSafetyDiscloseState(Integer safetyDiscloseState) {
		this.safetyDiscloseState = safetyDiscloseState;
	}
	public String getSafetyDiscloseContent() {
		return safetyDiscloseContent;
	}
	public void setSafetyDiscloseContent(String safetyDiscloseContent) {
		this.safetyDiscloseContent = safetyDiscloseContent;
	}
	public Integer getMaterialDiscloseState() {
		return materialDiscloseState;
	}
	public void setMaterialDiscloseState(Integer materialDiscloseState) {
		this.materialDiscloseState = materialDiscloseState;
	}
	public String getMaterialDiscloseContent() {
		return materialDiscloseContent;
	}
	public void setMaterialDiscloseContent(String materialDiscloseContent) {
		this.materialDiscloseContent = materialDiscloseContent;
	}
	public List<ProductionRecords> getProductionRecordsList() {
		return productionRecordsList;
	}
	public void setProductionRecordsList(List<ProductionRecords> productionRecordsList) {
		this.productionRecordsList = productionRecordsList;
	}
	
	
}
