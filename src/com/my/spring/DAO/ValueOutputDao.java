package com.my.spring.DAO;

import java.util.List;

import com.my.spring.model.ValueOutput;
import com.my.spring.utils.DataWrapper;

public interface ValueOutputDao {
	boolean addValueOutput(ValueOutput ValueOutput);
    boolean deleteValueOutput(Long id);
    boolean updateValueOutput(ValueOutput ValueOutput);
    DataWrapper<List<ValueOutput>> getValueOutputList(String projectList);
	DataWrapper<ValueOutput> getValueOutputByProjectId(Long projectId);
	boolean addValueOutputList(List<ValueOutput> ValueOutputList);
}
