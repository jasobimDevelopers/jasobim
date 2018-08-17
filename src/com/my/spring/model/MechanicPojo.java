package com.my.spring.model;

public class MechanicPojo {
	private Long id;
	private Long projectId;
	private String realName;
	private String idCard;
	private String tel;
	private String workName;////工种
	private int daySalary;
	private String remark;
	private String createDate;
	private String createUser;
	private String idCardImgZ;
	private String idCardImgF;
	private Integer dayHours;
	private Integer sex;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
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
	
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	
	public int getDaySalary() {
		return daySalary;
	}
	public void setDaySalary(int daySalary) {
		this.daySalary = daySalary;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public Integer getDayHours() {
		return dayHours;
	}
	public void setDayHours(Integer dayHours) {
		this.dayHours = dayHours;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
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
	
}
