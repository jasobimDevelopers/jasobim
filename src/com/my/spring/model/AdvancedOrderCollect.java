package com.my.spring.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="advance_order_collect")
public class AdvancedOrderCollect {
	private Long id;
	private Date createDate;///创建时间
	private Long submitUserId;////预付单提交人
	private String createUserName;////预付单提交人姓名
	private String projectName;///项目部名称
	private String leader;////承包人姓名
	private Integer month;////预付单月份
	private String constructPart;///施工部位
	private Long currentFinished;//本期完成额
	private Long beforeFinished;//上期累计已付结额
	private String leaderName;//项目经理签字
	private String constructorName;///施工员签字
	private String quantityerName;//预算员签字
	
	private Long advancedOrderId;///预付单id

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
	@Column(name = "leader")
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	@Basic
	@Column(name = "month")
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	
	@Basic
	@Column(name = "construct_part")
	public String getConstructPart() {
		return constructPart;
	}
	public void setConstructPart(String constructPart) {
		this.constructPart = constructPart;
	}
	
	
	
	
	@Basic
	@Column(name = "submit_user_id")
	public Long getSubmitUserId() {
		return submitUserId;
	}
	public void setSubmitUserId(Long submitUserId) {
		this.submitUserId = submitUserId;
	}
	
	@Basic
	@Column(name = "create_user_name")
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	@Basic
	@Column(name = "project_name")
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Basic
	@Column(name = "current_finished")
	public Long getCurrentFinished() {
		return currentFinished;
	}
	public void setCurrentFinished(Long currentFinished) {
		this.currentFinished = currentFinished;
	}
	
	@Basic
	@Column(name = "before_finished")
	public Long getBeforeFinished() {
		return beforeFinished;
	}
	public void setBeforeFinished(Long beforeFinished) {
		this.beforeFinished = beforeFinished;
	}
	
	@Basic
	@Column(name = "leader_name")
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	
	@Basic
	@Column(name = "constructor_name")
	public String getConstructorName() {
		return constructorName;
	}
	public void setConstructorName(String constructorName) {
		this.constructorName = constructorName;
	}
	
	@Basic
	@Column(name = "quantityer_name")
	public String getQuantityerName() {
		return quantityerName;
	}
	public void setQuantityerName(String quantityerName) {
		this.quantityerName = quantityerName;
	}
	
	@Basic
	@Column(name = "advanced_order_id")
	public Long getAdvancedOrderId() {
		return advancedOrderId;
	}
	public void setAdvancedOrderId(Long advancedOrderId) {
		this.advancedOrderId = advancedOrderId;
	}
	
	@Basic
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
