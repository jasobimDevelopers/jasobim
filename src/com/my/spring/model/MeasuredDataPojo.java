package com.my.spring.model;



public class MeasuredDataPojo {
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
	private String createDate;
	private String submiteUser;
	private String passPercent;
	
	public String getCeliangUser() {
		return celiangUser;
	}
	public void setCeliangUser(String celiangUser) {
		this.celiangUser = celiangUser;
	}
	public String getCheckMoreUser() {
		return checkMoreUser;
	}
	public void setCheckMoreUser(String checkMoreUser) {
		this.checkMoreUser = checkMoreUser;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getSubmiteUser() {
		return submiteUser;
	}
	public void setSubmiteUser(String submiteUser) {
		this.submiteUser = submiteUser;
	}
	
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	

	public String getLeaderUnit() {
		return leaderUnit;
	}
	public void setLeaderUnit(String leaderUnit) {
		this.leaderUnit = leaderUnit;
	}
	

	public String getCheckPart() {
		return checkPart;
	}
	public void setCheckPart(String checkPart) {
		this.checkPart = checkPart;
	}
	

	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	

	public String getCheckContent() {
		return checkContent;
	}
	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
	

	public String getMeasuredData() {
		return measuredData;
	}
	public void setMeasuredData(String measuredData) {
		this.measuredData = measuredData;
	}
	

	public String getQualifiedData() {
		return qualifiedData;
	}
	public void setQualifiedData(String qualifiedData) {
		this.qualifiedData = qualifiedData;
	}
	

	public String getMeasuredDataSum() {
		return measuredDataSum;
	}
	public void setMeasuredDataSum(String measuredDataSum) {
		this.measuredDataSum = measuredDataSum;
	}
	

	public String getQualifiedDataSum() {
		return qualifiedDataSum;
	}
	public void setQualifiedDataSum(String qualifiedDataSum) {
		this.qualifiedDataSum = qualifiedDataSum;
	}
	

	public String getCheckTemplate() {
		return checkTemplate;
	}
	public void setCheckTemplate(String checkTemplate) {
		this.checkTemplate = checkTemplate;
	}
	

	public String getDesignLevel() {
		return designLevel;
	}
	public void setDesignLevel(String designLevel) {
		this.designLevel = designLevel;
	}
	

	public String getLocationSize() {
		return locationSize;
	}
	public void setLocationSize(String locationSize) {
		this.locationSize = locationSize;
	}
	

	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getConstructor() {
		return constructor;
	}
	public void setConstructor(String constructor) {
		this.constructor = constructor;
	}
	
	public String getQuantiter() {
		return quantiter;
	}
	public void setQuantiter(String quantiter) {
		this.quantiter = quantiter;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getPassPercent() {
		return passPercent;
	}
	public void setPassPercent(String passPercent) {
		this.passPercent = passPercent;
	}
}
