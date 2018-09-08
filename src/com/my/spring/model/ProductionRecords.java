package com.my.spring.model;

import java.util.Date;
import javax.persistence.*;
/**
* @author 徐雨祥
* @version 创建时间：2018年9月5日 下午3:26:30
* 类说明
*/
@Entity
@Table(name="production_records")
public class ProductionRecords {
	private Long id;
	private String userIdList;//作业人员idlist
	private String userNameList;//名字集合
	private Long createUserId;
	private Date createDate;
	private String constructPartName;
	private Long constructPartId;
	private String constructContent;
	private Long constructionLogId;
	
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
	@Column(name="user_id_list")
	public String getUserIdList() {
		return userIdList;
	}
	public void setUserIdList(String userIdList) {
		this.userIdList = userIdList;
	}
	
	@Basic
	@Column(name="user_name_list")
	public String getUserNameList() {
		return userNameList;
	}
	public void setUserNameList(String userNameList) {
		this.userNameList = userNameList;
	}
	
	@Basic
	@Column(name="create_user_id")
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
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
	@Column(name="construct_part_name")
	public String getConstructPartName() {
		return constructPartName;
	}
	public void setConstructPartName(String constructPartName) {
		this.constructPartName = constructPartName;
	}
	
	@Basic
	@Column(name="construct_part_id")
	public Long getConstructPartId() {
		return constructPartId;
	}
	public void setConstructPartId(Long constructPartId) {
		this.constructPartId = constructPartId;
	}
	
	@Basic
	@Column(name="construct_content")
	public String getConstructContent() {
		return constructContent;
	}
	public void setConstructContent(String constructContent) {
		this.constructContent = constructContent;
	}
	
	@Basic
	@Column(name="construction_log_id")
	public Long getConstructionLogId() {
		return constructionLogId;
	}
	public void setConstructionLogId(Long constructionLogId) {
		this.constructionLogId = constructionLogId;
	}
	
	
}
