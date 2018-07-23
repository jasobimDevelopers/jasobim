package com.my.spring.DAO;

import com.my.spring.model.AllItemData;
import com.my.spring.model.ConstructionTaskNew;
import com.my.spring.model.ConstructionTaskNewUser;
import com.my.spring.model.ItemNodeList;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface ConstructionTaskNewDao {
	boolean addConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew);
    boolean deleteConstructionTaskNew(Long id);
    boolean updateConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew);
    ConstructionTaskNew getById(Long id);
    DataWrapper<List<ConstructionTaskNew>> getConstructionTaskNewList(Integer pageIndex, Integer pageSize, ConstructionTaskNew ConstructionTaskNew);
	List<ConstructionTaskNewUser> getAboutUsers(Long projectId);
	List<AllItemData> getAllItemData(Long id);
	List<ItemNodeList> getAllItemLog(Long id);
	List<ConstructionTaskNew> getConstructionTaskNewByIds(Long id);
}
