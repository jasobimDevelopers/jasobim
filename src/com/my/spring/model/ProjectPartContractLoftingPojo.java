package com.my.spring.model;
/**
* @author 徐雨祥
* @version 创建时间：2018年10月17日 下午5:02:23
* 类说明
*/
public class ProjectPartContractLoftingPojo {
	private Long id;
	private Long loftingId;
	private String name;
	private String partName;
	private Double value;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLoftingId() {
		return loftingId;
	}
	public void setLoftingId(Long loftingId) {
		this.loftingId = loftingId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
}
