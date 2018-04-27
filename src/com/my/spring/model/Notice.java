package com.my.spring.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="notice")
public class Notice {
	private Long id;
	private Long userId;
	private Long aboutId;
	private Integer noticeType;///通知关联表：0 质量问题、 1 安全问题、 2 施工任务单、 3 预付单 、4 留言
	private Integer readState;////0 未读 1已读
	private Date createDate;
	private Date updateDate;
	private String remark;
	
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
	@Column(name="notice_type")
	public Integer getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}
	
	@Basic
	@Column(name="read_state")
	public Integer getReadState() {
		return readState;
	}
	public void setReadState(Integer readState) {
		this.readState = readState;
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
	@Column(name="update_date")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Basic
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
