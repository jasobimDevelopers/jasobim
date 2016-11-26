package com.my.spring.model;


public class MessageFilePojo {
	private Long id;
	private Long fileId;
	private Long messageId;
	private String originName;
	private String urlList;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	

	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	

	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getUrlList() {
		return urlList;
	}
	public void setUrlList(String urlList) {
		this.urlList = urlList;
	}
	
	

}
