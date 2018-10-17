package com.my.spring.model;

import java.util.Date;
import javax.persistence.*;

/**
* @author 徐雨祥
* @version 创建时间：2018年10月12日 下午3:34:15
* 类说明
*/

@Entity
@Table(name="contract_lofting")
public class ContractLofting {
	private Long id;
	private Long projectId;
	private Date createDate;
	private Long createUser;
	private String name;
	private String unit;
	private Long projectPartId;
	private Double projectPartValue;
	private Double sum;//合计
	private Double LimitCoefficient;//限额系数
	private Double LimitNum;///限额量
	private Long pid;
	private String remark;
	
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
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name="unit")
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Basic
	@Column(name="project_part_id")
	public Long getProjectPartId() {
		return projectPartId;
	}
	public void setProjectPartId(Long projectPartId) {
		this.projectPartId = projectPartId;
	}
	
	@Basic
	@Column(name="project_part_value")
	public Double getProjectPartValue() {
		return projectPartValue;
	}
	public void setProjectPartValue(Double projectPartValue) {
		this.projectPartValue = projectPartValue;
	}
	
	@Basic
	@Column(name="sum")
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	
	@Basic
	@Column(name="limit_coefficient")
	public Double getLimitCoefficient() {
		return LimitCoefficient;
	}
	public void setLimitCoefficient(Double limitCoefficient) {
		LimitCoefficient = limitCoefficient;
	}
	
	@Basic
	@Column(name="limit_num")
	public Double getLimitNum() {
		return LimitNum;
	}
	public void setLimitNum(Double limitNum) {
		LimitNum = limitNum;
	}
	
	@Basic
	@Column(name="pid")
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	
	@Basic
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
}
