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
	private String dayWeather;//白天天气
	private String dayTemperature;//白天温度
	private String dayWindForce;//白天风力
	private String nightWeather;//夜间天气
	private String nightTemperature;//夜间温度
	private String nightWindForce;//夜间风力
	private Integer technologyDiscloseState;//0、无  1、有
	private String technologyDiscloseContent;//交底内容
	private Integer qualityDiscloseState;// 0、无  1、有
	private String qualityDiscloseContent;//质量交底内容
	private Integer safetyDiscloseState;//0、无  1、有
	private String safetyDiscloseContent;//安全交底内容
	private Integer materialDiscloseState;//0、无  1、有
	private String materialDiscloseContent;//材料出、进场记录
	private String cityCode;
	private String projectTender;//项目标段
	
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
	
	@Basic
	@Column(name="day_weather")
	public String getDayWeather() {
		return dayWeather;
	}
	public void setDayWeather(String dayWeather) {
		this.dayWeather = dayWeather;
	}
	
	@Basic
	@Column(name="day_temperature")
	public String getDayTemperature() {
		return dayTemperature;
	}
	public void setDayTemperature(String dayTemperature) {
		this.dayTemperature = dayTemperature;
	}
	
	@Basic
	@Column(name="day_wind_force")
	public String getDayWindForce() {
		return dayWindForce;
	}
	public void setDayWindForce(String dayWindForce) {
		this.dayWindForce = dayWindForce;
	}
	
	@Basic
	@Column(name="night_weather")
	public String getNightWeather() {
		return nightWeather;
	}
	public void setNightWeather(String nightWeather) {
		this.nightWeather = nightWeather;
	}
	
	@Basic
	@Column(name="night_temperature")
	public String getNightTemperature() {
		return nightTemperature;
	}
	public void setNightTemperature(String nightTemperature) {
		this.nightTemperature = nightTemperature;
	}
	
	@Basic
	@Column(name="night_wind_force")
	public String getNightWindForce() {
		return nightWindForce;
	}
	public void setNightWindForce(String nightWindForce) {
		this.nightWindForce = nightWindForce;
	}
	
	@Basic
	@Column(name="project_tender")
	public String getProjectTender() {
		return projectTender;
	}
	public void setProjectTender(String projectTender) {
		this.projectTender = projectTender;
	}
	
	@Basic
	@Column(name="city_code")
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	
}
