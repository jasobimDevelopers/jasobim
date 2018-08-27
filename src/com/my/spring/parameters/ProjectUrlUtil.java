package com.my.spring.parameters;
import java.util.HashMap;
public class ProjectUrlUtil {
	private static HashMap<String,Integer> urlList = new HashMap<String,Integer>();
	public ProjectUrlUtil(String key,Integer value){
		urlList.put(key, value);
	}
	
	public ProjectUrlUtil(){
		//app接口
		urlList.put("/api/question/addQuestion",ProjectDatas.Question_area.getCode());///安全问题添加
		urlList.put("/api/question/admin/getQuestionList",ProjectDatas.Question_area.getCode());///安全问题查找
		urlList.put("/api/quality/addQuality",ProjectDatas.Quality_area.getCode());/////质量问题添加
		urlList.put("/api/quality/admin/getQualityList", ProjectDatas.Quality_area.getCode());///质量问题查找
		urlList.put("/api/constructionTaskNew/admin/addConstructionTaskNew", ProjectDatas.ConstructionTask_area.getCode());//施工任务单
		urlList.put("/api/constructionTaskNew/admin/getConstructionTaskNewList", ProjectDatas.ConstructionTask_area.getCode());//施工任务单
		urlList.put("/api/constructionTaskNew/getConstructionTaskNewDetail", ProjectDatas.ConstructionTask_area.getCode());//施工任务单
		urlList.put("/api/attenceLog/addAttenceLog", ProjectDatas.AttenceLog_area.getCode());///考勤打卡
		urlList.put("/api/attenceLog/getAttenceLogList", ProjectDatas.AttenceLog_area.getCode());//考勤打卡
		urlList.put("/api/folder/getFolderIndexList", ProjectDatas.FileManager_area.getCode());///文件云盘
		urlList.put("/api/item/admin/getItemList", ProjectDatas.Item_area.getCode());////模型构件信息
		urlList.put("/api/material/app/importMaterial", ProjectDatas.MaterialManager_area.getCode());//物资管理
		urlList.put("/api/material/app/getImportMaterialLog", ProjectDatas.MaterialManager_area.getCode());//物资管理
		urlList.put("/api/newsInfo/admin/addNewsInfo", ProjectDatas.NewsInfo_area.getCode());//新闻资讯
		urlList.put("/api/newsInfo/admin/getNewsInfoList", ProjectDatas.NewsInfo_area.getCode());//新闻资讯
		urlList.put("/api/normativefiles/getNormativefilesLists", ProjectDatas.NormativeFile_area.getCode());//规范查阅
		urlList.put("/api/notice/app/getAllNoticeList", ProjectDatas.Notification_area.getCode());//消息
		urlList.put("/api/paper/admin/getPaperLists", ProjectDatas.Paper_area.getCode());///图纸查看
		urlList.put("/api/project/admin/getProjectDetails", ProjectDatas.ProductionManager_projectDetail.getCode());///生产管理（项目详情）
		urlList.put("/api/project/admin/updateProject", ProjectDatas.ProductionManager_projectDetail.getCode());//生产管理（项目详情）
		urlList.put("/api/valueOutput/getValueOutputListnew", ProjectDatas.ValueOut_area.getCode());//项目产值获取
		urlList.put("/api/video/admin/addVideo", ProjectDatas.Video_area.getCode());//交底新增
		urlList.put("/api/video/admin/getVideoList", ProjectDatas.Video_area.getCode());//交底列表获取
		
		/////web接口
		urlList.put("/api/normativefiles/admin/getNormativefilesList", ProjectDatas.NormativeFile_area.getCode());//规范查阅
		urlList.put("/api/material/vs/getMaterialList", ProjectDatas.MaterialManager_area.getCode());//物资管理
		urlList.put("/api/mechanic/getMechanicList", ProjectDatas.MechanicPrice_area.getCode());//劳务管理(人员花名册)
		urlList.put("/api/mechanicPrice/getMechanicPriceList", ProjectDatas.MechanicPrice_area.getCode());//劳务管理（记工管理）
	};
	

	public static HashMap<String,Integer> getUrlList() {
		return urlList;
	}

	public static void setUrlList(HashMap<String,Integer> urlList) {
		ProjectUrlUtil.urlList = urlList;
	}
}

