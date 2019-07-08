package com.my.spring.model;

import java.util.List;

public class MeasuredProblemPojo {
	private Long id;
	private String checkSite;//检查部位
	private String checkDate;//检查日期
	private String checkUser;//检查人
	private String checkLists;//检查项
	private String detail;//问题描述
	private List<MeasuredUserInfo> aboutIcons;
	private String rectifyUser;//整改人
	private List<String> rectifyUserNames;
	private String finishedDate;//限定完成时间
	private Integer status;//0、待指派 1、进行中 2、待销项
	private Integer process;//进度百分比
	private List<String> files;
	private List<String> voices;
	private String content;
	private String contentDetail;
	private String title;
	private Long pointId;
	private Integer score;
	private String createUser;
	private String createDate;
	private Integer abscissa;//横坐标
	private Integer ordinate;//纵坐标
	private String paperUrl;
	private String projectName;
	
	
	public Integer getAbscissa() {
		return abscissa;
	}
	public void setAbscissa(Integer abscissa) {
		this.abscissa = abscissa;
	}
	public Integer getOrdinate() {
		return ordinate;
	}
	public void setOrdinate(Integer ordinate) {
		this.ordinate = ordinate;
	}
	public String getPaperUrl() {
		return paperUrl;
	}
	public void setPaperUrl(String paperUrl) {
		this.paperUrl = paperUrl;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCheckSite() {
		return checkSite;
	}
	public void setCheckSite(String checkSite) {
		this.checkSite = checkSite;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getCheckLists() {
		return checkLists;
	}
	public void setCheckLists(String checkLists) {
		this.checkLists = checkLists;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getRectifyUser() {
		return rectifyUser;
	}
	public void setRectifyUser(String rectifyUser) {
		this.rectifyUser = rectifyUser;
	}
	public String getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(String finishedDate) {
		this.finishedDate = finishedDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getProcess() {
		return process;
	}
	public void setProcess(Integer process) {
		this.process = process;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
	public List<String> getVoices() {
		return voices;
	}
	public void setVoices(List<String> voices) {
		this.voices = voices;
	}
	
	public Long getPointId() {
		return pointId;
	}
	public void setPointId(Long pointId) {
		this.pointId = pointId;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContentDetail() {
		return contentDetail;
	}
	public void setContentDetail(String contentDetail) {
		this.contentDetail = contentDetail;
	}
	public List<MeasuredUserInfo> getAboutIcons() {
		return aboutIcons;
	}
	public void setAboutIcons(List<MeasuredUserInfo> aboutIcons) {
		this.aboutIcons = aboutIcons;
	}
	public List<String> getRectifyUserNames() {
		return rectifyUserNames;
	}
	public void setRectifyUserNames(List<String> rectifyUserNames) {
		this.rectifyUserNames = rectifyUserNames;
	}
	
}
