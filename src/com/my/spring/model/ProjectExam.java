package com.my.spring.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/22.
 */
@Entity
@Table(name = "project_exam")
public class ProjectExam {
    private Long id;
    private String value;
    private String projectid;
    private String buildingid;//楼号
    private String housenum ;//户号
    private String systemtype;//系统类型
    private String devicetype;//设备类型
    private String size;
    private String material;//材质
    private String layernum;//层号
    private String unitnum;//单元号
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
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "projectid")
    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    @Basic
    @Column(name = "buildingid")
    public String getBuildingid() {
        return buildingid;
    }

    public void setBuildingid(String buildingid) {
        this.buildingid = buildingid;
    }

    @Basic
    @Column(name = "housenum")
    public String getHousenum() {
        return housenum;
    }

    public void setHousenum(String housenum) {
        this.housenum = housenum;
    }

    @Basic
    @Column(name = "systemtype")
    public String getSystemtype() {
        return systemtype;
    }

    public void setSystemtype(String systemtype) {
        this.systemtype = systemtype;
    }
    @Basic
    @Column(name = "devicetype")
    public String SetSystemtype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
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
    @Column(name = "layernum")
    public String getLayernum() {
        return layernum;
    }

    public void setLayernum(String layernum) {
        this.layernum = layernum;
    }
    @Basic
    @Column(name = "unitnum")
    public String getUnitnum() {
        return unitnum;
    }

    public void setUnitnum(String unitnum) {
        this.unitnum = unitnum;
    }

}
