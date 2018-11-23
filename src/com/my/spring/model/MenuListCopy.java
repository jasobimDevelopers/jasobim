package com.my.spring.model;

import java.util.Date;

public class MenuListCopy {
	private Long id;
	private Long create_user;
	private String menu_path;
	private String menu_name;
	private String pid;
	private Date create_date;
	private String remark;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCreateUser() {
		return create_user;
	}
	public void setCreateUser(Long create_user) {
		this.create_user = create_user;
	}
	public String getMenuPath() {
		return menu_path;
	}
	public void setMenuPath(String menu_path) {
		this.menu_path = menu_path;
	}
	
	public String getMenuName() {
		return menu_name;
	}
	public void setMenuName(String menuName) {
		this.menu_name = menuName;
	}
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public Date getCreateDate() {
		return create_date;
	}
	public void setCreateDate(Date create_date) {
		this.create_date = create_date;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
