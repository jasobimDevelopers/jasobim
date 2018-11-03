package com.my.spring.model;
import java.util.List;
/**
* @author 徐雨祥
* @version 创建时间：2018年10月31日 下午12:53:43
* 类说明
*/
public class ImageRecordPojo {
	private String buildingName;//楼栋信息id
	private List<ImageRecordData> contentList;
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public List<ImageRecordData> getContentList() {
		return contentList;
	}
	public void setContentList(List<ImageRecordData> contentList) {
		this.contentList = contentList;
	}
	
}
