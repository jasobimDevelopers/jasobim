package com.my.spring.model;

public class ProcessLogPojo {
	private Long id;
	private Long processId;
	private Integer currentNode;
	private Integer endFlag;//0、未结束 1、结束
	private String itemId;
	private String note;
	private Integer itemState;//节点的状态（同意、不同意）
	private String createDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	
	
	public Integer getCurrentNode() {
		return currentNode;
	}
	public void setCurrentNode(Integer currentNode) {
		this.currentNode = currentNode;
	}
	
	public Integer getEndFlag() {
		return endFlag;
	}
	public void setEndFlag(Integer endFlag) {
		this.endFlag = endFlag;
	}
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public Integer getItemState() {
		return itemState;
	}
	public void setItemState(Integer itemState) {
		this.itemState = itemState;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
