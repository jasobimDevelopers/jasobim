package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="attence_Log")
public class AttenceLog {
	private Long id;
	private String startWorkTime;
	private String endWorkTime;
	private Integer late;
	private Integer leaveEarly;
	private Integer clockFlag;
	private Double lat;///经度
	private Double lng;///纬度
	private Long userId;
	private Date createDate;
	private Long projectId;
	private Long attenceModelId;
	
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
	@Column(name="start_work_time")
	public String getStartWorkTime() {
		return startWorkTime;
	}
	public void setStartWorkTime(String startWorkTime) {
		this.startWorkTime = startWorkTime;
	}
	
	@Basic
	@Column(name="end_work_time")
	public String getEndWorkTime() {
		return endWorkTime;
	}
	public void setEndWorkTime(String endWorkTime) {
		this.endWorkTime = endWorkTime;
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
	@Column(name="late")
	public Integer getLate() {
		return late;
	}
	public void setLate(Integer late) {
		this.late = late;
	}
	
	@Basic
	@Column(name="leave_early")
	public Integer getLeaveEarly() {
		return leaveEarly;
	}
	
	public void setLeaveEarly(Integer leaveEarly) {
		this.leaveEarly = leaveEarly;
	}
	
	@Basic
	@Column(name="lat")
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	
	@Basic
	@Column(name="lng")
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	
	@Basic
	@Column(name="attence_model_id")
	public Long getAttenceModelId() {
		return attenceModelId;
	}
	public void setAttenceModelId(Long attenceModelId) {
		this.attenceModelId = attenceModelId;
	}
	
	@Basic
	@Column(name="clock_flag")
	public Integer getClockFlag() {
		return clockFlag;
	}
	public void setClockFlag(Integer clockFlag) {
		this.clockFlag = clockFlag;
	}
	
	
}
