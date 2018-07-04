package com.my.spring.service;

import com.my.spring.model.WorkType;
import com.my.spring.utils.DataWrapper;

import java.util.List;



public interface WorkTypeService {
	DataWrapper<Void> addWorkType(WorkType WorkType, String token);
	DataWrapper<Void> deleteWorkType(Long id, String token);
	DataWrapper<Void> updateWorkType(WorkType WorkType, String token);
	DataWrapper<List<WorkType>> getWorkTypeList(String token, Integer pageIndex, Integer pageSize,
			WorkType WorkType, Integer type);
}
