package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
/**
* @author 徐雨祥
* @version 创建时间：2018年9月13日 下午4:46:50
* 类说明
*/
@Entity
@Table(name="budget_building")
public class BudgetBuilding {
	private Long id;
	private String name;
	private Long projectId;
	private Date createDate;
	private Long createUser;
	
	@Id
	@GeneratedValue
	@Column
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
