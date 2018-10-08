package com.my.spring.model;

import java.util.Date;

/**
* @author 徐雨祥
* @version 创建时间：2018年9月29日 下午2:29:13
* 类说明
*/
public class UserTeamIndex {
	private Long id;
	private String name;
	private String teamUserName;
	private Long projectId;
	private Long createUser;
	private Date createDate;
	private Long indexs;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeamUserName() {
		return teamUserName;
	}
	public void setTeamUserName(String teamUserName) {
		this.teamUserName = teamUserName;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getIndexs() {
		return indexs;
	}
	public void setIndexs(Long indexs) {
		this.indexs = indexs;
	}
	
}
