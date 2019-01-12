package com.my.spring.model;
import javax.persistence.*;
@Entity
@Table(name="relation")
public class Relation {
	private Long id;
	private Integer relationType;
	private Long aboutId;
	private Long userId;
	private Integer state;
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
	@Column(name="relation_type")
	public Integer getRelationType() {
		return relationType;
	}
	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}
	@Basic
	@Column(name="about_id")
	public Long getAboutId() {
		return aboutId;
	}
	public void setAboutId(Long aboutId) {
		this.aboutId = aboutId;
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
	@Column(name="state")
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
