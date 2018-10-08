package com.my.spring.model;

import java.util.Date;
import javax.persistence.*;
@Entity
@Table(name="item_data")
public class ItemData {
	private Long id;
	private String name;
	private Date createDate;
	private Long createUser;
	private Long approveUser;
	private Date updateDate;
	private Integer workName;//0.班组长、1.质量员、2.安全员或其他、3.施工员、4.预算员、5.经理
	private Long updateUser;
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
	@Column(name="approve_user")
	public Long getApproveUser() {
		return approveUser;
	}
	public void setApproveUser(Long approveUser) {
		this.approveUser = approveUser;
	}
	
	@Basic
	@Column(name="update_date")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Basic
	@Column(name="work_name")
	public Integer getWorkName() {
		return workName;
	}
	public void setWorkName(Integer workName) {
		this.workName = workName;
	}
	
	@Basic
	@Column(name="update_user")
	public Long getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}
	
	
}
