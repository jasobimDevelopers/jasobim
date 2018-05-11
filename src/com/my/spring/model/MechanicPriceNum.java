package com.my.spring.model;

public class MechanicPriceNum {
	private Long id;
	private String real_name;
	private String work_name;
	private Integer day_salary;
	private Long project_id;
	private Integer num;
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getWork_name() {
		return work_name;
	}
	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}
	public Integer getDay_salary() {
		return day_salary;
	}
	public void setDay_salary(Integer day_salary) {
		this.day_salary = day_salary;
	}
	public Long getProject_id() {
		return project_id;
	}
	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
