package com.my.spring.model;

import java.util.Date;

/**
* @author 徐雨祥
* @version 创建时间：2018年9月29日 下午2:38:31
* 类说明
*/
public class ItemDataIndex {
	private Long id;
	private Long indexs;
	private String name;
	private Long approveUser;
	private Date createDate;
	private Long createUser;
	private Integer workName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIndexs() {
		return indexs;
	}
	public void setIndexs(Long indexs) {
		this.indexs = indexs;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getApproveUser() {
		return approveUser;
	}
	public void setApproveUser(Long approveUser) {
		this.approveUser = approveUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	public Integer getWorkName() {
		return workName;
	}
	public void setWorkName(Integer workName) {
		this.workName = workName;
	}
	
}
