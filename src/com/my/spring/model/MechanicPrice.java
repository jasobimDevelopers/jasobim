package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="mechanic_price")
public class MechanicPrice {
	private Long id;
	private Long mechanicId;
	private Integer hour;
	private Date editDate;
	private Date createDate;
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
	@Column(name="mechanic_id")
	public Long getMechanicId() {
		return mechanicId;
	}
	public void setMechanicId(Long mechanicId) {
		this.mechanicId = mechanicId;
	}
	
	@Basic
	@Column(name="hour")
	public Integer getHour() {
		return hour;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
	}
	
	@Basic
	@Column(name="edit_date")
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
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
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
}
