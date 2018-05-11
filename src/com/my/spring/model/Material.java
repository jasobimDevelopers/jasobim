package com.my.spring.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "material")
public class Material {
	private Long id;
	private Long projectId;//项目id
	private Long userId;//上传用户id
	private Long materialType;///物资分类
	private String materialName;///物资名称
	private String size;///规格尺寸
	private String unit;///单位
	private Integer inNum;///入库数量
	private Integer outNum;///出库数量
	private Integer leaveNum;///库存数量
	private Date createDate;///物资新建时间
	private String remark;///备注
	
	@GeneratedValue
	@Id
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	@Column(name ="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@Basic
	@Column(name ="user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Basic
	@Column(name ="material_type")
	public Long getMaterialType() {
		return materialType;
	}
	public void setMaterialType(Long materialType) {
		this.materialType = materialType;
	}
	
	@Basic
	@Column(name ="material_name")
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	@Basic
	@Column(name ="size")
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	@Basic
	@Column(name ="unit")
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Basic
	@Column(name ="in_num")
	public Integer getInNum() {
		return inNum;
	}
	public void setInNum(Integer inNum) {
		this.inNum = inNum;
	}
	
	@Basic
	@Column(name ="out_num")
	public Integer getOutNum() {
		return outNum;
	}
	public void setOutNum(Integer outNum) {
		this.outNum = outNum;
	}
	
	
	@Basic
	@Column(name ="leave_num")
	public Integer getLeaveNum() {
		return leaveNum;
	}
	public void setLeaveNum(Integer leaveNum) {
		this.leaveNum = leaveNum;
	}
	
	@Basic
	@Column(name ="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Basic
	@Column(name ="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
