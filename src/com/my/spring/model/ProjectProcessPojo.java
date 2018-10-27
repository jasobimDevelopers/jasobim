package com.my.spring.model;


/**
* @author 徐雨祥
* @version 创建时间：2018年10月25日 上午8:39:59
* 类说明
*/
public class ProjectProcessPojo {
	private Long id;
	private Long projectId;
    /* 上级Id */
    private Long parentId;
    /* 结构层级 */
    private Integer level;
    /* 任务名称 */
    private String taskName;
    /* 工期 */
    private Integer durationDate;
    /* 开始时间 */
    private String startDate;
    /* 结束时间 */
    private String endDate;
    /* 导入时间 */
    private String importTime;
	//private 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Integer getDurationDate() {
		return durationDate;
	}
	public void setDurationDate(Integer durationDate) {
		this.durationDate = durationDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getImportTime() {
		return importTime;
	}
	public void setImportTime(String importTime) {
		this.importTime = importTime;
	}
    
}
