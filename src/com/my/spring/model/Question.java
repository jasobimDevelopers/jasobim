package com.my.spring.model;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question {
	private Long id;
	private Integer questionType;
	private Long userId;
	private String name;
	private String trades;
	private String intro;
	private Date questionDate;
	private Integer priority;
	private Integer state;
	private String codeInformation;
	
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
    @Column(name = "question_type")
	public Integer getQuestionType() {
		return questionType;
	}
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
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
    @Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
    @Column(name = "trades")
	public String getTrades() {
		return trades;
	}
	public void setTrades(String trades) {
		this.trades = trades;
	}
	
	@Basic
    @Column(name = "intro")
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	@Basic
    @Column(name = "question_date")
	public Date getQuestionDate() {
		return questionDate;
	}
	public void setQuestionDate(Date questionDate) {
		this.questionDate = questionDate;
	}
	
	@Basic
    @Column(name = "priority")
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	@Basic
    @Column(name = "state")
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	@Basic
    @Column(name = "code_information")
	public String getCodeInformation() {
		return codeInformation;
	}
	public void setCodeInformation(String codeInformation) {
		this.codeInformation = codeInformation;
	}
	
	

}
