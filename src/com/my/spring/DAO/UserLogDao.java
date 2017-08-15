package com.my.spring.DAO;

import com.my.spring.model.UserLog;
import com.my.spring.model.UserLogPojos;
import com.my.spring.utils.DataWrapper;

import java.util.Date;
import java.util.List;


public interface UserLogDao {
	DataWrapper<List<UserLog>> getUserLogList(Integer pageSize, Integer pageIndex, UserLog userLog,Date s,Date f);

	boolean addUserLog(UserLog userLog);
	
	boolean addUserLogList(List<UserLog> userLogList);

	boolean deleteUserLog(String[] ids);

	UserLog getById(Long id);

	boolean exportUserLog(String tempFile, String dateStart, String dateFinished);

	DataWrapper<List<UserLogPojos>> getUserLogLists(String startTime,String finishedTime,Long projectId,Integer projectPart,Integer systemType);

	boolean loadUserLogFile(String fileUrl);
}
