package com.my.spring.model;
import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name="point_data_input_log")
public class PointDataInputLog {//测量点位数据录入表
	private Long id;
	private Integer inputData;//测量数据，单位毫米
	private Long projectId;//项目id
	private Long measuredSiteId;//具体区域id
	private Long bfmId;//楼栋id
	private String siteInfo;//区域名称
	private String flag;//唯一性标签（楼栋名称+房间号+测点序列号）
	private Long checkTypeId;//检查项id
	private Integer status;//0、正常数据  1、异常数据
	private Long createUser;
	private Date createDate;
	private Long pointId;//测点id
	
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
	@Column(name="input_data")
	public Integer getInputData() {
		return inputData;
	}
	public void setInputData(Integer inputData) {
		this.inputData = inputData;
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
	@Column(name="flag")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Basic
	@Column(name="check_type_id")
	public Long getCheckTypeId() {
		return checkTypeId;
	}
	public void setCheckTypeId(Long checkTypeId) {
		this.checkTypeId = checkTypeId;
	}
	
	@Basic
	@Column(name="status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Basic
	@Column(name="point_id")
	public Long getPointId() {
		return pointId;
	}
	public void setPointId(Long pointId) {
		this.pointId = pointId;
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
	@Column(name="measured_site_id")
	public Long getMeasuredSiteId() {
		return measuredSiteId;
	}
	public void setMeasuredSiteId(Long measuredSiteId) {
		this.measuredSiteId = measuredSiteId;
	}
	@Basic
	@Column(name="site_info")
	public String getSiteInfo() {
		return siteInfo;
	}
	public void setSiteInfo(String siteInfo) {
		this.siteInfo = siteInfo;
	}
	
	
}
