package com.my.spring.model;

import java.util.Date;
import javax.persistence.*;
/**
* @author 徐雨祥
* @version 创建时间：2018年10月12日 下午12:55:34
* 类说明
*/
@Entity
@Table(name="process_of_mode")
public class ProcessOfMode {
	private Long id;
	private Long projectId;
	private Long itemId;
	private Integer itemType;//状态
	private Date createDate;
	private Long createUser;
	private Long modeId;
	
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
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@Basic
	@Column(name="item_id")
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	@Basic
	@Column(name="item_type")
	public Integer getItemType() {
		return itemType;
	}
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
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
	@Column(name="mode_id")
	public Long getModeId() {
		return modeId;
	}
	public void setModeId(Long modeId) {
		this.modeId = modeId;
	}
	
}
