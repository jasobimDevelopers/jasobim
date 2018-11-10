package com.my.spring.service;

import com.my.spring.model.ItemStateLog;
import com.my.spring.model.ItemStateLogPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface ItemStateLogService {
    DataWrapper<ItemStateLog> addItemStateLog(ItemStateLog ItemStateLog, String token,String selfIdList);
    DataWrapper<Void> deleteItemStateLog(Long id,String token);
    DataWrapper<Void> updateItemStateLog(ItemStateLog ItemStateLog,String token);
    DataWrapper<List<ItemStateLog>> getItemStateLogList();
	DataWrapper<ItemStateLog> getItemStateLogByProjectId(Long projectId,String token);
	DataWrapper<List<ItemStateLogPojo>> getItemStateLogList(String token, Long projectId, String idList);
	DataWrapper<List<ItemStateLogPojo>> getAllItemStateLogList(String token, Long projectId);
}
