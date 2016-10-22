package com.my.spring.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/22.
 */
@Entity
@Table(name = "projects")
public class ProjectEntity {
    private Long id;//工程id
    private String project_name;//工程名
    private String project_number;//工程編碼
    private String construction_unit;//施工單位
    private String project_leader;//施工方
    private String project_adress;//施工地點
    private String project_desc;//項目工程描述
    private String project_area;//工程面積
    private String start_time;//施工時間
    private String time_length;//施工周期
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
    @Column(name = "project_name")
    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String projectName) {
        this.project_name = projectName;
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
    @Column(name = "construction_unit")
    public String getConstruction_unit() {
        return construction_unit;
    }

    public void setConstruction_unit(String construction_unit) {
        this.construction_unit = construction_unit;
    }

    @Basic
    @Column(name = "project_leader")
    public String getProject_leader() {
        return project_leader;
    }

    public void setProject_leader(String project_leader) {
        this.project_leader = project_leader;
    }

    @Basic
    @Column(name = "project_adress")
    public String getProject_adress() {
        return project_adress;
    }

    public void setProject_adress(String project_adress) {
        this.project_adress = project_adress;
    }
    @Basic
    @Column(name = "project_desc")
    public String getProject_desc() {
        return project_desc;
    }

    public void setProject_desc(String project_desc) {
        this.project_desc = project_desc;
    }
    @Basic
    @Column(name = "project_area")
    public String getProject_area() {
        return project_area;
    }

    public void setProject_area(String project_area) {
        this.project_area = project_area;
    }
    @Basic
    @Column(name = "start_time")
    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
    @Basic
    @Column(name = "time_length")
    public String getTime_length() {
        return time_length;
    }

    public void setTime_length(String time_length) {
        this.time_length = time_length;
    }

   
}
