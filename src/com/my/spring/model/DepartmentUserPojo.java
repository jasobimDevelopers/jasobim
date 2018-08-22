package com.my.spring.model;
public class DepartmentUserPojo {
	private Long id;
	private String team;///所属班组id
	private String name;
	private String sex;///0、男  1、女
	private String workTypeName;///工种/岗位
	private Integer userTeamType;
	private String idCard;//身份证号码
	private String tel;
	private Integer salary;//日工资
	private String idCardImgZ;///身份证正面
	private String idCardImgF;///身份证反面
	private String createDate;
	private String updateDate;
	private String createUser;
	private String updateUser;
	private Long projectId;
	private Long userTeamId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getWorkTypeName() {
		return workTypeName;
	}
	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	public String getIdCardImgZ() {
		return idCardImgZ;
	}
	public void setIdCardImgZ(String idCardImgZ) {
		this.idCardImgZ = idCardImgZ;
	}
	public String getIdCardImgF() {
		return idCardImgF;
	}
	public void setIdCardImgF(String idCardImgF) {
		this.idCardImgF = idCardImgF;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Integer getUserTeamType() {
		return userTeamType;
	}
	public void setUserTeamType(Integer userTeamType) {
		this.userTeamType = userTeamType;
	}
	public Long getUserTeamId() {
		return userTeamId;
	}
	public void setUserTeamId(Long userTeamId) {
		this.userTeamId = userTeamId;
	}
	
	
	
}
