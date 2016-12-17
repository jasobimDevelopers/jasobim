package com.my.spring.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/22.
 */
@Entity
@Table(name = "quantity")
public class Quantity {
    private Long id;
    private String name;
    private Integer professionType;//0 电气  1 暖通 2 给排水  3 消防 4建筑
    private Double  value;
    private Long projectId;
    private String unit;
    private Integer buildingNum;//楼号
    private Integer floorNum;
    private Integer unitNum;
    private Integer householdNum ;//户号
    private String familyAndType;
    private String systemType;//系统类型
    private String serviceType;//设备类型
    private String size;
    private String typeName;
    private String material;//材质
    private Integer quantityType;///工程量来源   0、模型计算出来的工程量  1、预算的工程量
    
    
    @Basic
    @Column(name = "quantity_type")
    public Integer getQuantityType() {
		return quantityType;
	}

	public void setQuantityType(Integer quantityType) {
		this.quantityType = quantityType;
	}

	@Basic
    @Column(name = "value")
    public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

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
    @Column(name = "household_num")
    public Integer getHouseholdNum() {
        return householdNum;
    }

    public void setHouseholdNum(Integer householdNum) {
        this.householdNum = householdNum;
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
    @Column(name = "service_type")
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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
    @Column(name = "material")
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
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
    @Column(name = "profession_type")
	public Integer getProfessionType() {
		return professionType;
	}

	public void setProfessionType(Integer professionType) {
		this.professionType = professionType;
	}

	@Basic
    @Column(name = "unit")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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
    @Column(name = "family_and_type")
	public String getFamilyAndType() {
		return familyAndType;
	}

	public void setFamilyAndType(String familyAndType) {
		this.familyAndType = familyAndType;
	}

	@Basic
    @Column(name = "type_name")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
   

}
