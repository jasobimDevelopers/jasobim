package com.my.spring.model;

import java.util.Date;

/**
* @author 徐雨祥
* @version 创建时间：2018年9月29日 下午1:22:13
* 类说明
*/
public class RoleIndex {
	private Long id;
	private Long indexs;
	private String name;
	private String menuList;
	private Integer readState;
	private Date createDate;
	private Long createUser;
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
	public String getMenuList() {
		return menuList;
	}
	public void setMenuList(String menuList) {
		this.menuList = menuList;
	}
	public Integer getReadState() {
		return readState;
	}
	public void setReadState(Integer readState) {
		this.readState = readState;
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
	
}
