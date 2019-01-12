package com.my.spring.model;

public class ManageStates {
	private Integer rectifyNum;//待我整改数目
	private Integer checkNum;//待我复检数目
	private String rectifyIds;
	private String checkIds;
	private Integer checkState;//0、无  1、有
	private Integer qualityState;
	private Integer awardState;
	public Integer getRectifyNum() {
		return rectifyNum;
	}
	public void setRectifyNum(Integer rectifyNum) {
		this.rectifyNum = rectifyNum;
	}
	public Integer getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(Integer checkNum) {
		this.checkNum = checkNum;
	}
	public Integer getCheckState() {
		return checkState;
	}
	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}
	public Integer getQualityState() {
		return qualityState;
	}
	public void setQualityState(Integer qualityState) {
		this.qualityState = qualityState;
	}
	public Integer getAwardState() {
		return awardState;
	}
	public void setAwardState(Integer awardState) {
		this.awardState = awardState;
	}
	public String getCheckIds() {
		return checkIds;
	}
	public void setCheckIds(String checkIds) {
		this.checkIds = checkIds;
	}
	public String getRectifyIds() {
		return rectifyIds;
	}
	public void setRectifyIds(String rectifyIds) {
		this.rectifyIds = rectifyIds;
	}
	
	
}
