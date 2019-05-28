package com.my.spring.model;

import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="measured_site")
public class MeasuredSite {
	private Long siteId;
	private Long bfmId;
	private Long projectId;
	private String siteName;
	private Long checkUser;
	private Long paperOfMeasuredId;//户型图纸id
	private Date createDate;
	private Long createUser;
	@Id
	@GeneratedValue
	@Column(name="site_id")
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	
	@Basic
	@Column(name="bfm_id")
	public Long getBfmId() {
		return bfmId;
	}
	public void setBfmId(Long bfmId) {
		this.bfmId = bfmId;
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
	@Column(name="site_name")
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	@Basic
	@Column(name="check_user")
	public Long getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(Long checkUser) {
		this.checkUser = checkUser;
	}
	
	@Basic
	@Column(name="paper_of_measured_id")
	public Long getPaperOfMeasuredId() {
		return paperOfMeasuredId;
	}
	public void setPaperOfMeasuredId(Long paperOfMeasuredId) {
		this.paperOfMeasuredId = paperOfMeasuredId;
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
	
}
