package com.my.spring.model;
import javax.persistence.*;
@Entity
@Table(name="award_read_state")
public class AwardReadState {
	private Long id;
	private Long userId;
	private Long awardId;
	private Integer state;
	private Integer qualityType;//0、质量 1、安全
	
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
	@Column(name="award_id")
	public Long getAwardId() {
		return awardId;
	}
	public void setAwardId(Long awardId) {
		this.awardId = awardId;
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
	@Column(name="quality_type")
	public Integer getQualityType() {
		return qualityType;
	}
	public void setQualityType(Integer qualityType) {
		this.qualityType = qualityType;
	}
	
}
