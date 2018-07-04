package com.my.spring.model;
import javax.persistence.*;
@Entity
@Table(name="process_item")
public class ProcessItem {
	private Long id;
	private Long processId;
	private Long itemId;
	private Integer which;//当前第几个节点
	
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
	@Column(name="process_id")
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	
	@Basic
	@Column(name="item_id")
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	@Basic
	@Column(name="which")
	public Integer getWhich() {
		return which;
	}
	public void setWhich(Integer which) {
		this.which = which;
	}
	
	
}
