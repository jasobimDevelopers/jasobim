package com.my.spring.model;
import javax.persistence.*;
@Entity
@Table(name="quality_rectification_read_state")
public class QualityRectificationReadState {
	private Long id;
	private Long qualityRectificationId;
	private Long userId;
	private Integer state;//0、未读 1、已读
	
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
	@Column(name="quality_rectification_id")
	public Long getQualityRectificationId() {
		return qualityRectificationId;
	}
	public void setQualityRectificationId(Long qualityRectificationId) {
		this.qualityRectificationId = qualityRectificationId;
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
	
}
