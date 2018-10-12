package com.my.spring.model;


/**
* @author 徐雨祥
* @version 创建时间：2018年10月10日 上午9:54:56
* 类说明
*/
public class ProjectIndex {
	private Long id;//工程id
    private String name;//工程名
    private String picUrl;
    private String leader;
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
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public Long getIndexs() {
		return indexs;
	}
	public void setIndexs(Long indexs) {
		this.indexs = indexs;
	}
    
}
