package com.my.spring.model;
public class SafeFinePojo {
	private Long id;
	private String userName;
	private Long projectId;
	private String detail;
	private String createDate;
	private Integer level;
	private Integer forfeit;
	private String checkDate;
	private String[] fileUrls;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userId) {
		this.userName = userId;
	}
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public Integer getForfeit() {
		return forfeit;
	}
	public void setForfeit(Integer forfeit) {
		this.forfeit = forfeit;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String[] getFileUrls() {
		return fileUrls;
	}
	public void setFileUrls(String[] fileUrls) {
		this.fileUrls = fileUrls;
	}
	
	
	
}
