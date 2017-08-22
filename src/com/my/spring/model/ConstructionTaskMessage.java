package com.my.spring.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "construction_task")
public class ConstructionTaskMessage {
	private Long id;
	private Long userId;////任务单创建人id
	private Date date;
	private String content;
	private Long constructionTaskId;
	private String others;
	
	@Id
    @GeneratedValue
    @Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	@Basic
    @Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Basic
    @Column(name = "next_user_id")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Basic
    @Column(name = "next_user_id")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Basic
    @Column(name = "next_user_id")
	public Long getConstructionTaskId() {
		return constructionTaskId;
	}
	public void setConstructionTaskId(Long constructionTaskId) {
		this.constructionTaskId = constructionTaskId;
	}
	
	@Basic
    @Column(name = "next_user_id")
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	

	
	
	
	
	
}
