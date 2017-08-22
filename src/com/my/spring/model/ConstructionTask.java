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
public class ConstructionTask {
	private Long id;
	private Date createDate;//任务单创建时间
	private Long userId;////任务单创建人id
	private String nextUserId;////任务单指定下一个人id
	private Date finishedDate;////完成时间
	private Long receiveUserId;////签收人id
	private String taskContent;///任务单内容
	private String detailContent;////任务单交底内容
	private String constructionCrewSugesstion;/////施工员建议
	private String partmentSugesstion;////项目部门相关人员意见
	private String leaderSuggestion;////总经理意见
	private String leaderName;////总经理签名
	private Integer taskFlag;////任务单状态指标
	private String othersAttention;////任务单其他需要注意的事情
	
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
    @Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	public String getNextUserId() {
		return nextUserId;
	}
	public void setNextUserId(String nextUserId) {
		this.nextUserId = nextUserId;
	}
	
	@Basic
    @Column(name = "finished_date")
	public Date getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}
	
	@Basic
    @Column(name = "receive_user_id")
	public Long getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(Long receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	
	@Basic
    @Column(name = "task_content")
	public String getTaskContent() {
		return taskContent;
	}
	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}
	
	@Basic
    @Column(name = "detail_content")
	public String getDetailContent() {
		return detailContent;
	}
	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}
	
	@Basic
    @Column(name = "construction_crew_sugesstion")
	public String getConstructionCrewSugesstion() {
		return constructionCrewSugesstion;
	}
	public void setConstructionCrewSugesstion(String constructionCrewSugesstion) {
		this.constructionCrewSugesstion = constructionCrewSugesstion;
	}
	
	@Basic
    @Column(name = "partment_sugesstion")
	public String getPartmentSugesstion() {
		return partmentSugesstion;
	}
	public void setPartmentSugesstion(String partmentSugesstion) {
		this.partmentSugesstion = partmentSugesstion;
	}
	
	@Basic
    @Column(name = "leader_suggestion")
	public String getLeaderSuggestion() {
		return leaderSuggestion;
	}
	public void setLeaderSuggestion(String leaderSuggestion) {
		this.leaderSuggestion = leaderSuggestion;
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
    @Column(name = "task_flag")
	public Integer getTaskFlag() {
		return taskFlag;
	}
	public void setTaskFlag(Integer taskFlag) {
		this.taskFlag = taskFlag;
	}
	
	@Basic
    @Column(name = "others_attention")
	public String getOthersAttention() {
		return othersAttention;
	}
	public void setOthersAttention(String othersAttention) {
		this.othersAttention = othersAttention;
	}
	
	
	
	
}
