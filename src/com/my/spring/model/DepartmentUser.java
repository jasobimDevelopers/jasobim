package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name = "department_user")
public class DepartmentUser {
	private Long id;
	private Long departmentId;///所属班组id
	private String name;
	private Integer sex;///0、男  1、女
	private Long workTypeId;///工种/岗位
	private String idCard;//身份证号码
	private String tel;
	private Integer salary;//日工资
	private Long idCardImgZ;///身份证正面
	private Long idCardImgF;///身份证反面
	private Date createDate;
	private Date updateDate;
	private Long createUser;
	private Long updateUser;
	private Long projectId;
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
	@Column(name="department_id")
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	
	@Basic
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name="sex")
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@Basic
	@Column(name="work_type_id")
	public Long getWorkTypeId() {
		return workTypeId;
	}
	public void setWorkTypeId(Long workTypeId) {
		this.workTypeId = workTypeId;
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
	@Column(name="salary")
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	
	@Basic
	@Column(name="id_card_img_z")
	public Long getIdCardImgZ() {
		return idCardImgZ;
	}
	public void setIdCardImgZ(Long idCardImgZ) {
		this.idCardImgZ = idCardImgZ;
	}
	
	@Basic
	@Column(name="id_card_img_f")
	public Long getIdCardImgF() {
		return idCardImgF;
	}
	public void setIdCardImgF(Long idCardImgF) {
		this.idCardImgF = idCardImgF;
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
	@Column(name="update_date")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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
	@Column(name="update_user")
	public Long getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}
	
	@Basic 
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
}
