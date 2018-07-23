package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="quality_fine")
public class QualityFine {
	private Long id;
	private Long userId;
	private Long projectId;
	private String detail;
	private Date createDate;
	private Integer level;
	private Integer forfeit;
	private String checkDate;
	private String fileIds;
	
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
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Basic
	@Column(name="level")
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Basic
	@Column(name="forfeit")
	public Integer getForfeit() {
		return forfeit;
	}
	public void setForfeit(Integer forfeit) {
		this.forfeit = forfeit;
	}
	
	@Basic
	@Column(name="check_date")
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	
	@Basic
	@Column(name="file_ids")
	public String getFileIds() {
		return fileIds;
	}
	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
	
	
	
}