package com.my.spring.model;
public class MechanicPricePojo {
	private Long id;
	private Long mechanicId;
	private Integer hour;
	private String editDate;
	private String createDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMechanicId() {
		return mechanicId;
	}
	public void setMechanicId(Long mechanicId) {
		this.mechanicId = mechanicId;
	}
	
	public Integer getHour() {
		return hour;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
	}
	
	public String getEditDate() {
		return editDate;
	}
	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
