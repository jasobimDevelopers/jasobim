package com.my.spring.DAO;

import com.my.spring.model.ConstructionTask;
import com.my.spring.model.ConstructionTaskCopy;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface ConstructionTaskDao {
	boolean addConstructionTask(ConstructionTask ps);
    boolean deleteConstructionTask(Long id);
    boolean updateConstructionTask(ConstructionTask ps);
    ConstructionTask getById(Long id);
    DataWrapper<List<ConstructionTask>> getConstructionTasksList(Integer pageIndex, Integer pageSize, ConstructionTask constructionTask, Integer state,String userName,String NextReceivePeopleId);
    DataWrapper<List<ConstructionTask>> getConstructionTasksListByUserId(Long userId);
	List<ConstructionTaskCopy> getAdvancedOrdersListNotRead(Long id, Integer pageSize, Integer pageIndex);
}
