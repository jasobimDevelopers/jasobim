  package com.my.spring.model;


public class ValueOutputPojo {
	private Long id;
	private String others;
	private Double num;    //总产值
	private Double finished;    //已完成
	private String date;/////上传的时间
	private String projectName;///项目id
	
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}
	
	public double getNums() {
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

	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
}
