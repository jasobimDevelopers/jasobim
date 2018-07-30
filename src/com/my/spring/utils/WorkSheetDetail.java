package com.my.spring.utils;

public class WorkSheetDetail {
	 //工作内容
    private String workCtx;
    // 用工人总数	工日数 = gwnNum+tmnNum
    private Float totalHumanDays;
    //普工用工数 1-4小时为半天，4-8小时为一天;120每天
    private Integer gwnNum;
    //技工用工数 1-4小时为半天，4-8小时为一天；160每天
    private Integer tmnNum;
    // 单价（元）
    private Float unitPrice;
    // 金额(元） = gwnNum*120+tmnNum+160
    private Float unitAmount;
    // 备注
    private String notes; 
    
	public WorkSheetDetail(String workCtx, Float totalHumanDays, Integer gwnNum, Integer tmnNum, Float unitPrice,
			Float unitAmount, String notes) {
		super();
		this.workCtx = workCtx;
		this.totalHumanDays = totalHumanDays;
		this.gwnNum = gwnNum;
		this.tmnNum = tmnNum;
		this.unitPrice = unitPrice;
		this.unitAmount = unitAmount;
		this.notes = notes;
	}
	public String getWorkCtx() {
		return workCtx;
	}
	public void setWorkCtx(String workCtx) {
		this.workCtx = workCtx;
	}
	public Float getTotalHumanDays() {
		return totalHumanDays;
	}
	public void setTotalHumanDays(Float totalHumanDays) {
		this.totalHumanDays = totalHumanDays;
	}
	public Integer getGwnNum() {
		return gwnNum;
	}
	public void setGwnNum(Integer gwnNum) {
		this.gwnNum = gwnNum;
	}
	public Integer getTmnNum() {
		return tmnNum;
	}
	public void setTmnNum(Integer tmnNum) {
		this.tmnNum = tmnNum;
	}
	public Float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Float getUnitAmount() {
		return unitAmount;
	}
	public void setUnitAmount(Float unitAmount) {
		this.unitAmount = unitAmount;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

}
