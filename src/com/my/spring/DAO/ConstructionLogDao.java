package com.my.spring.DAO;

import java.util.List;

import com.my.spring.model.ConstructionLog;
import com.my.spring.model.ConstructionTask;
import com.my.spring.utils.DataWrapper;

public interface ConstructionLogDao {
	boolean addConstructionLog(ConstructionLog ps);
    boolean deleteConstructionLog(Long id);
    boolean updateConstructionLog(ConstructionLog ps);
    ConstructionLog getById(Long id);
    DataWrapper<List<ConstructionLog>> getConstructionTasksListByUserId(Long userId);
	DataWrapper<List<ConstructionLog>> getConstructionLogsList(Integer pageIndex, Integer pageSize,
			ConstructionLog constructionLog);
	boolean deleteConstructionLogList(String[] id);

}