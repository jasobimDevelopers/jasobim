package com.my.spring.model;

import java.util.Date;
import javax.persistence.*;
@Entity
@Table(name="news_info")
public class NewsInfo {
	private Long id;
	private String topic;
	private String content;
	private Date createDate;
	private Long createUserId;
	private String remark;
	private Integer readNum;//阅读量（默认值0）
	private Integer readState;//1、热门  0、一般状态
	private Integer readStatus;//1、置顶、 0、一般的状态
	
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
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	@Column(name="create_user_id")
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	
	@Basic
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Basic
	@Column(name="topic")
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	@Basic
	@Column(name="read_num")
	public Integer getReadNum() {
		return readNum;
	}
	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
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
	@Column(name="read_status")
	public Integer getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}
	

}
