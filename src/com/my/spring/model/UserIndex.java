package com.my.spring.model;
import javax.persistence.*;
/**
* @author 徐雨祥
* @version 创建时间：2018年9月28日 下午1:28:30
* 类说明
*/
@Entity
@Table(name="user_index")
public class UserIndex {
	private Long id;
	private Long userId;
	private Long aboutId;
	private Integer aboutType;////0.组织架构（department）1.职务权限（role）2.班组管理（userTeam）3.节点管理（itemDate）4.流程管理（processData）5.标段管理（projectTender）
	private Long indexs;
	
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
	@Column(name="about_id")
	public Long getAboutId() {
		return aboutId;
	}
	public void setAboutId(Long aboutId) {
		this.aboutId = aboutId;
	}
	
	@Basic
	@Column(name="about_type")
	public Integer getAboutType() {
		return aboutType;
	}
	public void setAboutType(Integer aboutType) {
		this.aboutType = aboutType;
	}
	
	@Basic
	@Column(name="indexs")
	public Long getIndexs() {
		return indexs;
	}
	public void setIndexs(Long indexs) {
		this.indexs = indexs;
	}
	
	
	
}
