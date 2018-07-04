package com.my.spring.service;

import com.my.spring.model.ProcessItem;
import com.my.spring.utils.DataWrapper;

import java.util.List;



public interface ProcessItemService {
    DataWrapper<List<ProcessItem>> getProcessItemList(String token, Integer pageIndex, Integer pageSize, ProcessItem ProcessItem);
	DataWrapper<Void> addProcessItem(ProcessItem ProcessItem, String token);
	DataWrapper<Void> deleteProcessItem(Long id, String token);
}
