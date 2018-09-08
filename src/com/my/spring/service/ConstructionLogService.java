package com.my.spring.service;

import com.my.spring.model.ConstructionLog;
import com.my.spring.model.ConstructionLogPojo;
import com.my.spring.utils.DataWrapper;
import java.util.List;


public interface ConstructionLogService {
    DataWrapper<List<ConstructionLog>> getConstructionLogListByUserId(Long userId,String token);
	DataWrapper<ConstructionLog> addConstructionLog(ConstructionLog constructionLog, String token,String constructDates,String cityNode);
	//DataWrapper<Void> deleteConstructionLog(String id, String token);
	DataWrapper<List<ConstructionLogPojo>> getConstructionLogList(String token, Integer pageIndex, Integer pageSize,
			ConstructionLog ps,String start,String end,String userRealName);
	DataWrapper<Void> deleteConstructionLog(Long id, String token);
	DataWrapper<String> exportConstructionLog(String token, Long id);
}
