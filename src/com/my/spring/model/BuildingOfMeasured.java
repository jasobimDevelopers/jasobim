package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="building_of_measured")
public class BuildingOfMeasured {
	private Long bfmId;
	private String bName;
	private Date createDate;
	private Long projectId;
	private Long createUser;
	@Id
	@GeneratedValue
	@Column(name="bfm_id")
	public Long getBfmId() {
		return bfmId;
	}
	public void setBfmId(Long bfmId) {
		this.bfmId = bfmId;
	}
	@Basic
	@Column(name="b_name")
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
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
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
