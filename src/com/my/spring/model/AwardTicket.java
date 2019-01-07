package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="award_ticket")
public class AwardTicket {
	private Long id;
	private Long createUser;
	private Long aboutId;
	private String personLiable;
	private Integer ticketType;
	private Integer awardType;
	private Integer awardNum;
	private Date createDate;
	private Date awardDate;
	private String remark;
	private String pictures;
	private String voices;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	@Column(name="create_user")
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	
	@Basic
	@Column(name="about_id")
	public Long getAboutId() {
		return aboutId;
	}
	public void setAboutId(Long aboutId) {
		this.aboutId = aboutId;
	}
	
	@Basic
	@Column(name="person_liable")
	public String getPersonLiable() {
		return personLiable;
	}
	public void setPersonLiable(String personLiable) {
		this.personLiable = personLiable;
	}
	
	@Basic
	@Column(name="ticket_type")
	public Integer getTicketType() {
		return ticketType;
	}
	public void setTicketType(Integer ticketType) {
		this.ticketType = ticketType;
	}
	
	@Basic
	@Column(name="award_type")
	public Integer getAwardType() {
		return awardType;
	}
	public void setAwardType(Integer awardType) {
		this.awardType = awardType;
	}
	
	@Basic
	@Column(name="award_num")
	public Integer getAwardNum() {
		return awardNum;
	}
	public void setAwardNum(Integer awardNum) {
		this.awardNum = awardNum;
	}
	
	@Basic
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Basic
	@Column(name="award_date")
	public Date getAwardDate() {
		return awardDate;
	}
	public void setAwardDate(Date awardDate) {
		this.awardDate = awardDate;
	}
	
	@Basic
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Basic
	@Column(name="pictures")
	public String getPictures() {
		return pictures;
	}
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	
	@Basic
	@Column(name="voices")
	public String getVoices() {
		return voices;
	}
	public void setVoices(String voices) {
		this.voices = voices;
	}
	
}
