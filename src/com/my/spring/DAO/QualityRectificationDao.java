package com.my.spring.DAO;

import com.my.spring.model.QualityRectification;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface QualityRectificationDao {
	QualityRectification getById(Long id);
	boolean deleteQualityRectification(Long id);
	boolean addQualityRectification(QualityRectification role);
	boolean deleteQualityRectificationList(String[] ids);
	DataWrapper<List<QualityRectification>> getQualityRectificationList(Integer pageIndex, Integer pageSize, QualityRectification QualityManage);
	boolean updateQualityRectification(QualityRectification dp);
}
