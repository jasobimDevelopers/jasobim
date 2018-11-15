package com.my.spring.service;
import com.my.spring.model.ProcessLog;
import com.my.spring.model.ProcessLogPojo;
import com.my.spring.utils.DataWrapper;
import java.util.List;
public interface ProcessLogService {
    DataWrapper<List<ProcessLogPojo>> getProcessLogList(String token, Integer pageIndex, Integer pageSize, ProcessLog ProcessLog);
	DataWrapper<Void> addProcessLog(ProcessLog ProcessLog, String token);
	DataWrapper<Void> deleteProcessLog(Long id, String token);
}
