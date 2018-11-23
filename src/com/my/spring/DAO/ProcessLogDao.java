package com.my.spring.DAO;

import com.my.spring.model.ProcessLog;
import com.my.spring.model.ProcessLogSql;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface ProcessLogDao {
	boolean addProcessLog(ProcessLog ProcessLog);
    boolean deleteProcessLog(Long id);
    ProcessLog getById(Long id);
    DataWrapper<List<ProcessLog>> getProcessLogList(Integer pageIndex, Integer pageSize, ProcessLog ProcessLog);
	ProcessLog getProcessLogByItemDataId(Long id, Long id2);
	List<ProcessLog> getProcessLogByAboutId(Long id, Long id2);
	List<ProcessLogSql> getProcessLogByStatus(Integer status);
	List<ProcessLog> getProcessLogByAboutIds(Long id, Long processDataId);
	List<ProcessLog> getProcessLogListByInfos(Long id, Long id2);
	ProcessLog getProcessLogListByInfo(Long aboutId, Long id);
	boolean deleteProcessLogByAbout(Long id, Integer type);
}
