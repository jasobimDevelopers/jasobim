package com.my.spring.service;

import java.util.List;

import com.my.spring.model.MaterialLog;
import com.my.spring.model.MaterialLogPojo;
import com.my.spring.utils.DataWrapper;

public interface MaterialLogService {
	DataWrapper<Void> addMaterialLog(MaterialLog m, String token);
	DataWrapper<Void> deleteMaterialLog(Long id, String token);
	DataWrapper<Void> updateMaterialLog(MaterialLog m, String token);
	DataWrapper<List<MaterialLogPojo>> getMaterialLogList(String token, Integer pageIndex, Integer pageSize,
			MaterialLog m);
}
