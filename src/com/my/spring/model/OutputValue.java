package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
/**
* @author 徐雨祥
* @version 创建时间：2018年9月25日 下午1:14:00
* 类说明
*/
@Entity
@Table(name="output_value")
public class OutputValue {
	private Long id;
	private Long createUser;
	private Long projectId;
	private Integer year;
	private Integer month;
	private Date valueDate;
	private Date createDate;
	private Double armourNum;///本月甲供材
	private Double operatingIncomeNum;//本月经营收入
	//private Double lastMonthArmourNum;//上月累计甲供材(当年)
	//private Double lastMonthOperatingIncomeNum;///上月累计经营收入（当年）
	//private Double allOperatingIncomeNum;//累计经营收入
	//private Double allOpeArmourNum;//累计甲供材
	
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
	@Column(name="month")
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
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
	@Column(name="armour_num")
	public Double getArmourNum() {
		return armourNum;
	}
	public void setArmourNum(Double armourNum) {
		this.armourNum = armourNum;
	}
	
	@Basic
	@Column(name="operating_income_num")
	public Double getOperatingIncomeNum() {
		return operatingIncomeNum;
	}
	public void setOperatingIncomeNum(Double operatingIncomeNum) {
		this.operatingIncomeNum = operatingIncomeNum;
	}
	
	
	/*@Basic
	@Column(name="last_month_armour_num")
	public Double getLastMonthArmourNum() {
		return lastMonthArmourNum;
	}
	public void setLastMonthArmourNum(Double lastMonthArmourNum) {
		this.lastMonthArmourNum = lastMonthArmourNum;
	}
	
	@Basic
	@Column(name="last_month_operating_income_num")
	public Double getLastMonthOperatingIncomeNum() {
		return lastMonthOperatingIncomeNum;
	}
	public void setLastMonthOperatingIncomeNum(Double lastMonthOperatingIncomeNum) {
		this.lastMonthOperatingIncomeNum = lastMonthOperatingIncomeNum;
	}
	
	@Basic
	@Column(name="all_operating_income_num")
	public Double getAllOperatingIncomeNum() {
		return allOperatingIncomeNum;
	}
	public void setAllOperatingIncomeNum(Double allOperatingIncomeNum) {
		this.allOperatingIncomeNum = allOperatingIncomeNum;
	}
	
	@Basic
	@Column(name="all_ope_armour_num")
	public Double getAllOpeArmourNum() {
		return allOpeArmourNum;
	}
	public void setAllOpeArmourNum(Double allOpeArmourNum) {
		this.allOpeArmourNum = allOpeArmourNum;
	}*/
	@Basic
	@Column(name="value_date")
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	@Basic
	@Column(name="year")
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	
	
}
