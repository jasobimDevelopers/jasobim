package com.my.spring.service;

import com.my.spring.model.ConstructionTaskNew;
import com.my.spring.model.ConstructionTaskNewPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;



public interface ConstructionTaskNewService {
	DataWrapper<Void> addConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew, String token);
	DataWrapper<Void> deleteConstructionTaskNew(Long id, String token);
	DataWrapper<Void> updateConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew, String token);
	DataWrapper<List<ConstructionTaskNewPojo>> getConstructionTaskNewList(String token, Integer pageIndex, Integer pageSize,
			ConstructionTaskNew ConstructionTaskNew, Integer type);
}
