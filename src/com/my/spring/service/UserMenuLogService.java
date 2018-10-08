package com.my.spring.service;

import java.util.List;

import com.my.spring.model.UserMenuLog;
import com.my.spring.utils.DataWrapper;
public interface UserMenuLogService {
	DataWrapper<Void> deleteUserMenuLogById(String token,Long id);
	DataWrapper<UserMenuLog> addUserMenuLog(String token,UserMenuLog role);
	DataWrapper<List<UserMenuLog>> getUserMenuLogList(String toke);
	DataWrapper<Void> updateUserMenuLog(String token, UserMenuLog UserMenuLog);
}
