package com.my.spring.model;

import java.util.List;

public class PaperPointInfoItemPojo {
	private Long pointItemId;
	private String title;
	private String content;
	private List<InputLog> logList;
	public Long getPointItemId() {
		return pointItemId;
	}
	public void setPointItemId(Long pointItemId) {
		this.pointItemId = pointItemId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<InputLog> getLogList() {
		return logList;
	}
	public void setLogList(List<InputLog> logList) {
		this.logList = logList;
	}
	
}
