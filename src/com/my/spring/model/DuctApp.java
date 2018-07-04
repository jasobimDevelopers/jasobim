package com.my.spring.model;

import java.util.Date;

public class DuctApp {
	private Long id;
	private String name;
	private String size;
	private String service_type;
	private String family_and_type;
	private Integer profession_type;///0、电气     1、暖通
	private String self_id;
	private Date date;
	private Integer state;
	private Integer nums;
	private Double areas;
	private Double lengths;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	public String getFamily_and_type() {
		return family_and_type;
	}
	public void setFamily_and_type(String family_and_type) {
		this.family_and_type = family_and_type;
	}
	public String getSelf_id() {
		return self_id;
	}
	public void setSelf_id(String self_id) {
		this.self_id = self_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getNums() {
		return nums;
	}
	public void setNums(Integer nums) {
		this.nums = nums;
	}
	public Double getAreas() {
		return areas;
	}
	public void setAreas(Double areas) {
		this.areas = areas;
	}
	public Double getLengths() {
		return lengths;
	}
	public void setLengths(Double lengths) {
		this.lengths = lengths;
	}
	public Integer getProfession_type() {
		return profession_type;
	}
	public void setProfession_type(Integer profession_type) {
		this.profession_type = profession_type;
	}
	
}
