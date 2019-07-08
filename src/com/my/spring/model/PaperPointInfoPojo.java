package com.my.spring.model;

import java.util.List;

public class PaperPointInfoPojo {///测量点位具体信息表
	private String paperUrl;
	private Long paperOfMeasuredId;
	private List<PointInfoPojo> info;
	public String getPaperUrl() {
		return paperUrl;
	}
	public void setPaperUrl(String paperUrl) {
		this.paperUrl = paperUrl;
	}
	public List<PointInfoPojo> getInfo() {
		return info;
	}
	public void setInfo(List<PointInfoPojo> info) {
		this.info = info;
	}
	public Long getPaperOfMeasuredId() {
		return paperOfMeasuredId;
	}
	public void setPaperOfMeasuredId(Long paperOfMeasuredId) {
		this.paperOfMeasuredId = paperOfMeasuredId;
	}
	
}
