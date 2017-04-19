package com.my.spring.DAO;

import java.util.List;

import com.my.spring.model.DuctLog;
import com.my.spring.utils.DataWrapper;

public interface DuctLogDao {
	boolean addDuctLog(DuctLog DuctLog);
    boolean deleteDuctLog(Long id);
	DataWrapper<List<DuctLog>> getDuctLogByDuctId(Long id);
}
