package com.my.spring.model;

import java.util.Date;

public class CheckListTypesPojo {
	private Long checkId;
	private Long createUser;
	private Date createDate;
	private String checkName;
	private Integer checkType;///0、实测实量
	private String remark;
	private Integer errorUpperLimit;
    private Integer errorLowerLimit;
    private Integer standardNum;
	public Long getCheckId() {
		return checkId;
	}
	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public Integer getCheckType() {
		return checkType;
	}
	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getErrorUpperLimit() {
		return errorUpperLimit;
	}
	public void setErrorUpperLimit(Integer errorUpperLimit) {
		this.errorUpperLimit = errorUpperLimit;
	}
	public Integer getErrorLowerLimit() {
		return errorLowerLimit;
	}
	public void setErrorLowerLimit(Integer errorLowerLimit) {
		this.errorLowerLimit = errorLowerLimit;
	}
	public Integer getStandardNum() {
		return standardNum;
	}
	public void setStandardNum(Integer standardNum) {
		this.standardNum = standardNum;
	}
    
    
}
