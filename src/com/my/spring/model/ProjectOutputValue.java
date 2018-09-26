package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
/**
* @author 徐雨祥
* @version 创建时间：2018年9月25日 下午2:10:32
* 类说明
*/
@Entity
@Table(name="project_output_value")
public class ProjectOutputValue {
	private Long id;
	private Long projectId;
	private Long createUser;
	private Double contractPrice;//合同价
	private Double contractOutputValue;//合同产值
	private String periodComparison;//工期对比
	private Double armour;//甲供材
	private Double visaChange;//签证变更
	private Date editDate;
	
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
	@Column(name="contract_price")
	public Double getContractPrice() {
		return contractPrice;
	}
	public void setContractPrice(Double contractPrice) {
		this.contractPrice = contractPrice;
	}
	
	@Basic
	@Column(name="contract_output_value")
	public Double getContractOutputValue() {
		return contractOutputValue;
	}
	public void setContractOutputValue(Double contractOutputValue) {
		this.contractOutputValue = contractOutputValue;
	}
	
	@Basic
	@Column(name="period_comparison")
	public String getPeriodComparison() {
		return periodComparison;
	}
	public void setPeriodComparison(String periodComparison) {
		this.periodComparison = periodComparison;
	}
	
	@Basic
	@Column(name="armour")
	public Double getArmour() {
		return armour;
	}
	public void setArmour(Double armour) {
		this.armour = armour;
	}
	
	@Basic
	@Column(name="visa_change")
	public Double getVisaChange() {
		return visaChange;
	}
	public void setVisaChange(Double visaChange) {
		this.visaChange = visaChange;
	}
	
	@Basic
	@Column(name="edit_date")
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
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
