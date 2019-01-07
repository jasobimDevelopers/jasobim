package com.my.spring.DAO;

import com.my.spring.model.QualityCheck;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface QualityCheckDao {
	QualityCheck getById(Long id);
	boolean deleteQualityCheck(Long id);
	boolean addQualityCheck(QualityCheck role);
	boolean deleteQualityCheckList(String[] ids);
	DataWrapper<List<QualityCheck>> getQualityCheckList(Integer pageIndex, Integer pageSize, QualityCheck QualityManage);
	boolean updateQualityCheck(QualityCheck dp);
}
