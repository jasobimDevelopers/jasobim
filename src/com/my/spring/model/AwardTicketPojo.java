package com.my.spring.model;

import java.util.List;

public class AwardTicketPojo {
	private Long id;
	private String createUserName;
	private Long aboutId;
	private List<String> personLiable;
	private Integer ticketType;
	private Integer awardType;
	private Integer awardNum;
	private String createDate;
	private String awardDate;
	private String remark;
	private List<String> pictures;
	private List<String> voices;
	private String userIcon;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Long getAboutId() {
		return aboutId;
	}
	public void setAboutId(Long aboutId) {
		this.aboutId = aboutId;
	}
	public List<String> getPersonLiable() {
		return personLiable;
	}
	public void setPersonLiable(List<String> personLiable) {
		this.personLiable = personLiable;
	}
	public Integer getTicketType() {
		return ticketType;
	}
	public void setTicketType(Integer ticketType) {
		this.ticketType = ticketType;
	}
	public Integer getAwardType() {
		return awardType;
	}
	public void setAwardType(Integer awardType) {
		this.awardType = awardType;
	}
	public Integer getAwardNum() {
		return awardNum;
	}
	public void setAwardNum(Integer awardNum) {
		this.awardNum = awardNum;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getAwardDate() {
		return awardDate;
	}
	public void setAwardDate(String awardDate) {
		this.awardDate = awardDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<String> getPictures() {
		return pictures;
	}
	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}
	public List<String> getVoices() {
		return voices;
	}
	public void setVoices(List<String> voices) {
		this.voices = voices;
	}
	public String getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}
	
	
	
	
}
