package com.my.spring.DAO;

import com.my.spring.model.ProcessLog;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface ProcessLogDao {
	boolean addProcessLog(ProcessLog ProcessLog);
    boolean deleteProcessLog(Long id);
    ProcessLog getById(Long id);
    DataWrapper<List<ProcessLog>> getProcessLogList(Integer pageIndex, Integer pageSize, ProcessLog ProcessLog);
}
