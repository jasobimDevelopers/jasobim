package com.my.spring.service;

import java.util.List;
import com.my.spring.model.CheckLists;
import com.my.spring.utils.DataWrapper;
public interface CheckListsService {
	DataWrapper<Void> deleteCheckListsById(String token,Long id);
	DataWrapper<CheckLists> addCheckLists(String token,CheckLists role);
	DataWrapper<Void> updateCheckLists(String token, CheckLists CheckLists);
	DataWrapper<List<CheckLists>> getCheckListsList(Integer pageIndex, Integer pageSize, CheckLists CheckLists,
			String token);
}
