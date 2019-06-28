package com.my.spring.model;

import java.util.List;

public class PointDataInputLogGet {//测量点位数据录入表
	private Long projectId;//项目id
	private Long measuredSiteId;//具体区域id
	private Long bfmId;//楼栋id
	private Integer stag;//序号
	private String siteInfo;//区域名称（1#-101）
	private String flag;//唯一性标签（楼栋名称+房间号+测点序列号）
	private Long pointId;//测点id
	private Integer abscissa;//横坐标
	private Integer ordinate;//纵坐标
	private Integer state;//0、 正常数据 1、异常数据
	private Long paperOfMeasuredId;
	private List<PointDataInputItem> inputItemList;
	
	
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
	public List<PointDataInputItem> getInputItemList() {
		return inputItemList;
	}
	public void setInputItemList(List<PointDataInputItem> inputItemList) {
		this.inputItemList = inputItemList;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public Long getPointId() {
		return pointId;
	}
	public void setPointId(Long pointId) {
		this.pointId = pointId;
	}
	public Long getBfmId() {
		return bfmId;
	}
	public void setBfmId(Long bfmId) {
		this.bfmId = bfmId;
	}
	public Long getMeasuredSiteId() {
		return measuredSiteId;
	}
	public void setMeasuredSiteId(Long measuredSiteId) {
		this.measuredSiteId = measuredSiteId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getStag() {
		return stag;
	}
	public void setStag(Integer stag) {
		this.stag = stag;
	}
	public String getSiteInfo() {
		return siteInfo;
	}
	public void setSiteInfo(String siteInfo) {
		this.siteInfo = siteInfo;
	}
	
	
}
