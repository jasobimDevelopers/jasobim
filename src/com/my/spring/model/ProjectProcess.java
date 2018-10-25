package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;

/**
* @author 徐雨祥
* @version 创建时间：2018年10月24日 下午12:42:13
* 类说明
*/
@Entity
@Table(name="project_process")
public class ProjectProcess {
	private Long id;
	private Long projectId;
    /* 上级Id */
    private Long parentId;
    /* 结构层级 */
    private Integer level;
    /* 任务名称 */
    private String taskName;
    /* 工期 */
    private String durationDate;
    /* 开始时间 */
    private Date startDate;
    /* 结束时间 */
    private Date endDate;
    /* 前置任务ID */
    private Integer preTask;
    /* 资源名称 */
    private String resource;
    /* 导入时间 */
    private Date importTime;
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
	@Column(name="level")
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	@Basic
	@Column(name="task_name")
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	@Basic
	@Column(name="duration_date")
	public String getDurationDate() {
		return durationDate;
	}
	public void setDurationDate(String durationDate) {
		this.durationDate = durationDate;
	}
	
	@Basic
	@Column(name="start_date")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Basic
	@Column(name="end_date")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Basic
	@Column(name="pre_task")
	public Integer getPreTask() {
		return preTask;
	}
	public void setPreTask(Integer preTask) {
		this.preTask = preTask;
	}
	
	@Basic
	@Column(name="resource")
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	@Basic
	@Column(name="import_time")
	public Date getImportTime() {
		return importTime;
	}
	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}
	
	@Basic
	@Column(name="parent_id")
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
