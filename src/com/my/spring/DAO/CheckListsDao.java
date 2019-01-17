package com.my.spring.DAO;

import com.my.spring.model.CheckLists;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface CheckListsDao {
	CheckLists getById(Long id);
	boolean deleteCheckLists(Long id);
	boolean addCheckLists(CheckLists role);
	boolean updateCheckLists(CheckLists dp);
	DataWrapper<List<CheckLists>> getCheckListsListByProjectId(Integer pageIndex, Integer pageSize, Long projectId,
			Integer type, Long pid);
}
