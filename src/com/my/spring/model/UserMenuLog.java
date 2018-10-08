package com.my.spring.model;
import javax.persistence.*;
/**
* @author 徐雨祥
* @version 创建时间：2018年9月27日 上午9:53:18
* 类说明
*/
@Entity
@Table(name="user_menu_log")
public class UserMenuLog {
	private Long id;
	private Long userId;
	private String menuPath;
	private Long menuPid;
	private String menuChildId;
	private Integer lockStatus;//0、未锁定 1、锁定
	
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
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Basic
	@Column(name="menu_path")
	public String getMenuPath() {
		return menuPath;
	}
	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}
	
	@Basic
	@Column(name="lock_status")
	public Integer getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}
	
	@Basic
	@Column(name="menu_pid")
	public Long getMenuPid() {
		return menuPid;
	}
	public void setMenuPid(Long menuPid) {
		this.menuPid = menuPid;
	}
	
	@Basic
	@Column(name="menu_child_id")
	public String getMenuChildId() {
		return menuChildId;
	}
	public void setMenuChildId(String menuChildId) {
		this.menuChildId = menuChildId;
	}
	
	
	
}
