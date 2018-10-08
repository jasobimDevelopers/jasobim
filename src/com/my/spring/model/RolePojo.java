  package com.my.spring.model;
public class RolePojo {
	private Long id;
	private String name;
	private Object menuList;
	private Integer readState;////0、可读  1 可写
	private String remark;
	private String createDate;/////当时的时间
	private String createUser;///用户id
	private Long indexs;
	private String updateDate;///修改时间
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}
	
	public Object getMenuList() {
		return menuList;
	}

	public void setMenuList(Object menuList) {
		this.menuList = menuList;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getReadState() {
		return readState;
	}

	public void setReadState(Integer readState) {
		this.readState = readState;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Long getIndexs() {
		return indexs;
	}

	public void setIndexs(Long indexs) {
		this.indexs = indexs;
	}
	
	
}
