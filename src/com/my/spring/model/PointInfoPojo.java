package com.my.spring.model;

public class PointInfoPojo {
	private Long pointId;
	private Long projectId;
	private Long paperOfMeasuredId;//户型名称
	private Integer abscissa;//横坐标
	private Integer ordinate;//纵坐标
	private Integer tag;//序列号
	private Integer status;//0、正常  1、爆点（当传过来的数据中有一个不正常时）
	public Long getPointId() {
		return pointId;
	}
	public void setPointId(Long pointId) {
		this.pointId = pointId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getPaperOfMeasuredId() {
		return paperOfMeasuredId;
	}
	public void setPaperOfMeasuredId(Long paperOfMeasuredId) {
		this.paperOfMeasuredId = paperOfMeasuredId;
	}
	public Integer getAbscissa() {
		return abscissa;
	}
	public void setAbscissa(Integer abscissa) {
		this.abscissa = abscissa;
	}
	public Integer getOrdinate() {
		return ordinate;
	}
	public void setOrdinate(Integer ordinate) {
		this.ordinate = ordinate;
	}
	public Integer getTag() {
		return tag;
	}
	public void setTag(Integer tag) {
		this.tag = tag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
