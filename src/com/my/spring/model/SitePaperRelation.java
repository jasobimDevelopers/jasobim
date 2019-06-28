package com.my.spring.model;
import javax.persistence.*;
@Entity
@Table(name="site_paper_relation")
public class SitePaperRelation {
	/*每新增一个measuredSite，添加一个SitePaperRelation*/
	/*删除时同时进行*/
	private Long id;
	private Long projectId;
	private Long siteId;
	private Long bfmId;
	private Long paperOfMeasuredId;
	
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
	@Column(name="project_id")
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@Basic
	@Column(name="site_id")
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	
	@Basic
	@Column(name="bfm_id")
	public Long getBfmId() {
		return bfmId;
	}
	public void setBfmId(Long bfmId) {
		this.bfmId = bfmId;
	}
	
	@Basic
	@Column(name="paper_of_measured_id")
	public Long getPaperOfMeasuredId() {
		return paperOfMeasuredId;
	}
	public void setPaperOfMeasuredId(Long paperOfMeasuredId) {
		this.paperOfMeasuredId = paperOfMeasuredId;
	}
	
}
