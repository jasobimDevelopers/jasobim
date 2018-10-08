package com.my.spring.model;

import java.util.Date;

/**
* @author 徐雨祥
* @version 创建时间：2018年9月29日 下午3:10:33
* 类说明
*/
public class ProcessDataIndex {
	private Long id;
	private Long indexs;
	private String name;
	private Integer itemNum;
	private Date createDate;
	private Long projectId;
	private Integer type;
	private Long createUser;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIndexs() {
		return indexs;
	}
	public void setIndexs(Long indexs) {
		this.indexs = indexs;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getItemNum() {
		return itemNum;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	
}
