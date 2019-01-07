package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="reply")
public class Reply {
	private Long id;
	private Long replyUser;
	private Date createDate;
	private String replyContent;
	private Integer replyType;
	private Long aboutId;
	private Integer schedule;
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
	@Column(name="reply_user")
	public Long getReplyUser() {
		return replyUser;
	}
	public void setReplyUser(Long replyUser) {
		this.replyUser = replyUser;
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
	@Column(name="reply_content")
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	
	@Basic
	@Column(name="reply_type")
	public Integer getReplyType() {
		return replyType;
	}
	public void setReplyType(Integer replyType) {
		this.replyType = replyType;
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
	@Column(name="schedule")
	public Integer getSchedule() {
		return schedule;
	}
	public void setSchedule(Integer schedule) {
		this.schedule = schedule;
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
