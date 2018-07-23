package com.my.spring.model;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name="construction_task_new")
public class ConstructionTaskNew {
	private Long id;
	private String name;
	private Long createUser;
	private Date createDate;
	private String constructionTaskDate;///施工任务单日期
	private Long processDataId;//审批流程：创建人-->班组长-->质量员-->安全员或其他-->施工员-->预算员-->经理
	private String constructContent;//施工内容
	private String constructType;//施工类型
	private Integer teamType;//0、自有技工(自有技工)  1、班组技工（班组信息里面的成员）
	private String teamUserIds;//班组技工人员（12,15,16）
	private Integer dayWorkHours;//白天工作时间
	private Integer nightWorkHours;//夜班时间
	private Long tendersId;///标段id
	private String constructPart;//施工部位
	private Long pid;
	private String imgs;//(12,13)
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
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name="create_user")
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	
	@Basic
	@Column(name="process_data_id")
	public Long getProcessDataId() {
		return processDataId;
	}
	public void setProcessDataId(Long processDataId) {
		this.processDataId = processDataId;
	}
	
	@Basic
	@Column(name="construct_part")
	public String getConstructPart() {
		return constructPart;
	}
	public void setConstructPart(String constructPart) {
		this.constructPart = constructPart;
	}
	
	@Basic
	@Column(name="construct_task_date")
	public String getConstructionTaskDate() {
		return constructionTaskDate;
	}
	public void setConstructionTaskDate(String constructionTaskDate) {
		this.constructionTaskDate = constructionTaskDate;
	}
	
	@Basic
	@Column(name="construct_content")
	public String getConstructContent() {
		return constructContent;
	}
	public void setConstructContent(String constructContent) {
		this.constructContent = constructContent;
	}
	
	@Basic
	@Column(name="construct_type")
	public String getConstructType() {
		return constructType;
	}
	public void setConstructType(String constructType) {
		this.constructType = constructType;
	}
	
	@Basic
	@Column(name="team_type")
	public Integer getTeamType() {
		return teamType;
	}
	public void setTeamType(Integer teamType) {
		this.teamType = teamType;
	}
	
	@Basic
	@Column(name="team_user_ids")
	public String getTeamUserIds() {
		return teamUserIds;
	}
	public void setTeamUserIds(String teamUserIds) {
		this.teamUserIds = teamUserIds;
	}
	
	@Basic
	@Column(name="day_work_hours")
	public Integer getDayWorkHours() {
		return dayWorkHours;
	}
	public void setDayWorkHours(Integer dayWorkHours) {
		this.dayWorkHours = dayWorkHours;
	}
	
	@Basic
	@Column(name="night_work_hours")
	public Integer getNightWorkHours() {
		return nightWorkHours;
	}
	public void setNightWorkHours(Integer nightWorkHours) {
		this.nightWorkHours = nightWorkHours;
	}
	
	@Basic
	@Column(name="tenders_id")
	public Long getTendersId() {
		return tendersId;
	}
	public void setTendersId(Long tendersId) {
		this.tendersId = tendersId;
	}
	
	@Basic
	@Column(name="imgs")
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
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
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@Basic
	@Column(name="pid")
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	
	
}
