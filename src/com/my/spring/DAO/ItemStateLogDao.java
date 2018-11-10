package com.my.spring.DAO;

import com.my.spring.model.Item;
import com.my.spring.model.ItemStateLog;
import com.my.spring.utils.DataWrapper;

import java.util.List;
public interface ItemStateLogDao {
    boolean addItemStateLog(ItemStateLog ItemStateLog);
    boolean deleteItemStateLog(Long id);
    boolean updateItemStateLog(ItemStateLog ItemStateLog);
    DataWrapper<List<ItemStateLog>> getItemStateLogList();
	DataWrapper<ItemStateLog> getItemStateLogByProjectId(Long projectId);
	List<ItemStateLog> getAllItemStateLogByProjectId(Long projectId);
	boolean deleteItemStateLogByProjectId(Long id);
	ItemStateLog getItemStateLogByProjectIdAndSelfId(Long selfId, Long projectId);
	List<ItemStateLog> getItemStateLogList(Long projectId, String idList);
	boolean addList(List<ItemStateLog> gets);
	List<ItemStateLog> getAllItemStateLogGroupBySelfId(Long projectId);
	boolean deleteItemStateLogByProjectIdAndSelfIds(Long projectId, String selfIdList);
}
