package com.my.spring.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
////质量安全问题属性

@Entity
@Table(name = "quality_question")
public class QualityQuestion {
	private Long id;       ////问题编号
	private Long projectId;////项目工程id
	private Long userId;  ///问题提交人id
	private String name;  ///问题名称
	private String trades;///问题专业
	private String intro; //////问题内容
	private Date questionDate;/////问题的创建时间
	private Integer priority;////问题等级 (0.一般 1.重要 2.紧急)
	private Integer state;   ///问题的状态(0.待解决 1.已解决)
	private String codeInformation;/////图片的二维码信息
	private String position;///图片位置
	private String modelFlag;
	//////
	private String voiceIdList;//语音idList
	private String userList;///接收到人
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
    @Column(name = "model_flag")
	public String getModelFlag() {
		return modelFlag;
	}
	public void setModelFlag(String modelFlag) {
		this.modelFlag = modelFlag;
	}
	
	@Basic
    @Column(name = "user_list")
	public String getUserList() {
		return userList;
	}
	public void setUserList(String userList) {
		this.userList = userList;
	}
	
	@Basic
    @Column(name = "project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
	
	@Basic
    @Column(name = "position")
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	
	@Basic
	@Column(name = "voice_id_list")
	public String getVoiceIdList() {
		return voiceIdList;
	}
	public void setVoiceIdList(String voiceIdList) {
		this.voiceIdList = voiceIdList;
	}
	
	

}
