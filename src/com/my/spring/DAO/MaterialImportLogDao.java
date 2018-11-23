package com.my.spring.DAO;

import com.my.spring.model.MaterialImportLog;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface MaterialImportLogDao {
    boolean addMaterialImportLog(MaterialImportLog MaterialImport);
    boolean deleteMaterialImportLog(Long id);
    boolean deleteMaterialImportLogByPorjectId(Long projectId);
    DataWrapper<List<MaterialImportLog>> getMaterialImportLogList(Long projectId, Integer pageSize, Integer pageIndex);
	
}
