package com.my.spring.model;

import java.util.List;

public class MeasuredThirdChild {
	private String paperUrl;
	private Long paperOfMeasuredId;
	private List<MeasuredForthChild> child;
	public String getPaperUrl() {
		return paperUrl;
	}
	public void setPaperUrl(String paperUrl) {
		this.paperUrl = paperUrl;
	}
	public Long getPaperOfMeasuredId() {
		return paperOfMeasuredId;
	}
	public void setPaperOfMeasuredId(Long paperOfMeasuredId) {
		this.paperOfMeasuredId = paperOfMeasuredId;
	}
	public List<MeasuredForthChild> getChild() {
		return child;
	}
	public void setChild(List<MeasuredForthChild> child) {
		this.child = child;
	}
	
}
