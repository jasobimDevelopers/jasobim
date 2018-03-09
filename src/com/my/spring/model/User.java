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
    private Integer userType;//0:超级管理员、1:普通管理员、2:安全员、3:质量员、4:施工员、5:资料员、6:材料员、7:Bim工程师
   						///8:技术员、9:预算员、10:项目负责人
    private String	workName;///职位名称
    private String email;
    private String tel;
    private Long userIcon;
    private String userIconUrl;
    private Date registerDate;
    private String projectList;
    private String teamInformation;////班组信息
    private Integer teamId;
    private Integer systemId;//0.IOS  1.android
    private Integer systemType;//0.安装人员 1.土建人员
    private String menuItemList;
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
    @Column(name = "project_list")
	public String getProjectList() {
		return projectList;
	}
	public void setProjectList(String projectList) {
		this.projectList = projectList;
	}
	
	@Basic
    @Column(name = "team_information")
	public String getTeamInformation() {
		return teamInformation;
	}
	public void setTeamInformation(String teamInformation) {
		this.teamInformation = teamInformation;
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
    @Column(name = "work_name")
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
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
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
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
	@Column(name = "menu_item_list")
	public String getMenuItemList() {
		return menuItemList;
	}
	public void setMenuItemList(String menuItemList) {
		this.menuItemList = menuItemList;
	}
    
    
}
