package com.my.spring.DAO;

import java.util.List;
import com.my.spring.model.ImageRecordBuildingInfo;
/**
* @author 徐雨祥
* @version 创建时间：2018年11月2日 上午8:28:40
* 类说明
*/
public interface ImageRecordBuildingInfoDao {
	boolean addImageRecordBuildingInfo(ImageRecordBuildingInfo info);
	boolean updateImageRecordBuildingInfo(ImageRecordBuildingInfo info);
	boolean deleteImageRecordBuildingInfo(Long id);
	ImageRecordBuildingInfo getById(Long id);
	List<ImageRecordBuildingInfo> getImageRecordBuildingInfoList(ImageRecordBuildingInfo info);
}
