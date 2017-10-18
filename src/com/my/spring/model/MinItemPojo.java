package com.my.spring.model;


/**
 * Created by Administrator on 2016/6/22.
 */
public class MinItemPojo {
    private String projectName;
    private Integer buildingNum;
    private Integer floorNum;
    private Integer unitNum;
    private Integer householdNum;
    private String size;      //尺寸
    private Double length;    //长度
    private String serviceType;//设备类型
    private String familyAndType;
    private String level;    //标高
    private Double offset;   //偏移量
    private Double area;    //面积
    private String systemType;//系统类型
    private String material;//材质
    private String name;   //构件名称
    private String typeName;//类型名
    private Long selfId;
    private Long id;
    private Integer professionType;//专业类型 0、电气 1、暖通 2、给水 3、排水 4.消防



	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getBuildingNum() {
		return buildingNum;
	}

	public void setBuildingNum(Integer buildingNum) {
		this.buildingNum = buildingNum;
	}

	public Integer getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(Integer floorNum) {
		this.floorNum = floorNum;
	}

	public Integer getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(Integer unitNum) {
		this.unitNum = unitNum;
	}

	public Integer getHouseholdNum() {
		return householdNum;
	}

	public void setHouseholdNum(Integer householdNum) {
		this.householdNum = householdNum;
	}


	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getProfessionType() {
		return professionType;
	}

	public void setProfessionType(Integer professionType) {
		this.professionType = professionType;
	}

	public Long getSelfId() {
		return selfId;
	}

	public void setSelfId(Long selfId) {
		this.selfId = selfId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

   
    
}
