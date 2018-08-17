package com.my.spring.model;

import java.util.Date;

public class NoticePojo {
	private Long id;
	private Long user_id;
	private Long project_id;
	private Long about_id;
	private Integer notice_type;///通知关联表：0 质量问题、 1 安全问题、 2 施工任务单、 3 预付单 、4 留言
	private Integer read_state;////0 未读 1已读
	private Date create_date;
	private Date update_date;
	private String remark;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return user_id;
	}
	public void setUserId(Long userId) {
		this.user_id = userId;
	}
	
	public Long getAboutId() {
		return about_id;
	}
	public void setAboutId(Long aboutId) {
		this.about_id = aboutId;
	}
	
	public Integer getNoticeType() {
		return notice_type;
	}
	public void setNoticeType(Integer noticeType) {
		this.notice_type = noticeType;
	}
	

	public Integer getReadState() {
		return read_state;
	}
	public void setReadState(Integer readState) {
		this.read_state = readState;
	}
	

	public Date getCreateDate() {
		return create_date;
	}
	public void setCreateDate(Date createDate) {
		this.create_date = createDate;
	}
	
	public Date getUpdateDate() {
		return update_date;
	}
	public void setUpdateDate(Date updateDate) {
		this.update_date = updateDate;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	public Long getProjectId() {
		return project_id;
	}
	public void setProjectId(Long projectId) {
		this.project_id = projectId;
	}
	
	
	
}
