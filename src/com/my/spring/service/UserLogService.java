package com.my.spring.service;

import java.util.List;

import com.my.spring.model.UserLog;
import com.my.spring.model.UserLogPojo;
import com.my.spring.utils.DataWrapper;

public interface UserLogService {

	DataWrapper<Void> deleteUserLog(String ids, String token);

	DataWrapper<List<UserLogPojo>> getUserLogList(Integer pageIndex, Integer pageSize, UserLog userLog, String token);

	DataWrapper<Void> addUserLog(UserLog userLog, String token);


}
