package com.my.spring.model;

import java.util.Date;

import javax.persistence.*;

/**
* @author 徐雨祥
* @version 创建时间：2018年11月1日 下午4:57:08
* 类说明
*/
@Entity
@Table(name="image_record_building_info")
public class ImageRecordBuildingInfo {
	private Long id;
	private Long projectId;
	private String name;
	private Integer indexs;
	private Date createDate;
	private Long createUser;
	
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
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
	@Column(name="indexs")
	public Integer getIndexs() {
		return indexs;
	}
	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}
	
	@Basic
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Basic
	@Column(name="create_user")
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	
}
