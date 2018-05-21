package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name= "budget")
public class Budget {
	private Long id;
	private String selfId;
	private String projectCode;
	private String projectName;
	private String projectDescription;
	private String unit;
	private Double quantity;
	private Double onePrice;
	private Double priceNum;
	private Double maybePrice;
	private Long projectId;
	private Long userId;
	private Date uploadDate;
	
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
	@Column(name="self_id")
	public String getSelfId() {
		return selfId;
	}
	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}
	
	@Basic
	@Column(name="project_code")
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Basic
	@Column(name="project_name")
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Basic
	@Column(name="project_description")
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	
	@Basic
	@Column(name="unit")
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Basic
	@Column(name="quantity")
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	@Basic
	@Column(name="one_price")
	public Double getOnePrice() {
		return onePrice;
	}
	public void setOnePrice(Double onePrice) {
		this.onePrice = onePrice;
	}
	
	@Basic
	@Column(name="price_num")
	public Double getPriceNum() {
		return priceNum;
	}
	public void setPriceNum(Double priceNum) {
		this.priceNum = priceNum;
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
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Basic
	@Column(name="upload_date")
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	@Basic
	@Column(name="maybe_price")
	public Double getMaybePrice() {
		return maybePrice;
	}
	public void setMaybePrice(Double maybePrice) {
		this.maybePrice = maybePrice;
	}
	
	
	
	
}
