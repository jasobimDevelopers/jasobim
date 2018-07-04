package com.my.spring.DAO;

import com.my.spring.model.ProcessData;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface ProcessDataDao {
	boolean addProcessData(ProcessData ProcessData);
    boolean deleteProcessData(Long id);
    boolean updateProcessData(ProcessData ProcessData);
    ProcessData getById(Long id);
    DataWrapper<List<ProcessData>> getProcessDataList(Integer pageIndex, Integer pageSize, ProcessData ProcessData);
}
