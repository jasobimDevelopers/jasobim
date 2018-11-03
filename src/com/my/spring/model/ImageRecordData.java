package com.my.spring.model;

import java.util.List;

/**
* @author 徐雨祥
* @version 创建时间：2018年10月31日 下午12:53:43
* 类说明
*/
public class ImageRecordData {
	private Integer projectPart;//分部工程
	private Integer unitPart;//分项工程
	private List<String> projectPartDate;
	private List<String> content;
	public Integer getProjectPart() {
		return projectPart;
	}
	public void setProjectPart(Integer projectPart) {
		this.projectPart = projectPart;
	}
	public Integer getUnitPart() {
		return unitPart;
	}
	public void setUnitPart(Integer unitPart) {
		this.unitPart = unitPart;
	}
	public List<String> getProjectPartDate() {
		return projectPartDate;
	}
	public void setProjectPartDate(List<String> projectPartDate) {
		this.projectPartDate = projectPartDate;
	}
	public List<String> getContent() {
		return content;
	}
	public void setContent(List<String> content) {
		this.content = content;
	}
	
	
}
