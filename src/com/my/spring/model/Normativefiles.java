package com.my.spring.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="normative_files")
public class Normativefiles {
	private Long id;
	private String content;////文件内容
	private String fileIdList;/////文件id列表
	private String fileTypeList;////文件类型列表（图片、word文档、pdf、视频、其他）
	private Date submitDate;////提交时间
	private Long submitUserId;///提交人id
	private String title;////标题
	private String describes;////描述
	private String remark;//备注
	private Long size;//
	private Integer studyType;////0、建筑施工规范   1、建筑设计规范   2、建筑结构规范 3、市政路桥 4、造价参考资料 5、国际参考资料 6、当地参考资料
							  ////7、学习辅导资料 8、未分类资料 9、人防工程资料 10、水利电力通讯 
	
	@Id
	@GeneratedValue
    @Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Basic
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Basic
	@Column(name = "size")
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	
	@Basic 
	@Column(name = "file_id_list")
	public String getFileIdList() {
		return fileIdList;
	}
	public void setFileIdList(String fileIdList) {
		this.fileIdList = fileIdList;
	}
	
	@Basic
	@Column(name = "file_type_list")
	public String getFileTypeList() {
		return fileTypeList;
	}
	public void setFileTypeList(String fileTypeList) {
		this.fileTypeList = fileTypeList;
	}
	
	@Basic 
	@Column(name = "submit_date")
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	
	@Basic 
	@Column(name = "submit_user_id")
	public Long getSubmitUserId() {
		return submitUserId;
	}
	public void setSubmitUserId(Long submitUserId) {
		this.submitUserId = submitUserId;
	}
	
	
	@Basic
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Basic
	@Column(name = "describes")
	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	
	@Basic 
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Basic 
	@Column(name = "study_type")
	public Integer getStudyType() {
		return studyType;
	}
	public void setStudyType(Integer studyType) {
		this.studyType = studyType;
	}
	
	
}
