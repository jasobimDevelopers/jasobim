package com.my.spring.model;

import java.util.Date;

import javax.persistence.*;
public class Projectvs {
    private Long id;//工程id
    private String name;//工程名
    private String num;//工程編碼
    private String construction_unit;//施工單位
    private String construction_unit_user;///施工单位负责人
    private String building_unit_user;/////建设单位负责人
    private String design_unit_user;///设计单位负责人
    private String construction_control_unit;////监理单位
    private String construction_control_user;////监理单位负责人
    private String leader;//项目负责人
    private String building_unit;
    private String short_name;
    private String pic_id;
    private String model_id;
    private String place;//施工地點
    private String description;//項目工程描述
    private String design_unit;//设计单位
    private String version;//版本
    private String start_date;//施工开始時間
    private String finished_date;///结束时间
    private String phase;//施工周期
    private String state;//上线不上线的标志0.未上线 1.已上线
    private String is_ios;//1.安卓	 0.ios   2.pad模型
    private String model_part;//all.整体	rf.顶层     standard.标准层     B1.地下一层     B2.地下二层      B3.地下三层
    private String team_list;
    private String team_id;
    private Date update_date;
    private Date create_date;
    
    public Long getId() {
        return id;
    }

    public String getConstruction_unit_user() {
		return construction_unit_user;
	}

	public void setConstruction_unit_user(String construction_unit_user) {
		this.construction_unit_user = construction_unit_user;
	}

	public String getBuilding_unit_user() {
		return building_unit_user;
	}

	public void setBuilding_unit_user(String building_unit_user) {
		this.building_unit_user = building_unit_user;
	}

	public String getDesign_unit_user() {
		return design_unit_user;
	}

	public void setDesign_unit_user(String design_unit_user) {
		this.design_unit_user = design_unit_user;
	}

	public String getConstruction_control_unit() {
		return construction_control_unit;
	}

	public void setConstruction_control_unit(String construction_control_unit) {
		this.construction_control_unit = construction_control_unit;
	}

	public String getConstruction_control_user() {
		return construction_control_user;
	}

	public void setConstruction_control_user(String construction_control_user) {
		this.construction_control_user = construction_control_user;
	}

	public void setId(Long id) {
        this.id = id;
    }

	public String getModel_part() {
		return model_part;
	}

	public void setModel_part(String model_part) {
		this.model_part = model_part;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTeam_list() {
		return team_list;
	}

	public void setTeam_list(String team_list) {
		this.team_list = team_list;
	}
	
	public String getIs_ios() {
		return is_ios;
	}

	public void setIs_ios(String is_ios) {
		this.is_ios = is_ios;
	}
	
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

	public String getConstruction_unit() {
		return construction_unit;
	}

	public void setConstruction_unit(String construction_unit) {
		this.construction_unit = construction_unit;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getBuilding_unit() {
		return building_unit;
	}

	public void setBuilding_unit(String building_unit) {
		this.building_unit = building_unit;
	}

	public String getPic_id() {
		return pic_id;
	}

	public void setPic_id(String pic_id) {
		this.pic_id = pic_id;
	}
	
	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDesign_unit() {
		return design_unit;
	}

	public void setDesign_unit(String design_unit) {
		this.design_unit = design_unit;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	
	public String getFinished_date() {
		return finished_date;
	}

	public void setFinished_date(String finished_date) {
		this.finished_date = finished_date;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	@Basic
    @Column(name = "team_id")
	public String getTeam_id() {
		return team_id;
	}

	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}

	public String getShort_name() {
		return short_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

    
}
