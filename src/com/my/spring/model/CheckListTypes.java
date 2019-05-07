package com.my.spring.model;
import java.util.Date;
import javax.persistence.*;
@Entity
@Table(name = "check_list_types")
public class CheckListTypes {
	private Long checkId;
	private Long createUser;
	private Date createDate;
	private String checkName;
	private Integer checkType;///0、实测实量
	private String remark;
	
	@Id
	@GeneratedValue
	@Column(name="check_id")
	public Long getCheckId() {
		return checkId;
	}
	public void setCheckId(Long checkId) {
		this.checkId = checkId;
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
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Basic
	@Column(name="check_name")
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	
	@Basic
	@Column(name="check_type")
	public Integer getcheckType() {
		return checkType;
	}
	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
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
