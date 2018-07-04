package com.my.spring.DAO;

import com.my.spring.model.ProcessItem;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface ProcessItemDao {
	boolean addProcessItem(ProcessItem ProcessItem);
    boolean deleteProcessItem(Long id);
    ProcessItem getById(Long id);
    DataWrapper<List<ProcessItem>> getProcessItemList(Integer pageIndex, Integer pageSize, ProcessItem ProcessItem);
}
