package com.my.spring.DAO;

import com.my.spring.model.UserLog;
import com.my.spring.model.UserLogCount;
import com.my.spring.model.UserLogMonth;
import com.my.spring.model.UserLogPart;
import com.my.spring.model.UserLogPojos;
import com.my.spring.utils.DataWrapper;

import java.util.Date;
import java.util.List;


public interface UserLogDao {

	boolean addUserLog(UserLog userLog);
	
	boolean addUserLogList(List<UserLog> userLogList);

	boolean deleteUserLog(String[] ids);

	UserLog getById(Long id);

	String exportUserLog(String dateStart, String dateFinished);

	DataWrapper<List<UserLogPojos>> getUserLogLists(String startTime,String finishedTime,Long projectId,Integer projectPart,Integer systemType);

	boolean loadUserLogFile(String fileUrl);

	List<UserLogPart> getCountNumsByPart(String startTime, String finishedTime, String projectIdList);


	List<UserLogMonth> getCountNumsByMonth(String projectIdList, Integer year);

	List<UserLogMonth> getCountNumsByUserId(Long userId, Integer year);

	List<UserLogMonth> getCountRealNumsByMonth(String projectIdList, Integer year);

	List<UserLogMonth> getCountRealNumsByUserId(Long userId, Integer year);

	List<UserLogPart> getCountPersonNumsByPart(String startTime, String finishedTime, Long userId);

	List<UserLogCount> countUserLogNum(String dateStarts, String dateFinisheds, String projectIds, String userIds);


	DataWrapper<List<UserLog>> getUserLogList(Integer pageSize, Integer pageIndex, UserLog UserLog, Date startDate,
			Date finishedDate, String projectIds, String userIds, String userType);
}
