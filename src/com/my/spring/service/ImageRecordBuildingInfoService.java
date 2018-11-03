package com.my.spring.service;

import java.util.List;

import com.my.spring.model.ImageRecordBuildingInfo;
import com.my.spring.utils.DataWrapper;

/**
* @author 徐雨祥
* @version 创建时间：2018年11月2日 上午8:34:02
* 类说明
*/
public interface ImageRecordBuildingInfoService {
	DataWrapper<ImageRecordBuildingInfo> addImageRecordBuildingInfo(String token,ImageRecordBuildingInfo info);
	DataWrapper<Void> updateImageRecordBuildingInfo(String token,ImageRecordBuildingInfo info);
	DataWrapper<Void> deleteImageRecordBuildingInfo(String token,Long id);
	DataWrapper<ImageRecordBuildingInfo> getImageRecordBuildingInfoById(String token,Long id);
	DataWrapper<List<ImageRecordBuildingInfo>> getImageRecordBuildingInfoList(String token,
			ImageRecordBuildingInfo info);
}
