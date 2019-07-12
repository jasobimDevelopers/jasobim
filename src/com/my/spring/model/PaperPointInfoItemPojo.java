package com.my.spring.model;

import java.util.List;

public class PaperPointInfoItemPojo {
	private Long pointItemId;
	private String flag;//唯一性标签（楼栋名称+房间号+测点序列号）
	private String title;
	private String content;
	private List<InputLog> logList;
	private Integer errorUpperLimit;//误差上限
	private Integer errorLowerLimit;//误差下限
	private Integer standardNum;//设计值/标准值
	
	
	public Integer getErrorUpperLimit() {
		return errorUpperLimit;
	}
	public void setErrorUpperLimit(Integer errorUpperLimit) {
		this.errorUpperLimit = errorUpperLimit;
	}
	public Integer getErrorLowerLimit() {
		return errorLowerLimit;
	}
	public void setErrorLowerLimit(Integer errorLowerLimit) {
		this.errorLowerLimit = errorLowerLimit;
	}
	public Integer getStandardNum() {
		return standardNum;
	}
	public void setStandardNum(Integer standardNum) {
		this.standardNum = standardNum;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
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
