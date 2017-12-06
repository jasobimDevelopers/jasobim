package com.my.spring.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table( name= "measured_data")
public class MeasuredData {
	private Long id;
	private String projectName;
	private String leaderUnit;
	private String checkPart;
	private String checkUser;
	private String checkType;//0.检查内容 1.评判标准 2.实测点数 3.合格点数 4.设计标高 5.定位尺寸 6.原始数据 7.备份
	private String checkContent;//0.户内强电箱 1.户内弱电箱 2.盒子墙面的垂直度 3.开关 4.厨房插座 5.客厅或卧室插座
								///6.阳台插座 7.闭路电视 8.红外幕帘 9.网络电话 10.手动报警按钮 11.客厅空调插座 12.插座
	private String measuredData;//实测点数
	private String qualifiedData;////合格点数
	private String measuredDataSum;///实测点数总计
	private String qualifiedDataSum;///合格点数总计
	private String checkTemplate;//评判标准
	private String designLevel;//设计标高
	private String locationSize;//定位尺寸
	private String leaderName;
	private String constructor;
	private String celiangUser;  //测量人姓名
	private String checkMoreUser;///复查人姓名
	private String quantiter;
	private Date createDate;
	private String submiteUser;
	
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
	@Column(name = "project_name")
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Basic 
	@Column(name = "leader_unit")
	public String getLeaderUnit() {
		return leaderUnit;
	}
	public void setLeaderUnit(String leaderUnit) {
		this.leaderUnit = leaderUnit;
	}
	
	@Basic 
	@Column(name = "check_part")
	public String getCheckPart() {
		return checkPart;
	}
	public void setCheckPart(String checkPart) {
		this.checkPart = checkPart;
	}
	
	@Basic 
	@Column(name = "check_type")
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	
	@Basic 
	@Column(name = "check_content")
	public String getCheckContent() {
		return checkContent;
	}
	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
	
	@Basic 
	@Column(name = "measured_data")
	public String getMeasuredData() {
		return measuredData;
	}
	public void setMeasuredData(String measuredData) {
		this.measuredData = measuredData;
	}
	
	@Basic 
	@Column(name = "qualified_data")
	public String getQualifiedData() {
		return qualifiedData;
	}
	public void setQualifiedData(String qualifiedData) {
		this.qualifiedData = qualifiedData;
	}
	
	@Basic 
	@Column(name = "measured_data_sum")
	public String getMeasuredDataSum() {
		return measuredDataSum;
	}
	public void setMeasuredDataSum(String measuredDataSum) {
		this.measuredDataSum = measuredDataSum;
	}
	
	@Basic 
	@Column(name = "qualified_data_sum")
	public String getQualifiedDataSum() {
		return qualifiedDataSum;
	}
	public void setQualifiedDataSum(String qualifiedDataSum) {
		this.qualifiedDataSum = qualifiedDataSum;
	}
	
	@Basic 
	@Column(name = "check_template")
	public String getCheckTemplate() {
		return checkTemplate;
	}
	public void setCheckTemplate(String checkTemplate) {
		this.checkTemplate = checkTemplate;
	}
	
	@Basic 
	@Column(name = "design_level")
	public String getDesignLevel() {
		return designLevel;
	}
	public void setDesignLevel(String designLevel) {
		this.designLevel = designLevel;
	}
	
	@Basic 
	@Column(name = "location_size")
	public String getLocationSize() {
		return locationSize;
	}
	public void setLocationSize(String locationSize) {
		this.locationSize = locationSize;
	}
	
	@Basic 
	@Column(name = "leader_name")
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	
	@Basic 
	@Column(name = "constructor")
	public String getConstructor() {
		return constructor;
	}
	public void setConstructor(String constructor) {
		this.constructor = constructor;
	}
	
	@Basic 
	@Column(name = "quantiter")
	public String getQuantiter() {
		return quantiter;
	}
	public void setQuantiter(String quantiter) {
		this.quantiter = quantiter;
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
	@Column(name = "submite_user")
	public String getSubmiteUser() {
		return submiteUser;
	}
	public void setSubmiteUser(String submiteUser) {
		this.submiteUser = submiteUser;
	}
	
	@Basic
	@Column(name= "check_user")
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	
	@Basic
	@Column(name= "celiang_user")
	public String getCeliangUser() {
		return celiangUser;
	}
	public void setCeliangUser(String celiangUser) {
		this.celiangUser = celiangUser;
	}
	
	@Basic
	@Column(name= "check_more_user")
	public String getCheckMoreUser() {
		return checkMoreUser;
	}
	public void setCheckMoreUser(String checkMoreUser) {
		this.checkMoreUser = checkMoreUser;
	}
}
