  package com.my.spring.model;

import javax.persistence.Basic;
import javax.persistence.Column;

import java.util.Date;

import javax.persistence.*;
import javax.persistence.GeneratedValue;

@Entity
@Table(name ="value_output")
public class ValueOutput {
	private Long id;
	private String others;
	private Double num;    //总产值
	private Double finished;    //已完成
	private Date date;/////上传的时间
	private Long projectId;///项目id
	
	@Id
    @GeneratedValue
    @Column(name = "id")
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}
	
	@Basic
	@Column(name = "num")
	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}
	
	@Basic
	@Column(name = "finished")
	public double getFinished() {
		return finished;
	}

	public void setFinished(double finished) {
		this.finished = finished;
	}

	@Basic
	@Column(name = "others")
	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	
	@Basic
    @Column(name = "date")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Basic
    @Column(name = "project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	
}
