package com.my.spring.model;
/**
* @author 徐雨祥
* @version 创建时间：2018年9月25日 下午1:14:00
* 类说明
*/
public class OutputValuePojo {
	private Long id;
	private Long createUser;
	private Long projectId;
	private Integer year;
	private Integer month;
	private String createDate;
	private Double armourNum;///本月甲供材
	private Double operatingIncomeNum;//本月经营收入
	private Double lastMonthArmourNum;//上月累计甲供材(当年)
	private Double lastMonthOperatingIncomeNum;///上月累计经营收入（当年）
	private Double allOperatingIncomeNum;//累计经营收入
	private Double allOpeArmourNum;//累计甲供材
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public Double getArmourNum() {
		return armourNum;
	}
	public void setArmourNum(Double armourNum) {
		this.armourNum = armourNum;
	}
	
	public Double getOperatingIncomeNum() {
		return operatingIncomeNum;
	}
	public void setOperatingIncomeNum(Double operatingIncomeNum) {
		this.operatingIncomeNum = operatingIncomeNum;
	}
	
	public Double getLastMonthArmourNum() {
		return lastMonthArmourNum;
	}
	public void setLastMonthArmourNum(Double lastMonthArmourNum) {
		this.lastMonthArmourNum = lastMonthArmourNum;
	}
	
	public Double getLastMonthOperatingIncomeNum() {
		return lastMonthOperatingIncomeNum;
	}
	public void setLastMonthOperatingIncomeNum(Double lastMonthOperatingIncomeNum) {
		this.lastMonthOperatingIncomeNum = lastMonthOperatingIncomeNum;
	}
	
	public Double getAllOperatingIncomeNum() {
		return allOperatingIncomeNum;
	}
	public void setAllOperatingIncomeNum(Double allOperatingIncomeNum) {
		this.allOperatingIncomeNum = allOperatingIncomeNum;
	}
	
	public Double getAllOpeArmourNum() {
		return allOpeArmourNum;
	}
	public void setAllOpeArmourNum(Double allOpeArmourNum) {
		this.allOpeArmourNum = allOpeArmourNum;
	}
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	
	
}
