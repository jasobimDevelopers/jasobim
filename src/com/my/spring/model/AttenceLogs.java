package com.my.spring.model;
import java.util.Date;


public class AttenceLogs {
	private Date month;
	private Long id;
	private String start_work_time;
	private String end_work_time;
	private Long project_id;
	private Double lat;
	private Double lng;
	private Long attence_model_id;
	private Integer clock_flag;
	private Integer leave_early_num;
	private Long userId;
	private Date create_date;
	
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getMonth() {
		return month;
	}
	public void setMonth(Date month) {
		this.month = month;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStart_work_time() {
		return start_work_time;
	}
	public void setStart_work_time(String start_work_time) {
		this.start_work_time = start_work_time;
	}
	public String getEnd_work_time() {
		return end_work_time;
	}
	public void setEnd_work_time(String end_work_time) {
		this.end_work_time = end_work_time;
	}
	public Long getProject_id() {
		return project_id;
	}
	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Long getAttence_model_id() {
		return attence_model_id;
	}
	public void setAttence_model_id(Long attence_model_id) {
		this.attence_model_id = attence_model_id;
	}
	public Integer getClock_flag() {
		return clock_flag;
	}
	public void setClock_flag(Integer clock_flag) {
		this.clock_flag = clock_flag;
	}
	public Integer getLeave_early_num() {
		return leave_early_num;
	}
	public void setLeave_early_num(Integer leave_early_num) {
		this.leave_early_num = leave_early_num;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	
	
}
