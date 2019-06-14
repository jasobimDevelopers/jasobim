package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="comment")
public class Comment {
	private Long id;
	private Long commentUser;//评论人
	private Long aboutId;
	private Integer replyType;//0、质量 1、安全 2、实测实量
	private Date createDate;
	private String commentContent;
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
	@Column(name="comment_user")
	public Long getCommentUser() {
		return commentUser;
	}
	public void setCommentUser(Long commentUser) {
		this.commentUser = commentUser;
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
	@Column(name="reply_type")
	public Integer getReplyType() {
		return replyType;
	}
	public void setReplyType(Integer replyType) {
		this.replyType = replyType;
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
	@Column(name="comment_content")
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
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
