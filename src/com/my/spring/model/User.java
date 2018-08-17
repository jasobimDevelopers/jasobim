package com.my.spring.model;

import java.util.Date;

import javax.persistence.*;



@Entity
@Table(name = "user")
public class User {
	private Long id;
    private String userName;
    private String password;
    private String realName;
    private Integer userType;//0:超级管理员、1:普通用户、2:投资方、3:项目管理人员、4:游客 5:项目工匠（手机功能开放区域：图纸、交底、模型浏览、质量管理、安全管理、规范查阅）
    private String email;
    private String tel;
    private Long userIcon;
    private String userIconUrl;
    private Date registerDate;
    private Date updateDate;
    private Long teamId;
    private Integer systemId;//0.IOS  1.android -1.web
    private Integer systemType;//0.安装人员 1.土建人员
    private Long roleId;
    private Long departmentId;
    private String menuItemList;
    private String workName;//////施工员、资料员、项目负责人（负责考勤模板设置）、技术员、电工、管道工
    
    @Id
    @GeneratedValue
    @Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Basic
    @Column(name = "user_name")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Basic
    @Column(name = "user_icon_url")
	public String getUserIconUrl() {
		return userIconUrl;
	}
	public void setUserIconUrl(String userIconUrl) {
		this.userIconUrl = userIconUrl;
	}

	
	@Basic
    @Column(name = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Basic
    @Column(name = "real_name")
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Basic
    @Column(name = "user_type")
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	@Basic
    @Column(name = "email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Basic
    @Column(name = "tel")
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Basic
    @Column(name = "register_date") 
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	@Basic
    @Column(name = "user_icon") 
	public Long getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(Long userIcon) {
		this.userIcon = userIcon;
	}
	
	@Basic
    @Column(name = "system_id") 
	public Integer getSystemId() {
		return systemId;
	}
	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}
	
	@Basic
    @Column(name = "team_id")
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	@Basic
    @Column(name = "system_type")
	public Integer getSystemType() {
		return systemType;
	}
	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
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
	@Column(name="role_id")
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	@Basic
	@Column(name="department_id")
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	
	@Basic
	@Column(name="menu_item_list")
	public String getMenuItemList() {
		return menuItemList;
	}
	public void setMenuItemList(String menuItemList) {
		this.menuItemList = menuItemList;
	}
	
	@Basic
	@Column(name="work_name")
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	
    
}
