package com.my.spring.service;

import java.util.List;

import com.my.spring.model.CheckListTypes;
import com.my.spring.utils.DataWrapper;

public interface CheckListTypesService {
	DataWrapper<Void> deleteCheckListTypesById(String token,Long id);
	DataWrapper<CheckListTypes> addCheckListTypes(String token,CheckListTypes role);
	DataWrapper<List<CheckListTypes>> getCheckListTypesList(Integer pageIndex, Integer pageSize, CheckListTypes CheckLists,
			String token);
}
