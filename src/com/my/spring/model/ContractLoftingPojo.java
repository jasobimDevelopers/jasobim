package com.my.spring.model;

import java.util.List;

/**
* @author 徐雨祥
* @version 创建时间：2018年10月12日 下午3:34:15
* 类说明
*/

public class ContractLoftingPojo {
	private Long id;
	private Long projectId;
	private String createDate;
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
	private List<ProjectPartContractLofting> partList;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Long getProjectPartId() {
		return projectPartId;
	}
	public void setProjectPartId(Long projectPartId) {
		this.projectPartId = projectPartId;
	}
	
	public Double getProjectPartValue() {
		return projectPartValue;
	}
	public void setProjectPartValue(Double projectPartValue) {
		this.projectPartValue = projectPartValue;
	}
	
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	
	public Double getLimitCoefficient() {
		return LimitCoefficient;
	}
	public void setLimitCoefficient(Double limitCoefficient) {
		LimitCoefficient = limitCoefficient;
	}
	
	public Double getLimitNum() {
		return LimitNum;
	}
	public void setLimitNum(Double limitNum) {
		LimitNum = limitNum;
	}
	
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<ProjectPartContractLofting> getPartList() {
		return partList;
	}
	public void setPartList(List<ProjectPartContractLofting> partList) {
		this.partList = partList;
	}
	
	
	
	
	
}
