package com.my.spring.model;
import javax.persistence.*;
@Entity
@Table(name="work_type")
public class WorkType {
	private Long id;
	private String name;
	private Integer teamType;//0.自有技工  1.班组技工
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
	@Column(name="team_type")
	public Integer getTeamType() {
		return teamType;
	}
	public void setTeamType(Integer teamType) {
		this.teamType = teamType;
	}
}
