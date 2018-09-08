package com.my.spring.model;

import javax.persistence.*;

/**
* @author 徐雨祥
* @version 创建时间：2018年8月31日 上午11:05:54
* 类说明
*/
@Entity
@Table(name="construct_part")
public class ConstructPart {
	private Long id;
	private String name;
	private Long projectId;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
}
