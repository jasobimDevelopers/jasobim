package com.my.spring.model;

import java.util.Date;

import javax.persistence.*;

/**
* @author 徐雨祥
* @version 创建时间：2018年10月12日 下午3:20:32
* 类说明
*/
@Entity
@Table(name="project_part_contract_lofting")
public class ProjectPartContractLofting {
	private Long id;
	private String name;
	private Long projectId;
	private Long contractLoftingId;
	private Date createDate;
	private Long createUser;
	private Double value;
	private String pName;
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
	@Column(name="value")
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	@Basic
	@Column(name="p_name")
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	
	@Basic
	@Column(name="contract_lofting_id")
	public Long getContractLoftingId() {
		return contractLoftingId;
	}
	public void setContractLoftingId(Long contractLoftingId) {
		this.contractLoftingId = contractLoftingId;
	}
	
}
