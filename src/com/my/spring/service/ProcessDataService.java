package com.my.spring.service;

import com.my.spring.model.ProcessData;
import com.my.spring.model.ProcessDataPojo;
import com.my.spring.model.ProcessItem;
import com.my.spring.model.ProcessItemPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;



public interface ProcessDataService {
	DataWrapper<Void> deleteProcessData(Long id, String token);
	DataWrapper<Void> updateProcessData(ProcessData ProcessData, String token);
	DataWrapper<List<ProcessDataPojo>> getProcessDataList(String token, Integer pageIndex, Integer pageSize,
			ProcessData ProcessData, Integer type);
	DataWrapper<ProcessData> addProcessData(ProcessData ProcessData, String token);
	DataWrapper<Void> addProcessItem(ProcessItem processItem, String token);
	DataWrapper<List<ProcessItemPojo>> getProcessItemListById(String token, Long id);
}
