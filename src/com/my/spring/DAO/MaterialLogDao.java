package com.my.spring.DAO;

import com.my.spring.model.MaterialLog;
import com.my.spring.utils.DataWrapper;

import java.util.List;


public interface MaterialLogDao {
	boolean addMaterialLog(MaterialLog ml);
    boolean deleteMaterialLog(Long id);
    boolean updateMaterialLog(MaterialLog ml);
    MaterialLog getById(Long id);
	DataWrapper<List<MaterialLog>> getMaterialLogList(Integer pageIndex, Integer pageSize, MaterialLog m);
}
