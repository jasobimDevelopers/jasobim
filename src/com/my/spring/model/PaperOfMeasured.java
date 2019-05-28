package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="paper_of_measured")
public class PaperOfMeasured {
	private Long paperId;
	private Long projectId;
	private String paperName;
	private Long fileId;
	private Integer measuredNum;
	private Integer paperStatus;
	private Date createDate;
	private Long createUser;
	
	@Id
	@GeneratedValue
	@Column(name="paper_id")
	public Long getPaperId() {
		return paperId;
	}
	public void setPaperId(Long paperId) {
		this.paperId = paperId;
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
	@Column(name="paper_name")
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	@Basic
	@Column(name="file_id")
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	@Basic
	@Column(name="measured_num")
	public Integer getMeasuredNum() {
		return measuredNum;
	}
	public void setMeasuredNum(Integer measuredNum) {
		this.measuredNum = measuredNum;
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
	@Column(name="create_user")
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	@Basic
	@Column(name="paper_status")
	public Integer getPaperStatus() {
		return paperStatus;
	}
	public void setPaperStatus(Integer paperStatus) {
		this.paperStatus = paperStatus;
	}
	
}