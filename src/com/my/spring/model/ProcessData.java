package com.my.spring.model;

import java.util.Date;
import javax.persistence.*;
@Entity
@Table(name="process_data")
public class ProcessData {
	private Long id;
	private String name;
	private Integer itemNum;
	private Date createDate;
	private Long createUser;
	private Long projectId;
	private Integer type;//0.施工任务单 1.预付单
	
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
	@Column(name="item_num")
	public Integer getItemNum() {
		return itemNum;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
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
	
	@Basic
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@Basic
	@Column(name="type")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
}
