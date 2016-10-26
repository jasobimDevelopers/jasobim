package com.my.spring.model;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "building")
public class Building {
	private Long id;
	private String name;
	private Long preojectId;
	private Integer buildingNum;
	private Integer floorNum;
	private double area;
	private Integer householdNum;
	private Integer unitNum;
	private String description;
	private double height;
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
    @Column(name = "building_num")
    public Integer getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(Integer buildingNum) {
        this.buildingNum = buildingNum;
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
	@Column(name = "preoject_id")
	public Long getPreojectId() {
		return preojectId;
	}

	public void setPreojectId(Long preojectId) {
		this.preojectId = preojectId;
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
	@Column(name = "area")
	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
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
	@Column(name = "unit_num")
	public Integer getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(Integer unitNum) {
		this.unitNum = unitNum;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "height")
	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}



}
