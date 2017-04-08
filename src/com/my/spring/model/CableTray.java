package com.my.spring.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name ="cable_tray")
public class CableTray {
	private Long id;
	private String name;
	private String location;
	private Long projectId;
	private String serviceType;
	private String familyAndType;
	private String size;
	private String level;
	private String offset;
	private String selfId;
	private String codeUrl;
	private Integer state;
	private Date date;
	@Id
    @GeneratedValue
    @Column(name = "id")
	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id=id;
	}
	
	@Basic
	@Column(name ="state")
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	@Basic
	@Column(name ="date")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Basic
	@Column(name ="code_url")
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	
	@Basic
	@Column(name ="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name ="location")
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Basic
	@Column(name ="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@Basic
	@Column(name ="service_type")
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	@Basic
	@Column(name ="family_and_type")
	public String getFamilyAndType() {
		return familyAndType;
	}
	public void setFamilyAndType(String familyAndType) {
		this.familyAndType = familyAndType;
	}
	
	@Basic 
	@Column(name ="size")
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	@Basic 
	@Column(name ="level")
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	@Basic 
	@Column(name ="offset")
	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}
	
	@Basic
	@Column(name ="self_id")
	public String getSelfId() {
		return selfId;
	}
	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}
	
}
