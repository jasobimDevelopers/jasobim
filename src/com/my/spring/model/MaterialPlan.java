package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="material_plan")
public class MaterialPlan {
	private Long id;
	private String name;//材料名称
	private String model;//型号规格
	private String standard;//质量标准
	private String unit;//单位
	private Integer num;//数量
	private String getTime;//供货时间
	private String outPlace;//卸货地点
	private String usePlace;//用料地点
	private Date startTime;
	private Date endTime;
	private Long pid;
	private Date createDate;
	private Date updateDate;
	private Long userId;
	private Long projectId;
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	@Column(name="start_time")
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Basic
	@Column(name="end_time")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Basic
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name="model")
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	@Basic
	@Column(name="standard")
	public String getStandard() {
		return standard;
	}
	
	public void setStandard(String standard) {
		this.standard = standard;
	}
	
	@Basic
	@Column(name="unit")
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Basic
	@Column(name="num")
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
	@Basic
	@Column(name="get_time")
	public String getGetTime() {
		return getTime;
	}
	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}
	
	@Basic
	@Column(name="out_place")
	public String getOutPlace() {
		return outPlace;
	}
	public void setOutPlace(String outPlace) {
		this.outPlace = outPlace;
	}
	
	@Basic
	@Column(name="use_place")
	public String getUsePlace() {
		return usePlace;
	}
	public void setUsePlace(String usePlace) {
		this.usePlace = usePlace;
	}
	
	@Basic
	@Column(name="pid")
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	
	@Basic
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Basic
	@Column(name="update_date")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Basic
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Basic
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
}
