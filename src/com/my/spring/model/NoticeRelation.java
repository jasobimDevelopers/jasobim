package com.my.spring.model;

import javax.persistence.*;
@Entity
@Table(name="notice_relation")
public class NoticeRelation {
	private Long id;
	private Long userId;
	private Long aboutId;
	private Integer type;//0 质量问题、 1 安全问题、 2 施工任务单、 3 预付单 、4 留言
	
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
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	@Column(name="type")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
