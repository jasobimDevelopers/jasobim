package com.my.spring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.my.spring.model.UserLog;
import com.my.spring.model.UserLogPojo;
import com.my.spring.model.UserLogPojos;
import com.my.spring.utils.DataWrapper;

public interface UserLogService {

	DataWrapper<Void> deleteUserLog(String ids, String token);

	DataWrapper<List<UserLogPojo>> getUserLogList(Integer pageIndex, Integer pageSize, UserLog userLog, String token,String s,String f,String searchContent);

	DataWrapper<Void> addUserLog(UserLog userLog, String token);

	DataWrapper<String> exportUserLog(String token, HttpServletRequest request, String dateStart, String dateFinished);

	DataWrapper<List<UserLogPojos>> getUserLogCountSum(String token,String startTime,String finishedTime,Long projectId,Integer projectPart,Integer systemType);

	DataWrapper<String> writeUserLogInFile(UserLog userLog);

	DataWrapper<List<UserLog>> readUserLogFromFile();


}
