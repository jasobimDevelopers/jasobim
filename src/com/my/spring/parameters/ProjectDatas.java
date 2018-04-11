package com.my.spring.parameters;
import java.io.Serializable;
public enum ProjectDatas implements Serializable {
	//public static final Integer[] projectPart = {//0.Model(模型) 1.Paper,//图纸   2.Login,//登录  3.Disclose,//交底   4.Prefabricate,//预制化   
			//5.Question,//紧急事项（问题）6.Notification,//通知 7.Production,//产值    8.Member,//班组信息 };
    Model_area("model",0),///模型
    Paper_area("paper", 1),///图纸区域
    Login_area("login", 2),///登录区域
    Video_area("video", 3),///交底区域
    ProcessManager_area("process_manager",4),////预制化进度管理区域
    Question_area("question",5),///安全区域
    Notification_area("notification",6),
    ValueOut_area("value_out",7),///产值统计管理区域
    UserTeam_area("user_team",8),///班组信息区域
    ConstructionTask_area("construction_task",9),///施工任务单区域
    AdvancedOrder_area("advanced_order",10),///预付单区域
    NormativeFile_area("normative_file",11),///在线预览区域
    Item_area("item",12),///模型构建信息区域
    Quality_area("question",5)///质量区域
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

