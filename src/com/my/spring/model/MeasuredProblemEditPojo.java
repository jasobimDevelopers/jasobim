package com.my.spring.model;

public class MeasuredProblemEditPojo {
	private Long id;
	private Long rectifyUserId;
	private String finishedDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRectifyUserId() {
		return rectifyUserId;
	}
	public void setRectifyUserId(Long rectifyUserId) {
		this.rectifyUserId = rectifyUserId;
	}
	public String getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(String finishedDate) {
		this.finishedDate = finishedDate;
	}
	
}
