package com.my.spring.model;

public class ProjectIds {
	private Long projectId;
	private String projectName;
	private int workHour;
	private int nightWorkHour;
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getWorkHour() {
		return workHour;
	}
	public void setWorkHour(int workHour) {
		this.workHour = workHour;
	}
	public int getNightWorkHour() {
		return nightWorkHour;
	}
	public void setNightWorkHour(int nightWorkHour) {
		this.nightWorkHour = nightWorkHour;
	}
	
}
