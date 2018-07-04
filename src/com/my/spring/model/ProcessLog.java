package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="process_log")
public class ProcessLog {
	private Long id;
	private Long processId;
	private Integer currentNode;
	private Integer endFlag;//0、未结束 1、结束
	private Long itemId;
	private String note;
	private Integer itemState;//节点的状态（同意、不同意）
	private Date createDate;
	
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
	@Column(name="process_id")
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	
	
	@Basic
	@Column(name="current_node")
	public Integer getCurrentNode() {
		return currentNode;
	}
	public void setCurrentNode(Integer currentNode) {
		this.currentNode = currentNode;
	}
	
	@Basic
	@Column(name="end_flag")
	public Integer getEndFlag() {
		return endFlag;
	}
	public void setEndFlag(Integer endFlag) {
		this.endFlag = endFlag;
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
	@Column(name="note")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	@Basic
	@Column(name="item_state")
	public Integer getItemState() {
		return itemState;
	}
	public void setItemState(Integer itemState) {
		this.itemState = itemState;
	}
	
	@Basic
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
