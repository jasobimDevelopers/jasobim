package com.my.spring.model;

public class ProcessLogPojo {
	private Long id;
	private Long processId;
	private Integer currentNode;
	private Integer endFlag;//0、未结束 1、结束
	private String itemName;
	private String note;
	private Integer itemState;//节点的状态（同意、不同意）
	private String approveUser;
	private String nextApproveUser;
	private String approveDate;
	
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
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	
	public String getApproveUser() {
		return approveUser;
	}
	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}
	public String getNextApproveUser() {
		return nextApproveUser;
	}
	public void setNextApproveUser(String nextApproveUser) {
		this.nextApproveUser = nextApproveUser;
	}
	public String getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}
	
	
}
