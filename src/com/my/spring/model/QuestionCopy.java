package com.my.spring.model;

import java.util.Date;

public class QuestionCopy {
	private Long id;       ////问题编号
	private Long project_id;////项目工程id
	//private Integer question_type;////问题类型(0.安全1.质量2.其他)
	private Long user_id;  ///问题提交人id
	private String name;  ///问题名称
	private String trades;///问题专业
	private String intro; //////问题内容
	private Date question_date;/////问题的创建时间
	private Integer priority;////问题等级 (0.一般 1.重要 2.紧急)
	private Integer state;   ///问题的状态(0.待解决 1.已解决)
	private String code_information;/////图片的二维码信息
	private String position;///图片位置
	private String model_flag;
	//////
	private String voice_id_list;//语音idList
	private String user_list;///接收到人
	private Integer total;
	//private String readState;///接收人的已读和未读情况
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProject_id() {
		return project_id;
	}
	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTrades() {
		return trades;
	}
	public void setTrades(String trades) {
		this.trades = trades;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public Date getQuestion_date() {
		return question_date;
	}
	public void setQuestion_date(Date question_date) {
		this.question_date = question_date;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getCode_information() {
		return code_information;
	}
	public void setCode_information(String code_information) {
		this.code_information = code_information;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getModel_flag() {
		return model_flag;
	}
	public void setModel_flag(String model_flag) {
		this.model_flag = model_flag;
	}
	public String getVoice_id_list() {
		return voice_id_list;
	}
	public void setVoice_id_list(String voice_id_list) {
		this.voice_id_list = voice_id_list;
	}
	public String getUser_list() {
		return user_list;
	}
	public void setUser_list(String user_list) {
		this.user_list = user_list;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
