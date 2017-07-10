  package com.my.spring.model;


public class DuctPojos {
	private String month;
	private Long sum_date;
	private Integer state;///0.未指定状态  1.出库 2.安装 3.完成 
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Long getSumDate() {
		return sum_date;
	}
	public void setSumDate(Long sumDate) {
		this.sum_date = sumDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

	

	
}
