package com.my.spring.model;

import java.util.Date;
import javax.persistence.*;
@Entity
@Table(name="paper_point_nums_log")
public class PaperPointNumsLog {
	private Long id;
	private Long userId;
	private Long projectId;
	private Integer pointNums;//总点位
	private Integer doneNums;//已测点位
	private Integer problemNums;//爆点点位
	private String buildingNames;//楼栋筛选条件
	private String checkTypes;//检查项筛选条件
	private Date createDate;
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
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Basic
	@Column(name="building_names")
	public String getBuildingNames() {
		return buildingNames;
	}
	public void setBuildingNames(String buildingNames) {
		this.buildingNames = buildingNames;
	}
	
	@Basic
	@Column(name="check_types")
	public String getCheckTypes() {
		return checkTypes;
	}
	public void setCheckTypes(String checkTypes) {
		this.checkTypes = checkTypes;
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
	@Column(name="point_nums")
	public Integer getPointNums() {
		return pointNums;
	}
	public void setPointNums(Integer pointNums) {
		this.pointNums = pointNums;
	}
	
	@Basic
	@Column(name="done_nums")
	public Integer getDoneNums() {
		return doneNums;
	}
	public void setDoneNums(Integer doneNums) {
		this.doneNums = doneNums;
	}
	
	@Basic
	@Column(name="problem_nums")
	public Integer getProblemNums() {
		return problemNums;
	}
	public void setProblemNums(Integer problemNums) {
		this.problemNums = problemNums;
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
