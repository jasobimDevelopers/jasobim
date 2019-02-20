package com.my.spring.DAO;

import com.my.spring.model.AwardTicket;
import com.my.spring.model.QualityCheck;
import com.my.spring.model.User;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface QualityCheckDao {
	QualityCheck getById(Long id);
	boolean deleteQualityCheck(Long id);
	boolean addQualityCheck(QualityCheck role);
	boolean deleteQualityCheckList(String[] ids);
	DataWrapper<List<QualityCheck>> getQualityCheckList(Integer pageIndex, Integer pageSize, QualityCheck QualityManage,String start,String end,List<User> users);
	DataWrapper<List<QualityCheck>> getQualityCheckLists(Integer pageIndex, Integer pageSize, QualityCheck QualityManage,String start,String end,String find);
	boolean updateQualityCheck(QualityCheck dp);
	List<QualityCheck> getQualityCheckLists(AwardTicket awardTicket,String find);
	DataWrapper<List<QualityCheck>> getQualityCheckList(Integer pageIndex, Integer pageSize, QualityCheck QualityManage);

}
