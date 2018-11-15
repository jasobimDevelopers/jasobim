package com.my.spring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.my.spring.model.UserLog;
import com.my.spring.model.UserLogMonth;
import com.my.spring.model.UserLogPart;
import com.my.spring.model.UserLogPojo;
import com.my.spring.model.UserLogPojos;
import com.my.spring.utils.DataWrapper;

public interface UserLogService {

	DataWrapper<Void> deleteUserLog(String ids, String token);
	DataWrapper<Void> addUserLog(UserLog userLog, String token);
	DataWrapper<String> exportUserLog(String token, HttpServletRequest request, String dateStart, String dateFinished);
	DataWrapper<List<UserLogPojos>> getUserLogCountSum(String token,String startTime,String finishedTime,Long projectId,Integer projectPart,Integer systemType);
	DataWrapper<String> writeUserLogInFile(UserLog userLog);
	DataWrapper<List<UserLog>> readUserLogFromFile();
	DataWrapper<List<UserLogPart>> countUserLogByPart(String token, String startTime, String finishedTime,
			String projectIdList);
	DataWrapper<List<UserLogMonth>> countUserLogByMonth(String token, String projectIdList, Integer year);
	DataWrapper<List<UserLogMonth>> countUserLogByUserId(String token, Long userId, Integer year);
	DataWrapper<List<UserLogPart>> countPersonLogByPart(String token, String startTime, String finishedTime,
			Long userId);
	DataWrapper<String> exportUserLogList(String token,
			String dateStart, String dateFinished, String searchContent, String projectIds);
	DataWrapper<String> exportPersonLogList(String token, Long userId, Integer year, String startTime, String finishedTime);
	DataWrapper<String> exportUserLogEcharts(String token, String dateStart, String dateFinished, String projectIds,
			String userIds, Integer year);
	DataWrapper<List<UserLogPojo>> getUserLogList(Integer pageIndex, Integer pageSize, UserLog UserLog, String token,
			String startDate, String finishedDate, String searchContent, String projectIds, String userIds,
			String userType);
}
