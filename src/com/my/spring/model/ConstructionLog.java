package com.my.spring.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "construction_log")
public class ConstructionLog {
	private Long id;
	private Long createUserId;//创建人id
	private String createUserName;
	private Date createDate;
	private Long projectId;//项目id
	private String constructionDateStr;//施工日志日期
	private Date constructionDate;
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

	
	@Id
	@GeneratedValue
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic 
	@Column(name = "weather")
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	
	@Basic 
	@Column(name = "create_user_id")
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	
	@Basic 
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Basic 
	@Column(name = "project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@Basic 
	@Column(name = "construction_date")
	public Date getConstructionDate() {
		return constructionDate;
	}
	public void setConstructionDate(Date constructionDate) {
		this.constructionDate = constructionDate;
	}
	
	@Basic 
	@Column(name = "emergency_state")
	public Integer getEmergencyState() {
		return emergencyState;
	}
	public void setEmergencyState(Integer emergencyState) {
		this.emergencyState = emergencyState;
	}
	
	@Basic 
	@Column(name = "temperature")
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	@Basic 
	@Column(name = "wind_force")
	public String getWindForce() {
		return windForce;
	}
	public void setWindForce(String windForce) {
		this.windForce = windForce;
	}
	
	@Basic 
	@Column(name = "technology_disclose_state")
	public Integer getTechnologyDiscloseState() {
		return technologyDiscloseState;
	}
	public void setTechnologyDiscloseState(Integer technologyDiscloseState) {
		this.technologyDiscloseState = technologyDiscloseState;
	}
	
	@Basic 
	@Column(name = "technology_disclose_content")
	public String getTechnologyDiscloseContent() {
		return technologyDiscloseContent;
	}
	public void setTechnologyDiscloseContent(String technologyDiscloseContent) {
		this.technologyDiscloseContent = technologyDiscloseContent;
	}
	
	@Basic 
	@Column(name = "quality_disclose_state")
	public Integer getQualityDiscloseState() {
		return qualityDiscloseState;
	}
	public void setQualityDiscloseState(Integer qualityDiscloseState) {
		this.qualityDiscloseState = qualityDiscloseState;
	}
	
	@Basic 
	@Column(name = "quality_disclose_content")
	public String getQualityDiscloseContent() {
		return qualityDiscloseContent;
	}
	public void setQualityDiscloseContent(String qualityDiscloseContent) {
		this.qualityDiscloseContent = qualityDiscloseContent;
	}
	
	@Basic 
	@Column(name = "safety_disclose_state")
	public Integer getSafetyDiscloseState() {
		return safetyDiscloseState;
	}
	public void setSafetyDiscloseState(Integer safetyDiscloseState) {
		this.safetyDiscloseState = safetyDiscloseState;
	}
	
	@Basic 
	@Column(name = "safety_disclose_content")
	public String getSafetyDiscloseContent() {
		return safetyDiscloseContent;
	}
	public void setSafetyDiscloseContent(String safetyDiscloseContent) {
		this.safetyDiscloseContent = safetyDiscloseContent;
	}
	
	@Basic 
	@Column(name = "material_disclose_state")
	public Integer getMaterialDiscloseState() {
		return materialDiscloseState;
	}
	public void setMaterialDiscloseState(Integer materialDiscloseState) {
		this.materialDiscloseState = materialDiscloseState;
	}
	
	@Basic 
	@Column(name = "material_disclose_content")
	public String getMaterialDiscloseContent() {
		return materialDiscloseContent;
	}
	public void setMaterialDiscloseContent(String materialDiscloseContent) {
		this.materialDiscloseContent = materialDiscloseContent;
	}
	
	@Basic 
	@Column(name = "construction_date_str")
	public String getConstructionDateStr() {
		return constructionDateStr;
	}
	public void setConstructionDateStr(String constructionDateStr) {
		this.constructionDateStr = constructionDateStr;
	}
	@Basic
	@Column(name="create_user_name")
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	
	
}
