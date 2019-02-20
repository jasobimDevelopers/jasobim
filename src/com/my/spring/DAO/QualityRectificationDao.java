package com.my.spring.DAO;

import com.my.spring.model.QualityRectification;
import com.my.spring.model.User;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface QualityRectificationDao {
	QualityRectification getById(Long id);
	boolean deleteQualityRectification(Long id);
	boolean addQualityRectification(QualityRectification role);
	boolean deleteQualityRectificationList(String[] ids);
	DataWrapper<List<QualityRectification>> getQualityRectificationList(Integer pageIndex, Integer pageSize, QualityRectification QualityManage,String ids);
	boolean updateQualityRectification(QualityRectification dp);
	List<QualityRectification> getQualityRectificationList(QualityRectification qualityManage);
	DataWrapper<List<QualityRectification>> getQualityRectificationList(Integer pageIndex, Integer pageSize,
			QualityRectification qualityManage,String ids, String start, String end, List<User> userss);
	DataWrapper<List<QualityRectification>> getQualityRectificationLists(Integer pageIndex, Integer pageSize,
			QualityRectification qualityManage,String ids, String start, String end, String find);
}
