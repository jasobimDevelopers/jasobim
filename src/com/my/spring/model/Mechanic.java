package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name = "mechanic")
public class Mechanic {
	private Long id;
	private Long projectId;
	private String realName;
	private String idCard;
	private String tel;
	private String workName;////工种
	private int daySalary;
	private String remark;
	private Date createDate;
	private Integer dayHours;
	private Long createUser;
	private Long idCardImg;
	
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
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@Basic
	@Column(name="real_name")
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Basic
	@Column(name="id_card")
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	@Basic
	@Column(name="tel")
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Basic
	@Column(name="work_name")
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	
	@Basic
	@Column(name="day_salary")
	public int getDaySalary() {
		return daySalary;
	}
	public void setDaySalary(int daySalary) {
		this.daySalary = daySalary;
	}
	
	@Basic
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Basic
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Basic
	@Column(name="create_user")
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	
	@Basic
	@Column(name="id_card_img")
	public Long getIdCardImg() {
		return idCardImg;
	}
	public void setIdCardImg(Long idCardImg) {
		this.idCardImg = idCardImg;
	}
	
	@Basic
	@Column(name="day_hours")
	public Integer getDayHours() {
		return dayHours;
	}
	public void setDayHours(Integer dayHours) {
		this.dayHours = dayHours;
	}
	
	
	
	
	
}
