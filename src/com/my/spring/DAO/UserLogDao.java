package com.my.spring.DAO;

import com.my.spring.model.UserLog;
import com.my.spring.utils.DataWrapper;

import java.util.List;


public interface UserLogDao {
	DataWrapper<List<UserLog>> getUserLogList(Integer pageSize, Integer pageIndex, UserLog userLog);

	boolean addUserLog(UserLog userLog);

	boolean deleteUserLog(String[] ids);

	UserLog getById(Long id);
}
