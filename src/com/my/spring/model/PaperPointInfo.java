package com.my.spring.model;

import java.util.Date;
import javax.persistence.*;
@Entity
@Table(name="paper_point_info")
public class PaperPointInfo {
	private Long pointId;
	private Long projectId;
	private Long paperOfMeasuredId;//户型名称
	private Integer abscissa;//横坐标
	private Integer ordinate;//纵坐标
	private Double proportion;//比例
	private Double paperLength;//图纸长度
	private Double paperWidth;//图纸宽度
	private Integer errorUpperLimit;//误差上限
	private Integer errorLowerLimit;//误差下限
	private Integer pointNum;//测量点位总数
	private Integer standard;//测量标准
	private Date createDate;
	private Long createUser;
	
	@Id
	@GeneratedValue
	@Column(name="point_id")
	public Long getPointId() {
		return pointId;
	}
	public void setPointId(Long pointId) {
		this.pointId = pointId;
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
	@Column(name="abscissa")
	public Integer getAbscissa() {
		return abscissa;
	}
	public void setAbscissa(Integer abscissa) {
		this.abscissa = abscissa;
	}
	@Basic
	@Column(name="ordinate")
	public Integer getOrdinate() {
		return ordinate;
	}
	public void setOrdinate(Integer ordinate) {
		this.ordinate = ordinate;
	}
	@Basic
	@Column(name="proportion")
	public Double getProportion() {
		return proportion;
	}
	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}
	@Basic
	@Column(name="paper_length")
	public Double getPaperLength() {
		return paperLength;
	}
	public void setPaperLength(Double paperLength) {
		this.paperLength = paperLength;
	}
	@Basic
	@Column(name="paper_width")
	public Double getPaperWidth() {
		return paperWidth;
	}
	public void setPaperWidth(Double paperWidth) {
		this.paperWidth = paperWidth;
	}
	@Basic
	@Column(name="point_num")
	public Integer getPointNum() {
		return pointNum;
	}
	public void setPointNum(Integer pointNum) {
		this.pointNum = pointNum;
	}
	
	@Basic
	@Column(name="standard")
	public Integer getStandard() {
		return standard;
	}
	public void setStandard(Integer standard) {
		this.standard = standard;
	}
	@Basic
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Basic
	@Column(name="create_user")
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	@Basic
	@Column(name="error_upper_limit")
	public Integer getErrorUpperLimit() {
		return errorUpperLimit;
	}
	public void setErrorUpperLimit(Integer errorUpperLimit) {
		this.errorUpperLimit = errorUpperLimit;
	}
	@Basic
	@Column(name="error_lower_limit")
	public Integer getErrorLowerLimit() {
		return errorLowerLimit;
	}
	public void setErrorLowerLimit(Integer errorLowerLimit) {
		this.errorLowerLimit = errorLowerLimit;
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
