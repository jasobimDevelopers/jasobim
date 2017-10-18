package com.my.spring.model;



public class QuestionPojo {
	private Long id;       ////问题编号
	private Long projectId;////项目工程id
	private Integer questionType;////问题类型(0.安全1.质量2.其他)
	private String userId;  ///问题提交人id
	private String name;  ///问题名称
	private String trades;///问题专业
	private String intro; //////问题内容
	private String questionDate;/////问题的创建时间
	private Integer priority;////问题等级 (0.一般 1.重要 2.紧急)
	private Integer state;   ///问题的状态(0.待解决 1.已解决)
	private String codeInformation;/////图片的二维码信息
	private String[] fileList;////问题照片
	private String[] originName;///问题照片的名称
	private int sortPercent;/////一般问题的百分比
	private int importantPercent;/////重要问题的百分比
	private int urgentPercent;//////紧急问题的百分比
	private String position;//位置
	private String[] userNameLists;
	private String voiceUrlList;
	private Integer userid;
	private Integer roleFlag;//0.普通用户（只能看指定的项目的所有内容）
							 //1.总经理(能看所有，但是问题只看紧急和重要) 
	                         //2.投资方（只能看所述项目的内容，另外项目里面的工程量和紧急和重要问题不能看）
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
	public Integer getQuestionType() {
		return questionType;
	}
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String strings) {
		this.userId = strings;
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
	
	
	public String getQuestionDate() {
		return questionDate;
	}
	public void setQuestionDate(String date) {
		this.questionDate = date;
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
	
	
	public String getCodeInformation() {
		return codeInformation;
	}
	public void setCodeInformation(String codeInformation) {
		this.codeInformation = codeInformation;
	}
	public String[] getFileList() {
		return fileList;
	}
	public void setFileList(String[] fileList) {
		this.fileList = fileList;
	}
	public String[] getOriginName() {
		return originName;
	}
	public void setOriginName(String[] originName) {
		this.originName = originName;
	}
	public int getSortPercent() {
		return sortPercent;
	}
	public void setSortPercent(int sortPercent) {
		this.sortPercent = sortPercent;
	}
	public int getImportantPercent() {
		return importantPercent;
	}
	public void setImportantPercent(int importantPercent) {
		this.importantPercent = importantPercent;
	}
	public int getUrgentPercent() {
		return urgentPercent;
	}
	public void setUrgentPercent(int urgentPercent) {
		this.urgentPercent = urgentPercent;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String[] getUserNameLists() {
		return userNameLists;
	}
	public void setUserNameLists(String[] userNameLists) {
		this.userNameLists = userNameLists;
	}
	public Integer getRoleFlag() {
		return roleFlag;
	}
	public void setRoleFlag(Integer roleFlag) {
		this.roleFlag = roleFlag;
	}
	public String getVoiceUrlList() {
		return voiceUrlList;
	}
	public void setVoiceUrlList(String voiceUrlList) {
		this.voiceUrlList = voiceUrlList;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	

}
