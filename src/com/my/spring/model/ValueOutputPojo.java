  package com.my.spring.model;

import java.util.Date;

public class ValueOutputPojo {
	private Long id;
	private String others;
	private Double nums;   ///当前项目的总产值
	private Double finisheds;///当前项目已完成
	private Double finished;    //已完成
	private Integer month;
	private String dates;/////上传的时间
	private Date date;
	private String projectName;///项目名称
	private Long project_id;////项目id
	private Integer year;
	private String leader;///项目负责人
	private String userIcon;
	

	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
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

	public void setNums(Double num) {
		this.nums = num;
	}

	public Long getProject_id() {
		return project_id;
	}

	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}


	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	
}
