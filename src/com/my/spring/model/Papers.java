package com.my.spring.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/22.
 */
@Entity
@Table(name = "papers")
public class Papers {
    private Long id;
    private int buildingid;
    private int floor;
    private int fileid;
    private Integer type;

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
    @Column(name = "buildingid")
    public int getBuildingid() {
		return buildingid;
	}

	public void setBuildingid(int buildingid) {
		this.buildingid = buildingid;
	}

	@Basic
    @Column(name = "floor")
	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	@Basic
    @Column(name = "fileid")
	public int getFileid() {
		return fileid;
	}

	public void setFileid(int fileid) {
		this.fileid = fileid;
	}
	
	@Basic
    @Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/*public Papers(Long id,int buildingid,int floor,int fileid,Integer type){
    	this.id=id;
    	this.buildingid=buildingid;
    	this.floor=floor;
    	this.fileid=fileid;
    	this.type=type;
    }*/
    
}
