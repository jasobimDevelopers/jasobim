package com.my.spring.service;

import java.util.List;

import com.my.spring.model.UserIndex;
import com.my.spring.utils.DataWrapper;
public interface UserIndexService {
	DataWrapper<UserIndex> addUserIndex(String token,UserIndex role);
	DataWrapper<Void> deleteUserIndexByIdList(String token,String[] id);
	DataWrapper<List<UserIndex>> getUserIndexList(Integer pageIndex, Integer pageSize, UserIndex UserIndex,
			String token);
	DataWrapper<Void> addUserIndexList(String newList, String token);
	DataWrapper<Void> deleteUserIndexByAboutId(Integer type, Long id);
}
