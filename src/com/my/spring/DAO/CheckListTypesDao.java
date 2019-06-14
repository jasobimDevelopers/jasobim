package com.my.spring.DAO;

import com.my.spring.model.CheckListTypes;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface CheckListTypesDao {
	CheckListTypes getById(Long id);
	boolean deleteCheckListTypes(Long id);
	boolean addCheckListTypes(CheckListTypes role);
	DataWrapper<List<CheckListTypes>> getCheckListTypesListByType(Integer pageIndex, Integer pageSize, Integer type);
	boolean updateCheckListTypes(CheckListTypes newOne);
}
