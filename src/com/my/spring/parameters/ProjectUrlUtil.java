package com.my.spring.parameters;
import java.util.HashMap;
public class ProjectUrlUtil {
	private static HashMap<String,Integer> urlList = new HashMap<String,Integer>();
	public ProjectUrlUtil(String key,Integer value){
		urlList.put(key, value);
	}
	
	public ProjectUrlUtil(){
		/**
		 * app接口
		 * */
		
		/*新闻资讯列表获取*/
		urlList.put("/api/newsInfo/admin/getNewsInfoList", ProjectDatas.NewsInfo_area.getCode());
		/*新闻资讯添加*/
		urlList.put("/api/newsInfo/admin/addNewsInfo", ProjectDatas.NewsInfo_area.getCode());
		/*项目交底列表获取接口*/
		urlList.put("/api/video/admin/getVideoList", ProjectDatas.Video_area.getCode());
		urlList.put("/api/video/admin/addVideo", ProjectDatas.Video_area.getCode());//交底新增
		/*施工任务单*/
		urlList.put("/api/constructionTaskNew/admin/addConstructionTaskNew", ProjectDatas.ConstructionTask_area.getCode());//施工任务单
		urlList.put("/api/constructionTaskNew/admin/getConstructionTaskNewList", ProjectDatas.ConstructionTask_area.getCode());//施工任务单
		urlList.put("/api/constructionTaskNew/getConstructionTaskNewDetail", ProjectDatas.ConstructionTask_area.getCode());//施工任务单
		/*预付单*/
		
		/*安全管理*/
		urlList.put("/api/question/addQuestion",ProjectDatas.Question_area.getCode());///安全问题添加
		urlList.put("/api/question/admin/getQuestionList",ProjectDatas.Question_area.getCode());///安全问题查找
		/*质量管理*/
		urlList.put("/api/quality/addQuality",ProjectDatas.Quality_area.getCode());/////质量问题添加
		urlList.put("/api/quality/admin/getQualityList", ProjectDatas.Quality_area.getCode());///质量问题查找
		/*考勤管理*/
		urlList.put("/api/mechanic/getMechanicInfos", ProjectDatas.MechanicPrice_area.getCode());///考勤管理
		urlList.put("/api/attenceLog/addAttenceLog", ProjectDatas.AttenceLog_area.getCode());///考勤打卡
		/*云盘协同*/
		urlList.put("/api/folder/getFolderIndexList", ProjectDatas.FileManager_area.getCode());///文件云盘（云盘协同）
		/*图纸查看*/
		urlList.put("/api/paper/admin/getPaperLists", ProjectDatas.Paper_area.getCode());///图纸查看
		urlList.put("/api/paper/admin/uploadPaper", ProjectDatas.Paper_area.getCode());//图纸上传
		/*规范查阅*/
		urlList.put("/api/normativefiles/getNormativefilesLists", ProjectDatas.NormativeFile_area.getCode());//规范查阅
		
		/*模型浏览*/
		urlList.put("/api/bimface/getModelViewToken", ProjectDatas.Model_area.getCode());
		/*统计管理*/
		urlList.put("/api/valueOutput/getValueOutputListnew", ProjectDatas.ValueOut_area.getCode());//项目产值获取(统计管理)
		/*劳动力监测*/
		urlList.put("/api/mechanicPrice/app/getMechanicDatas", ProjectDatas.MechanicPrice_area.getCode());//劳动力监测
		/*进度管理*/
		urlList.put("/api/duct/app/getDuctNums", ProjectDatas.ProcessManager_area.getCode());//进度管理
		/*物资管理*/
		urlList.put("/api/material/app/getImportMaterialLog", ProjectDatas.MaterialManager_area.getCode());//物资管理
		urlList.put("/api/material/app/importMaterial", ProjectDatas.MaterialManager_area.getCode());//物资导入
		/*实测实量*/
		urlList.put("/api/measuredData/addMeasuredData", ProjectDatas.MeasuredData_area.getCode());//实测实量数据添加
		/*消息*/
		urlList.put("/api/notice/app/getAllNoticeList", ProjectDatas.Notification_area.getCode());//消息
		
		/**
		 * web接口
		 * */
		
		/*生产管理-----项目基本信息*/
		urlList.put("/api/project/admin/getProjectDetails", ProjectDatas.ProductionManager_projectDetail.getCode());///生产管理（项目详情）
		urlList.put("/api/project/admin/updateProject", ProjectDatas.ProductionManager_projectDetail.getCode());//生产管理（项目详情）
		/*生产管理-----施工任务单+1*/
		
		/*生产管理-----预付单*/
		
		/*生产管理-----构件信息*/
		urlList.put("/api/item/admin/getItemList", ProjectDatas.Item_area.getCode());////模型构件信息
		/*生产管理-----施工日志*/
		urlList.put("/api/constructionLog/web/getConstructionLogList", ProjectDatas.ConstructLog_area.getCode());
		/*成本管理-----预算*/
		urlList.put("/api/budget/vs/getBudgetList", ProjectDatas.CostManager_area.getCode());
		/*统计管理-----用工统计*/
		urlList.put("/api/mechanicPrice/getMechanicPriceNum", ProjectDatas.MechanicPrice_area.getCode());
		/*统计管理-----考勤管理*/
		urlList.put("/api/attenceLog/getAttenceLogList", ProjectDatas.AttenceLog_area.getCode());//考勤记录获取
		/*统计管理-----产值统计*/
		urlList.put("/api/valueOutput/admin/getValueOutputLists", ProjectDatas.ValueOut_area.getCode());
		/*质量管理-----质量整改单*/
		urlList.put("/api/quality/admin/getQualityHash", ProjectDatas.Quality_area.getCode());
		/*质量管理-----质量罚款单*/
		urlList.put("/api/qualityFine/getQualityFineList", ProjectDatas.QualityFine_area.getCode());
		/*安全管理-----安全整改单*/
		urlList.put("/api/question/admin/getQuestionHash", ProjectDatas.Question_area.getCode());
		/*安全管理-----安全罚款单*/
		urlList.put("/api/safeFine/getSafeFineList", ProjectDatas.QuestionFine_area.getCode());
		/*物资管理-----物资统计*/
		urlList.put("/api/material/vs/getMaterialList", ProjectDatas.MaterialManager_area.getCode());//物资管理
		/*物资管理-----材料计划*/
		urlList.put("/api/materialPlan/getMaterialPlanList", ProjectDatas.MaterialManager_area.getCode());
		/*资料管理-----文件+1*/
		/*资料管理-----交底+1*/
		/*劳务管理-----记工管理*/
		urlList.put("/api/mechanicPrice/getMechanicPriceList", ProjectDatas.MechanicPrice_area.getCode());//劳务管理（记工管理）
		/*劳务管理-----人员花名册*/
		urlList.put("/api/mechanic/getMechanicList", ProjectDatas.MechanicPrice_area.getCode());//劳务管理(人员花名册)
		
	
		
		//urlList.put("/api/normativefiles/admin/getNormativefilesList", ProjectDatas.NormativeFile_area.getCode());//规范查阅
		
		
		
	};
	

	public static HashMap<String,Integer> getUrlList() {
		return urlList;
	}

	public static void setUrlList(HashMap<String,Integer> urlList) {
		ProjectUrlUtil.urlList = urlList;
	}
}

