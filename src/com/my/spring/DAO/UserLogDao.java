package com.my.spring.DAO;

import com.my.spring.model.UserLog;
import com.my.spring.model.UserLogMonth;
import com.my.spring.model.UserLogPart;
import com.my.spring.model.UserLogPojos;
import com.my.spring.utils.DataWrapper;

import java.util.Date;
import java.util.List;


public interface UserLogDao {
	DataWrapper<List<UserLog>> getUserLogList(Integer pageSize, Integer pageIndex, UserLog userLog,Date s,Date f,String projectIds,String userIds);

	boolean addUserLog(UserLog userLog);
	
	boolean addUserLogList(List<UserLog> userLogList);

	boolean deleteUserLog(String[] ids);

	UserLog getById(Long id);

	String exportUserLog(String dateStart, String dateFinished);

	DataWrapper<List<UserLogPojos>> getUserLogLists(String startTime,String finishedTime,Long projectId,Integer projectPart,Integer systemType);

	boolean loadUserLogFile(String fileUrl);

	List<UserLogPart> getCountNumsByPart(String startTime, String finishedTime, String projectIdList);

	List<UserLogMonth> getCountNumsByMonth(String projectIdList);
}
