package com.my.spring.model;
/**
* @author 徐雨祥
* @version 创建时间：2018年10月25日 上午9:32:16
* 类说明
*/
public class ItemStateData {
    private String name;   //构件名称
    private String unit;
    private Double num;
    private String serviceType;
	private String familyAndType;
	private String size;
	private String systemType;
	private Integer professionType;
    private String percent;
    private String idList;
    
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getFamilyAndType() {
		return familyAndType;
	}
	public void setFamilyAndType(String familyAndType) {
		this.familyAndType = familyAndType;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getProfessionType() {
		return professionType;
	}
	public void setProfessionType(Integer professionType) {
		this.professionType = professionType;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getNum() {
		return num;
	}
	public void setNum(Double num) {
		this.num = num;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getIdList() {
		return idList;
	}
	public void setIdList(String idList) {
		this.idList = idList;
	}
    
}
