package com.my.spring.DAO;

import java.util.Date;
import java.util.List;

import com.my.spring.model.AttenceLog;
import com.my.spring.model.AttenceLogs;
import com.my.spring.utils.DataWrapper;

public interface AttenceLogDao {
	 boolean addAttenceLog(AttenceLog am);
     boolean deleteAttenceLog(Long id);
     boolean updateAttenceLog(AttenceLog am);
	 DataWrapper<Void> deleteAttenceLogByProjectId(Long id);
	 AttenceLog getAttenceLogById(Long id);
	 DataWrapper<List<AttenceLog>> getAttenceLogList(Integer pageIndex, Integer pageSize, AttenceLog am);
	DataWrapper<List<AttenceLogs>> getAttenceLogsList(Integer pageIndex, Integer pageSize, AttenceLog am, Integer year,
			Integer month);
	DataWrapper<List<AttenceLogs>> getAttenceLogsUserList(Integer pageIndex, Integer pageSize, AttenceLog am,
			Integer year, Integer month);
	AttenceLog getAttenceLogByInfos(Long id, Date nowDate, Long projectId);
}
