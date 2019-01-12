package com.my.spring.model;
import javax.persistence.*;
@Entity
@Table(name="quality_check_read_state")
public class QualityCheckReadState {
	private Long id;
	private Long userId;
	private Long qualityCheckId;
	private Integer state;
	
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
	@Column(name="quality_check_id")
	public Long getQualityCheckId() {
		return qualityCheckId;
	}
	public void setQualityCheckId(Long qualityCheckId) {
		this.qualityCheckId = qualityCheckId;
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
