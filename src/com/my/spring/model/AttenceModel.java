package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="attence_model")
public class AttenceModel {
	private Long id;
	private String startTime;
	private String endTime;
	private Double placeLat;////打卡地点的经度
	private Double placeLng;////打卡地点的纬度
	private String place;
	private String attenceDay;
	private Integer attenceRange;///50-100-200-300-500
	private Long userId;
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
	@Column(name="start_time")
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@Basic
	@Column(name="end_time")
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@Basic
	@Column(name="place")
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	@Basic
	@Column(name="attence_day")
	public String getAttenceDay() {
		return attenceDay;
	}
	public void setAttenceDay(String attenceDay) {
		this.attenceDay = attenceDay;
	}
	
	@Basic
	@Column(name="attence_range")
	public Integer getAttenceRange() {
		return attenceRange;
	}
	public void setAttenceRange(Integer attenceRange) {
		this.attenceRange = attenceRange;
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
	@Column(name="place_lng")
	public Double getPlaceLng() {
		return placeLng;
	}
	public void setPlaceLng(Double placeLng) {
		this.placeLng = placeLng;
	}
	
	@Basic
	@Column(name="place_lat")
	public Double getPlaceLat() {
		return placeLat;
	}
	public void setPlaceLat(Double placeLat) {
		this.placeLat = placeLat;
	}
	
	
	//private 
}
