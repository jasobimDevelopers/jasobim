  package com.my.spring.model;







public class DuctPojo {
	private String id;
	private String name;
	private String location;////标注是否输入标准层
	private String projectId;
	private String serviceType;
	private String familyAndType;
	private String size;
	private String level;
	private String selfId;
	private String codeUrl;
	private String length;    //长度
	private String area;    //面积
	private String state;///0.未指定状态  1.出库 2.安装 3.完成 
	private String date;/////当时的时间
	private String userId;///用户id
	private String typeName;//类型名
	private String systemType;////系统类型
	private String userName;
	private String buildingNum;
	private String floorNum;
    private String unitNum;
    private String householdNum;
    private String material;//材质
    private String projectName;
    private String modelFlag;
    public String getBuildingNum() {
		return buildingNum;
	}

	public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}

	public String getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(String unitNum) {
		this.unitNum = unitNum;
	}

	public String getHouseholdNum() {
		return householdNum;
	}

	public void setHouseholdNum(String householdNum) {
		this.householdNum = householdNum;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id=id;
	}
	

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	

	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	

	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	

	public String getFamilyAndType() {
		return familyAndType;
	}
	public void setFamilyAndType(String familyAndType) {
		this.familyAndType = familyAndType;
	}
	

	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	

	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	

	

	public String getSelfId() {
		return selfId;
	}
	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getModelFlag() {
		return modelFlag;
	}

	public void setModelFlag(String modelFlag) {
		this.modelFlag = modelFlag;
	}
	
}
