package com.my.spring.model;
/**
* @author 徐雨祥
* @version 创建时间：2018年10月25日 上午9:59:47
* 类说明
*/
public class ItemCount {
	private String name;
	private String serviceType;
	private String familyAndType;
	private String size;
	private String systemType;
	private Integer professionType;
	private Double length;
	private Double area;
	private Integer num;
	private Long selfId;
	private String idList;
	
	public Integer getProfessionType() {
		return professionType;
	}
	public void setProfessionType(Integer professionType) {
		this.professionType = professionType;
	}
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
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Long getSelfId() {
		return selfId;
	}
	public void setSelfId(Long selfId) {
		this.selfId = selfId;
	}
	public String getIdList() {
		return idList;
	}
	public void setIdList(String idList) {
		this.idList = idList;
	}
	
}
