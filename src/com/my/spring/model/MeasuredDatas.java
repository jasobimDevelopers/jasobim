package com.my.spring.model;

import javax.persistence.*;

@Entity
@Table(name="measured_data_list")
public class MeasuredDatas {
	private Long id;
	private String checkContent;
	private String checkTemplete;
	private Integer shiceData;
	private Integer passData;
	private String designLevel;
	private String locationSize;
	private String inputData;
	private Long measuredDataId;
	
	
	@Id
    @GeneratedValue
    @Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
    @Column(name = "check_content")
	public String getCheckContent() {
		return checkContent;
	}
	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
	
	@Basic
    @Column(name = "check_templete")
	public String getCheckTemplete() {
		return checkTemplete;
	}
	public void setCheckTemplete(String checkTemplete) {
		this.checkTemplete = checkTemplete;
	}
	
	@Basic
    @Column(name = "shice_data")
	public Integer getShiceData() {
		return shiceData;
	}
	public void setShiceData(Integer shiceData) {
		this.shiceData = shiceData;
	}
	
	@Basic
    @Column(name = "pass_data")
	public Integer getPassData() {
		return passData;
	}
	public void setPassData(Integer passData) {
		this.passData = passData;
	}
	
	@Basic
    @Column(name = "design_level")
	public String getDesignLevel() {
		return designLevel;
	}
	public void setDesignLevel(String designLevel) {
		this.designLevel = designLevel;
	}
	
	@Basic
    @Column(name = "location_size")
	public String getLocationSize() {
		return locationSize;
	}
	public void setLocationSize(String locationSize) {
		this.locationSize = locationSize;
	}
	
	@Basic
    @Column(name = "input_data")
	public String getInputData() {
		return inputData;
	}
	public void setInputData(String inputData) {
		this.inputData = inputData;
	}
	
	@Basic
	@Column(name = "measured_data_id")
	public Long getMeasuredDataId() {
		return measuredDataId;
	}
	public void setMeasuredDataId(Long measuredDataId) {
		this.measuredDataId = measuredDataId;
	}
	
	
	
}
