package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name= "budget")
public class Budget {
	private Long id;
	private String selfId;//序号
	private String projectCode;///项目编号
	private String projectName;///项目名称
	private String unit;//计量单位
	private Double quantity;///工程数量
	private Double quotaOne;//定额单价
	private Double quotaNum;//定额合价
	private Double sumOfMoneyOne;//金额单价
	private Double sumOfMoneyNum;//金额合价
	private Double artificialCostOne;//人工费单价
	private Double artificialCostNum;//人工费合价
	private Double materialsExpensesOne;//材料费单价
	private Double materialsExpensesNum;//材料费合价
	private Double mechanicalFeeOne;//机械费单价
	private Double mechanicalFeeNum;//机械费合价
	private Long budgetBuildingId;
	private Integer indexs;
	private Long projectId;
	private Long userId;
	private Date uploadDate;
	
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
	@Column(name="self_id")
	public String getSelfId() {
		return selfId;
	}
	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}
	
	@Basic
	@Column(name="project_code")
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Basic
	@Column(name="project_name")
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	@Column(name="quantity")
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Basic
	@Column(name="upload_date")
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	@Basic
	@Column(name="quota_one")
	public Double getQuotaOne() {
		return quotaOne;
	}
	public void setQuotaOne(Double quotaOne) {
		this.quotaOne = quotaOne;
	}
	
	@Basic
	@Column(name="quota_num")
	public Double getQuotaNum() {
		return quotaNum;
	}
	public void setQuotaNum(Double quotaNum) {
		this.quotaNum = quotaNum;
	}
	
	@Basic
	@Column(name="sum_of_money_one")
	public Double getSumOfMoneyOne() {
		return sumOfMoneyOne;
	}
	public void setSumOfMoneyOne(Double sumOfMoneyOne) {
		this.sumOfMoneyOne = sumOfMoneyOne;
	}
	
	@Basic
	@Column(name="sum_of_money_num")
	public Double getSumOfMoneyNum() {
		return sumOfMoneyNum;
	}
	public void setSumOfMoneyNum(Double sumOfMoneyNum) {
		this.sumOfMoneyNum = sumOfMoneyNum;
	}
	
	@Basic
	@Column(name="artificial_cost_one")
	public Double getArtificialCostOne() {
		return artificialCostOne;
	}
	public void setArtificialCostOne(Double artificialCostOne) {
		this.artificialCostOne = artificialCostOne;
	}
	
	@Basic
	@Column(name="artificial_cost_num")
	public Double getArtificialCostNum() {
		return artificialCostNum;
	}
	public void setArtificialCostNum(Double artificialCostNum) {
		this.artificialCostNum = artificialCostNum;
	}
	
	@Basic
	@Column(name="materials_expenses_one")
	public Double getMaterialsExpensesOne() {
		return materialsExpensesOne;
	}
	public void setMaterialsExpensesOne(Double materialsExpensesOne) {
		this.materialsExpensesOne = materialsExpensesOne;
	}
	
	@Basic
	@Column(name="materials_expenses_num")
	public Double getMaterialsExpensesNum() {
		return materialsExpensesNum;
	}
	public void setMaterialsExpensesNum(Double materialsExpensesNum) {
		this.materialsExpensesNum = materialsExpensesNum;
	}
	
	@Basic
	@Column(name="mechanical_fee_one")
	public Double getMechanicalFeeOne() {
		return mechanicalFeeOne;
	}
	public void setMechanicalFeeOne(Double mechanicalFeeOne) {
		this.mechanicalFeeOne = mechanicalFeeOne;
	}
	
	@Basic
	@Column(name="mechanical_fee_num")
	public Double getMechanicalFeeNum() {
		return mechanicalFeeNum;
	}
	public void setMechanicalFeeNum(Double mechanicalFeeNum) {
		this.mechanicalFeeNum = mechanicalFeeNum;
	}
	
	@Basic
	@Column(name="budget_building_id")
	public Long getBudgetBuildingId() {
		return budgetBuildingId;
	}
	public void setBudgetBuildingId(Long budgetBuildingId) {
		this.budgetBuildingId = budgetBuildingId;
	}
	
	@Basic
	@Column(name="indexs")
	public Integer getIndexs() {
		return indexs;
	}
	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}
	
}
