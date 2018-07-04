package com.my.spring.model;

public class DuctConfig {
	private Integer aboutId;
	private String name;
	private Integer type;//0.安装状态  1.专业  2.楼层
	
	
	public Integer getAboutId() {
		return aboutId;
	}
	public void setAboutId(Integer aboutId) {
		this.aboutId = aboutId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
