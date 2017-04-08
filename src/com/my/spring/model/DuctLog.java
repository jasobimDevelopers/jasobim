  package com.my.spring.model;

import javax.persistence.Basic;
import javax.persistence.Column;

import java.util.Date;

import javax.persistence.*;
import javax.persistence.GeneratedValue;

@Entity
@Table(name ="duct_log")
public class DuctLog {
	private Long id;
	private Long ductId;
	private Integer state;///0.未指定状态  1.出库 2.安装 3.完成 
	private Date date;/////当时的时间
	private Long userId;///用户id
	
	@Id
    @GeneratedValue
    @Column(name = "id")
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}
	
	@Basic
    @Column(name = "date")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Basic
    @Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Basic
    @Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Basic
    @Column(name = "duct_id")
	public Long getDuctId() {
		return ductId;
	}

	public void setDuctId(Long ductId) {
		this.ductId = ductId;
	}
	
}
