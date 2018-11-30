package com.my.spring.DAO;

import com.my.spring.model.ProcessData;
import com.my.spring.model.ProcessDataIndex;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface ProcessDataDao {
	boolean addProcessData(ProcessData ProcessData);
    boolean deleteProcessData(Long id);
    boolean updateProcessData(ProcessData ProcessData);
    ProcessData getById(Long id);
    DataWrapper<List<ProcessData>> getProcessDataList(Integer pageIndex, Integer pageSize, ProcessData ProcessData);
	ProcessData getProcessDataByProjectId(Long projectId, Integer i);
	boolean deleteProcessDataByUserId(Long id);
	boolean addProcessDataList(List<ProcessData> newList2);
	List<ProcessDataIndex> getProcessDataListByUserId(Long id,Long typeId, Integer pageSize, Integer pageIndex);
	Integer getProcessDataSizeByUserId(Long id);
}
