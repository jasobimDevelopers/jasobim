package com.my.spring.DAO;

import java.util.List;

import com.my.spring.model.ImageRecord;

/**
* @author 徐雨祥
* @version 创建时间：2018年10月31日 下午1:17:17
* 类说明
*/
public interface ImageRecordDao {
	boolean addImageRecord(ImageRecord imageRecord);
	boolean deleteImageRecordById(Long id);
	boolean updateImageRecord(ImageRecord imageRecord);
	ImageRecord getImageRecordById(Long id);
	List<ImageRecord> getImageRecordList(ImageRecord imageRecord);
}
