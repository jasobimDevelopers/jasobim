package com.my.spring.service;

import java.util.List;

import com.my.spring.model.ImageRecord;
import com.my.spring.model.ImageRecordData;
import com.my.spring.model.ImageRecordPojo;
import com.my.spring.utils.DataWrapper;

/**
* @author 徐雨祥
* @version 创建时间：2018年10月31日 下午1:36:47
* 类说明
*/
public interface ImageRecordService {
	DataWrapper<Void> addImageRecord(String token,ImageRecord imageRecord);
	DataWrapper<ImageRecord> updateImageRecord(String token,ImageRecord imageRecord);
	DataWrapper<Void> deleteImageRecord(String token,Long id);
	DataWrapper<List<ImageRecordPojo>> getImageRecordList(String token,ImageRecord imageRecord);
	DataWrapper<List<ImageRecordData>> getImageRecordListByBuildingId(String token, ImageRecord imageRecord);
}
