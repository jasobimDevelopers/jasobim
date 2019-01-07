package com.my.spring.model;

import java.util.List;

public class CommentPojo {
	private Long id;
	private String commentUserName;
	private String createDate;
	private String commentContent;
	private List<String> pictures;
	private List<String> voices;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCommentUserName() {
		return commentUserName;
	}
	public void setCommentUserName(String commentUserName) {
		this.commentUserName = commentUserName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
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
	
	
}
