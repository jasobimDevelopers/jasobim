package com.my.spring.model;

import javax.persistence.*;


@Entity
@Table(name = "item")
public class Item {
    private Long id;
    private Long projectId;
    private Integer buildingNum;
    private Integer floorNum;
    private Integer unitNum;
    private Integer householdNum;
    private String location;//构件所属具体位置（项目A-楼号B-单元C-楼层D-户E）
    private Double bottomElevation;//底部高程
    private String size;      //尺寸
    private Double length;    //长度
    private String serviceType;//设备类型
    private String familyAndType;
    private String level;    //标高
    private Double offset;   //偏移量
    private Double area;    //面积
    private String systemType;//系统类型
    private String material;//材质
    private Long selfId;  //模型中对应的id
    private String name;   //构件名称
    private String typeName;//类型名
    private Integer professionType;//专业类型
    private String modelFlag;//////模型标志
    
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
    @Column(name = "model_flag")
	public String getModelFlag() {
		return modelFlag;
	}

	public void setModelFlag(String modelFlag) {
		this.modelFlag = modelFlag;
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
	@Column(name = "location")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Basic
	@Column(name = "bottom_elevation")
	public double getBottomElevation() {
		return bottomElevation;
	}

	public void setBottomElevation(double bottomElevation) {
		this.bottomElevation = bottomElevation;
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
	@Column(name = "length")
	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
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
	@Column(name = "level")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Basic
	@Column(name = "offset")
	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
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
	@Column(name = "system_type")
	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	@Basic
	@Column(name = "material")
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Basic
	@Column(name = "self_id")
	public Long getSelfId() {
		return selfId;
	}

	public void setSelfId(Long selfId) {
		this.selfId = selfId;
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
	@Column(name = "type_name")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Basic
	@Column(name = "profession_type")
	public Integer getProfessionType() {
		return professionType;
	}

	public void setProfessionType(Integer professionType) {
		this.professionType = professionType;
	}

   
    
}
