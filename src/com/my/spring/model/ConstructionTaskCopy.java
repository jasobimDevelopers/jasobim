package com.my.spring.model;

import java.util.Date;

public class ConstructionTaskCopy {
	private Long id;
	private String company_name;//劳务公司名称
	private String work_people_name_list;
	private Date create_date;//任务单创建时间
	private Long user_id;////任务单创建人id
	private String detail_content;////任务单交底内容
	///////审批流程
	private Long project_id;////项目id
	//////图片
	private String file_id_list;
	private Integer total;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getCreateDate() {
		return create_date;
	}
	public void setCreateDate(Date createDate) {
		this.create_date = createDate;
	}
	
	public Long getUserId() {
		return user_id;
	}
	public void setUserId(Long userId) {
		this.user_id = userId;
	}
	
	public String getDetailContent() {
		return detail_content;
	}
	public void setDetailContent(String detailContent) {
		this.detail_content = detailContent;
	}
	
	public String getCompanyName() {
		return company_name;
	}
	public void setCompanyName(String companyName) {
		this.company_name = companyName;
	}
	
	public Long getProjectId() {
		return project_id;
	}
	public void setProjectId(Long projectId) {
		this.project_id = projectId;
	}
	
	public String getWorkPeopleNameList() {
		return work_people_name_list;
	}
	public void setWorkPeopleNameList(String workPeopleNameList) {
		this.work_people_name_list = workPeopleNameList;
	}
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getFileIdList() {
		return file_id_list;
	}
	public void setFileIdList(String file_id_list) {
		this.file_id_list = file_id_list;
	}
	
	
}
