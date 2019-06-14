package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="measured_problem")//爆点信息表单
public class MeasuredProblem {
	private Long id;
	private String checkSite;//检查部位
	private Date checkDate;//检查日期
	private Long checkUser;//检查人
	private String checkLists;//检查项
	private String detail;//问题描述
	private Long rectifyUser;//整改人
	private Date finishedDate;//限定完成时间
	private Integer status;//0、待指派 1、进行中 2、待销项
	private Integer process;//进度百分比
	private String files;
	private String voices;
	private Long projectId;
	private Integer score;
	private Long createUser;
	private Date createDate;
	
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
	@Column(name="check_site")
	public String getCheckSite() {
		return checkSite;
	}
	public void setCheckSite(String checkSite) {
		this.checkSite = checkSite;
	}
	@Basic
	@Column(name="check_date")
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	@Basic
	@Column(name="check_user")
	public Long getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(Long checkUser) {
		this.checkUser = checkUser;
	}
	@Basic
	@Column(name="check_lists")
	public String getCheckLists() {
		return checkLists;
	}
	public void setCheckLists(String checkLists) {
		this.checkLists = checkLists;
	}
	@Basic
	@Column(name="detail")
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@Basic
	@Column(name="rectify_user")
	public Long getRectifyUser() {
		return rectifyUser;
	}
	public void setRectifyUser(Long rectifyUser) {
		this.rectifyUser = rectifyUser;
	}
	@Basic
	@Column(name="finished_date")
	public Date getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}
	@Basic
	@Column(name="status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Basic
	@Column(name="process")
	public Integer getProcess() {
		return process;
	}
	public void setProcess(Integer process) {
		this.process = process;
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
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Basic
	@Column(name="score")
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
	@Basic
	@Column(name="files")
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	@Basic
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
