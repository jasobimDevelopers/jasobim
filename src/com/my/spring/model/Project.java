package com.my.spring.model;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "project")
public class Project {
    private Long id;//工程id
    private String name;//工程名
    private String shortName;//项目名简称
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
    private String startDate;//施工开始時間
    private String finishedDate;///结束时间
    private String buildingUnitUser;//建设单位联系人
    private String constructionUnitUser;//施工单位联系人
    private String designUnitUser;//设计单位联系人
    private String constructionControlUnit;///监理单位
    private String constructionControlUser;//监理单位联系人
    private String phase;//施工周期
    private String state;//上线不上线的标志0.未上线 1.已上线
    private String isIos;//1.安卓	 0.ios   2.pad模型
    private String modelPart;//all.整体	rf.顶层     standard.标准层     B1.地下一层     B2.地下二层      B3.地下三层
    private String teamList;
    private String teamId;
    private Date updateDate;
    private Date createDate;
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
    @Column(name = "team_list")
	public String getTeamList() {
		return teamList;
	}

	public void setTeamList(String teamList) {
		this.teamList = teamList;
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
    @Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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
    @Column(name = "finished_date")
	public String getFinishedDate() {
		return finishedDate;
	}

	public void setFinishedDate(String finishedDate) {
		this.finishedDate = finishedDate;
	}

	@Basic
    @Column(name = "phase")
	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	@Basic
    @Column(name = "team_id")
	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	@Basic
    @Column(name = "building_unit_user")
	public String getBuildingUnitUser() {
		return buildingUnitUser;
	}

	public void setBuildingUnitUser(String buildingUnitUser) {
		this.buildingUnitUser = buildingUnitUser;
	}

	@Basic
    @Column(name = "construction_unit_user")
	public String getConstructionUnitUser() {
		return constructionUnitUser;
	}

	public void setConstructionUnitUser(String constructionUnitUser) {
		this.constructionUnitUser = constructionUnitUser;
	}

	@Basic
    @Column(name = "design_unit_user")
	public String getDesignUnitUser() {
		return designUnitUser;
	}

	public void setDesignUnitUser(String designUnitUser) {
		this.designUnitUser = designUnitUser;
	}

	@Basic
	@Column(name = "construction_control_unit")
	public String getConstructionControlUnit() {
		return constructionControlUnit;
	}

	public void setConstructionControlUnit(String constructionControlUnit) {
		this.constructionControlUnit = constructionControlUnit;
	}

	@Basic
	@Column(name = "construction_control_user")
	public String getConstructionControlUser() {
		return constructionControlUser;
	}

	public void setConstructionControlUser(String constructionControlUser) {
		this.constructionControlUser = constructionControlUser;
	}

	@Basic
	@Column(name ="short_name")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Basic
    @Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    
    
}
