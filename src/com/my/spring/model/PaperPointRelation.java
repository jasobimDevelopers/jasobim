package com.my.spring.model;
import javax.persistence.*;
@Entity
@Table(name="paper_point_relation")
public class PaperPointRelation {
	/*每新增一条paperPointInfoItem,同时新增一条关系数据*/
	private Long id;
	private Long paperOfMeasuredId;
	private Long pointId;
	private Long checkTypeId;
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
	@Column(name="paper_of_measured_id")
	public Long getPaperOfMeasuredId() {
		return paperOfMeasuredId;
	}
	public void setPaperOfMeasuredId(Long paperOfMeasuredId) {
		this.paperOfMeasuredId = paperOfMeasuredId;
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
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
}
