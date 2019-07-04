package com.my.spring.model;

public class MeasuredProblemEditPojo {
	private Long id;
	private String rectifyUserId;
	private String finishedDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRectifyUserId() {
		return rectifyUserId;
	}
	public void setRectifyUserId(String rectifyUserId) {
		this.rectifyUserId = rectifyUserId;
	}
	public String getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(String finishedDate) {
		this.finishedDate = finishedDate;
	}
	
}
