package com.my.spring.model;

import java.util.List;

public class MeasuredForthChild {
	private Long pointId;
	private Long projectId;
	private Long paperOfMeasuredId;//户型名称
	private Integer abscissa;//横坐标
	private Integer ordinate;//纵坐标
	private Integer tag;//序列号
	private Integer status;//0、正常  1、爆点（当传过来的数据中有一个不正常时）
	private List<MeasuredFifthChild> child;
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
	public List<MeasuredFifthChild> getChild() {
		return child;
	}
	public void setChild(List<MeasuredFifthChild> child) {
		this.child = child;
	}
	
}
