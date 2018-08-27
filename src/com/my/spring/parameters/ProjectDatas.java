package com.my.spring.parameters;
import java.io.Serializable;
public enum ProjectDatas implements Serializable {
	//public static final Integer[] projectPart = {//0.Model(模型) 1.Paper,//图纸   2.Login,//登录  3.Disclose,//交底   4.Prefabricate,//预制化   
			//5.Question,//紧急事项（问题）6.Notification,//通知 7.Production,//产值    8.Member,//班组信息 };
    Model_area("model",0),///模型
    Paper_area("paper", 1),///图纸区域
    Login_area("login", 2),///首页区域
    Video_area("video", 3),///交底区域
    ProcessManager_area("process_manager",4),////（预制化）进度管理区域
    Question_area("question",5),///安全区域
    Notification_area("notification",6),///消息通知
    ValueOut_area("value_out",7),///（产值）统计管理区域
    UserTeam_area("user_team",8),///（密码修改、意见反馈）个人中心区域
    NormativeFile_area("normative_file",9),///规范查阅区域
    Item_area("item",10),///模型构建信息区域
    Quality_area("question",11),///质量区域
    NewsInfo_area("newsInfo",12),//新闻资讯
    MeasuredData_area("measuredData",13),///实测实量
    FileManager_area("fileManager",14),///云盘管理
    MaterialManager_area("material",15),///物资管理
    MechanicPrice_area("mechanicPrice",16),///劳动力监测、劳务管理
    AttenceLog_area("attenceLog",17),///考勤管理
    QuanlityChange_area("quanlity",18),//工程量变更
    ProcessData_area("processData",19),//进程管理
    AdvancedOrder_area("advancedOrder",20),//施工任务单
    ConstructionTask_area("constructionTask",21),//预付单
    ProductionManager_projectDetail("projectDetail",22)//生产管理
    ;
    private String label;
    private Integer code;
    ProjectDatas() {
    }
    ProjectDatas(String label, Integer code) {
        this.label = label;
        this.code = code;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public int getCode() {
        return code;
    }
    @Override
    public String toString() {
        return code.toString();
    }
    public static ProjectDatas parse(int code) {
        for (ProjectDatas theEnum : ProjectDatas.values()) {
            if (theEnum.getCode() == code) {
                return theEnum;
            }
        }
        return Login_area;
    }
}

