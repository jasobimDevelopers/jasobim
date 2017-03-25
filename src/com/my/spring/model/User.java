package com.my.spring.model;

import java.sql.Date;

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
    private Date registerDate;
    private String projectList;
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
    @Column(name = "user_name")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
    
    
}
