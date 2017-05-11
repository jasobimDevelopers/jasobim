  package com.my.spring.model;

import javax.persistence.Basic;
import javax.persistence.Column;

import java.util.Date;

import javax.persistence.*;
import javax.persistence.GeneratedValue;

@Entity
@Table(name ="duct")
public class Duct {
	private Long id;
	private String name;
	private String location;////标注是否输入标准层
	private Long projectId;
	private Integer buildingNum;
	private Integer floorNum;
    private Integer unitNum;
    private Integer householdNum;
    private String material;//材质
	private String serviceType;
	private String familyAndType;
	private String size;
	private String level;
	private String selfId;
	private String codeUrl;
	private Double length;    //长度
	private Double area;    //面积
	private Integer state;///0.未指定状态  1.出库 2.安装 3.完成 
	private Date date;/////当时的时间
	private String typeName;//类型名
	private Long userId;///用户id
	private String systemType;////系统类型
	private String modelFlag;/// 模型标志  1.standard  2.rf 3.all 4.B1 5.B2 6.B3
	
	@Id
    @GeneratedValue
    @Column(name = "id")
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}
	
	@Basic
	@Column(name = "length")
	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}
	
	@Basic
	@Column(name = "model_flag")
	public String getModelFlag() {
		return modelFlag;
	}

	public void setModelFlag(String modelFlag) {
		this.modelFlag = modelFlag;
	}
	
	@Basic
	@Column(name = "system_type")
	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	
	@Basic
	@Column(name = "area")
	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	
	@Basic
	@Column(name = "type_name")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Basic
    @Column(name = "date")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Basic
	@Column(name = "building_num")
	public Integer getBuildingNum() {
		return buildingNum;
	}

	public void setBuildingNum(Integer buildingNum) {
		this.buildingNum = buildingNum;
	}

	@Basic
	@Column(name = "floor_num")
	public Integer getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(Integer floorNum) {
		this.floorNum = floorNum;
	}

	@Basic
	@Column(name = "unit_num")
	public Integer getUnitNum() {
		return unitNum;
	}

	@Basic
	@Column(name = "material")
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	public void setUnitNum(Integer unitNum) {
		this.unitNum = unitNum;
	}

	@Basic
	@Column(name = "household_num")
	public Integer getHouseholdNum() {
		return householdNum;
	}

	public void setHouseholdNum(Integer householdNum) {
		this.householdNum = householdNum;
	}

	
	@Basic
    @Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Basic
    @Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
    @Column(name = "code_url")
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	
	@Basic
    @Column(name = "location")
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
    @Column(name = "service_type")
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	@Basic
    @Column(name = "family_and_type")
	public String getFamilyAndType() {
		return familyAndType;
	}
	public void setFamilyAndType(String familyAndType) {
		this.familyAndType = familyAndType;
	}
	
	@Basic
    @Column(name = "size")
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	@Basic
    @Column(name = "level")
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	@Basic
    @Column(name = "self_id")
	public String getSelfId() {
		return selfId;
	}
	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}

	@Basic
    @Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
}
