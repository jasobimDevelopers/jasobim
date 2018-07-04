package com.my.spring.service;

import java.util.List;

import com.my.spring.model.MaterialImportLog;
import com.my.spring.model.MaterialImportLogPojo;
import com.my.spring.utils.DataWrapper;

public interface MaterialImportLogService {
	 DataWrapper<Void> addMaterialImportLog(MaterialImportLog duct, String token);
	 DataWrapper<Void> deleteMaterialImportLog(Long id,String token);
	 DataWrapper<List<MaterialImportLogPojo>> getMaterialImportLogList(String token, MaterialImportLog duct, Integer pageSize,
			Integer pageIndex);
}
