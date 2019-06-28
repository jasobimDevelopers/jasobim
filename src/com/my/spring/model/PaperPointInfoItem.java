package com.my.spring.model;
import javax.persistence.*;
@Entity
@Table(name="paper_point_info_item")
public class PaperPointInfoItem {//测量点位单条数据表模板表

	private Long id;
	private Long pointId;//测点id
	private Long checkTypeId;//检查项id
	private Integer standardNum;//设计值/标准值
	private Long projectId;//项目id
	private String flag;//唯一性标签（楼栋名称+房间号+测点序列号）
	private Integer errorUpperLimit;//误差上限
	private Integer errorLowerLimit;//误差下限
	
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
	@Column(name="point_id")
	public Long getPointId() {
		return pointId;
	}
	public void setPointId(Long pointId) {
		this.pointId = pointId;
	}
	
	@Basic
	@Column(name="check_type_id")
	public Long getCheckTypeId() {
		return checkTypeId;
	}
	public void setCheckTypeId(Long checkTypeId) {
		this.checkTypeId = checkTypeId;
	}
	
	@Basic
	@Column(name="standard_num")
	public Integer getStandardNum() {
		return standardNum;
	}
	public void setStandardNum(Integer standardNum) {
		this.standardNum = standardNum;
	}
	
	@Basic
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@Basic
	@Column(name="flag")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Basic
	@Column(name="error_upper_limit")
	public Integer getErrorUpperLimit() {
		return errorUpperLimit;
	}
	public void setErrorUpperLimit(Integer errorUpperLimit) {
		this.errorUpperLimit = errorUpperLimit;
	}
	
	@Basic
	@Column(name="error_lower_limit")
	public Integer getErrorLowerLimit() {
		return errorLowerLimit;
	}
	public void setErrorLowerLimit(Integer errorLowerLimit) {
		this.errorLowerLimit = errorLowerLimit;
	}
	
}
