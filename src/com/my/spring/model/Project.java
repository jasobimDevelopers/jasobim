package com.my.spring.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/22.
 */
@Entity
@Table(name = "project")
public class Project {
    private Long id;//工程id
    private String name;//工程名
    private String num;//工程編碼
    private String constructionUnit;//施工單位
    private String leader;//项目负责人
    private String buildingUnit;
    private String picId;
    private String modelId;
    private String place;//施工地點
    private String description;//項目工程描述
    private String designUnit;//设计单位
    private String version;//版本
    private String startDate;//施工時間
    private String phase;//施工周期
    private String state;//上线不上线的标志0.未上线 1.已上线
    private String isIos;//1.安卓	 0.ios   2.pad模型
    private String modelPart;//all.整体	rf.顶层     standard.标准层     B1.地下一层     B2.地下二层      B3.地下三层
    
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
    @Column(name = "model_part")
	public String getModelPart() {
		return modelPart;
	}

	public void setModelPart(String modelPart) {
		this.modelPart = modelPart;
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
	@Column(name = "is_ios")
	public String getIsIos() {
		return isIos;
	}

	public void setIsIos(String isIos) {
		this.isIos = isIos;
	}
	
	@Basic
    @Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Basic
    @Column(name = "num")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Basic
    @Column(name = "construction_unit")
	public String getConstructionUnit() {
		return constructionUnit;
	}

	public void setConstructionUnit(String constructionUnit) {
		this.constructionUnit = constructionUnit;
	}

	@Basic
    @Column(name = "leader")
	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	@Basic
    @Column(name = "building_unit")
	public String getBuildingUnit() {
		return buildingUnit;
	}

	public void setBuildingUnit(String buildingUnit) {
		this.buildingUnit = buildingUnit;
	}

	@Basic
    @Column(name = "pic_id")
	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	@Basic
    @Column(name = "model_id")
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	@Basic
    @Column(name = "place")
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
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
    @Column(name = "design_unit")
	public String getDesignUnit() {
		return designUnit;
	}

	public void setDesignUnit(String designUnit) {
		this.designUnit = designUnit;
	}

	@Basic
    @Column(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Basic
    @Column(name = "start_date")
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@Basic
    @Column(name = "phase")
	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

    
    
}
