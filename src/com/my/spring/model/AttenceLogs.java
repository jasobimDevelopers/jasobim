package com.my.spring.model;


public class AttenceLogs {
	private Long user_id;
	private Integer late_nums;
	private Integer leave_early_nums;
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Integer getLeave_early_nums() {
		return leave_early_nums;
	}
	public void setLeave_early_nums(Integer leave_early_nums) {
		this.leave_early_nums = leave_early_nums;
	}
	public Integer getLate_nums() {
		return late_nums;
	}
	public void setLate_nums(Integer late_nums) {
		this.late_nums = late_nums;
	}
	
	
	
}
