package com.my.spring.model;

import java.util.Date;




public class UserCopy {
	private Long id;
    private String user_name;
    private String password;
    private String real_name;
    private Integer user_type;//0:超级管理员、1:普通用户、2:投资方、3:项目负责人/项目人员、4:游客
    private String email;
    private String tel;
    private Long user_icon;
    private String user_icon_url;
    private Date register_date;
    private Date update_date;
    private Long team_id;
    private Integer system_id;//0.IOS  1.android -1.web
    private Integer system_type;//0.安装人员 1.土建人员
    private Long role_id;
    private Long department_id;
    private String menu_item_list;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserName() {
		return user_name;
	}
	public void setUserName(String userName) {
		this.user_name = userName;
	}
	
	public String getUserIconUrl() {
		return user_icon_url;
	}
	public void setUserIconUrl(String userIconUrl) {
		this.user_icon_url = userIconUrl;
	}

	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRealName() {
		return real_name;
	}
	public void setRealName(String realName) {
		this.real_name = realName;
	}
	
	public Integer getUserType() {
		return user_type;
	}
	public void setUserType(Integer userType) {
		this.user_type = userType;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public Date getRegisterDate() {
		return register_date;
	}
	public void setRegisterDate(Date registerDate) {
		this.register_date = registerDate;
	}
	
	public Long getUserIcon() {
		return user_icon;
	}
	public void setUserIcon(Long userIcon) {
		this.user_icon = userIcon;
	}
	
	public Integer getSystemId() {
		return system_id;
	}
	public void setSystemId(Integer systemId) {
		this.system_id = systemId;
	}
	
	public Long getTeamId() {
		return team_id;
	}
	public void setTeamId(Long teamId) {
		this.team_id = teamId;
	}
	
	public Integer getSystemType() {
		return system_type;
	}
	public void setSystemType(Integer systemType) {
		this.system_type = systemType;
	}
	
	public Date getUpdateDate() {
		return update_date;
	}
	public void setUpdateDate(Date updateDate) {
		this.update_date = updateDate;
	}
	
	public Long getRoleId() {
		return role_id;
	}
	public void setRoleId(Long roleId) {
		this.role_id = roleId;
	}
	
	public Long getDepartmentId() {
		return department_id;
	}
	public void setDepartmentId(Long departmentId) {
		this.department_id = departmentId;
	}
	
	public String getMenuItemList() {
		return menu_item_list;
	}
	public void setMenuItemList(String menu_item_list) {
		this.menu_item_list = menu_item_list;
	}
	
    
}
