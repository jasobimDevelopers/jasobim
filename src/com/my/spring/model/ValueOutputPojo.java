  package com.my.spring.model;

import java.util.Date;

public class ValueOutputPojo {
	private Long id;
	private String others;
	private Double nums;   ///当前项目的总产值
	private Double finisheds;///当前项目已完成
	private Double num;    //单个项目总产值
	private Double finished;    //已完成
	private Date date;
	private String dates;/////上传的时间
	private String projectName;///项目名称
	private Long project_id;////项目id
	private String leader;///项目负责人
	private String projectPicUrl;
	
	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getProjectPicUrl() {
		return projectPicUrl;
	}

	public void setProjectPicUrl(String projectPicUrl) {
		this.projectPicUrl = projectPicUrl;
	}

	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}
	
	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}
	
	
	public double getFinished() {
		return finished;
	}

	public void setFinished(double finished) {
		this.finished = finished;
	}

	
	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Double getFinisheds() {
		return finisheds;
	}

	public void setFinisheds(Double finisheds) {
		this.finisheds = finisheds;
	}

	public Double getNums() {
		return nums;
	}

	public void setNums(Double nums) {
		this.nums = nums;
	}

	public Long getProject_id() {
		return project_id;
	}

	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}

	
}
