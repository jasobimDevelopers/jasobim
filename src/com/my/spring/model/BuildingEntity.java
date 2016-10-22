package com.my.spring.model;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "building")
public class BuildingEntity {
	private Long id;
	private String building_number;
	private String project_number;
	private int floor_num;
	private String building_desc;
	private String building_area;
	private int households_num;
	private int unit_num;
	private String building_height;
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
    @Column(name = "building_number")
    public String getBuilding_number() {
        return building_number;
    }

    public void setBuilding_number(String building_number) {
        this.building_number = building_number;
    }

    @Basic
    @Column(name = "floor_num")
    public int getFloor_num() {
        return floor_num;
    }

    public void setFloor_num(int floor_num) {
        this.floor_num = floor_num;
    }

    @Basic
    @Column(name = "project_number")
    public String getProject_number() {
        return project_number;
    }

    public void setProject_number(String project_number) {
        this.project_number = project_number;
    }

    @Basic
    @Column(name = "building_desc")
    public String getBuilding_desc() {
        return building_desc;
    }

    public void setBuilding_desc(String building_desc) {
        this.building_desc = building_desc;
    }

    @Basic
    @Column(name = "building_area")
    public String getBuilding_area() {
        return building_area;
    }

    public void setBuilding_area(String building_area) {
        this.building_area = building_area;
    }
    @Basic
    @Column(name = "households_num")
    public int getHouseholds_num() {
        return households_num;
    }

    public void setHouseholds_num(int households_num) {
        this.households_num = households_num;
    }
    @Basic
    @Column(name = "unit_num")
    public int getUnit_num() {
        return unit_num;
    }

    public void setUnit_num(int unit_num) {
        this.unit_num = unit_num;
    }
    @Basic
    @Column(name = "building_height")
    public String getBuilding_height() {
        return building_height;
    }

    public void setBuilding_height(String building_height) {
        this.building_height = building_height;
    }
   


}
