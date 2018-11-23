package com.my.spring.DAO;

import com.my.spring.model.WorkType;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface WorkTypeDao {
	boolean addWorkType(WorkType WorkType);
    boolean deleteWorkType(Long id);
    boolean updateWorkType(WorkType WorkType);
    WorkType getById(Long id);
    DataWrapper<List<WorkType>> getWorkTypeList(Integer pageIndex, Integer pageSize, WorkType WorkType);
}
