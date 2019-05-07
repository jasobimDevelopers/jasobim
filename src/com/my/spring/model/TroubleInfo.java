package com.my.spring.model;

import java.util.Date;
import javax.persistence.*; 
public class TroubleInfo {
	private Long id;
	private Long projectId;
	private Date createDate;
	private Long createUser;///创建人
	private Long rectifyUser;///整改人
	private String remark;///描述
	private String pics;///图片
	private Integer troubleType;///问题类型
	private Date rectifyDate;///整改期限
	private Long checkListId;///检查项
	private String measuredData;///测量数据
	private String checkSite;///检查部位
	
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
	@Column(name="rectify_user")
	public Long getRectifyUser() {
		return rectifyUser;
	}
	public void setRectifyUser(Long rectifyUser) {
		this.rectifyUser = rectifyUser;
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
	@Column(name="pics")
	public String getPics() {
		return pics;
	}
	public void setPics(String pics) {
		this.pics = pics;
	}
	
	@Basic
	@Column(name="trouble_type")
	public Integer getTroubleType() {
		return troubleType;
	}
	public void setTroubleType(Integer troubleType) {
		this.troubleType = troubleType;
	}
	
	@Basic
	@Column(name="rectify_date")
	public Date getRectifyDate() {
		return rectifyDate;
	}
	public void setRectifyDate(Date rectifyDate) {
		this.rectifyDate = rectifyDate;
	}
	
	@Basic
	@Column(name="check_list_id")
	public Long getCheckListId() {
		return checkListId;
	}
	public void setCheckListId(Long checkListId) {
		this.checkListId = checkListId;
	}
	
	@Basic
	@Column(name="measured_data")
	public String getMeasuredData() {
		return measuredData;
	}
	public void setMeasuredData(String measuredData) {
		this.measuredData = measuredData;
	}
	
	@Basic
	@Column(name="check_site")
	public String getCheckSite() {
		return checkSite;
	}
	public void setCheckSite(String checkSite) {
		this.checkSite = checkSite;
	}
	 


}
