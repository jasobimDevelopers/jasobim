package com.my.spring.model;
/**
* @author 徐雨祥
* @version 创建时间：2018年10月11日 下午2:35:39
* 类说明
*/
public class AppLoginBackInfo {
	private Long id;///id
	private String realName;///真实姓名
	private String iconUrl;///头像
	private String userName;///用户名
	private String workName;///职称
	private Integer userType;//用户角色
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	
}
