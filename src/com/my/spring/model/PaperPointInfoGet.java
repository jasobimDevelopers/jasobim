package com.my.spring.model;

import java.util.List;

public class PaperPointInfoGet {///测量点位具体信息表
	private Long pointId;
	private Long projectId;
	private Long paperOfMeasuredId;//户型名称
	private Integer abscissa;//横坐标
	private Integer ordinate;//纵坐标
	private Integer tag;//序列号
	private List<PaperPointInfoItem> itemList;
	
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

	
	public Long getPaperOfMeasuredId() {
		return paperOfMeasuredId;
	}
	public void setPaperOfMeasuredId(Long paperOfMeasuredId) {
		this.paperOfMeasuredId = paperOfMeasuredId;
	}
	
	public Integer getTag() {
		return tag;
	}
	public void setTag(Integer tag) {
		this.tag = tag;
	}
	public List<PaperPointInfoItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<PaperPointInfoItem> itemList) {
		this.itemList = itemList;
	}
	
	
}
