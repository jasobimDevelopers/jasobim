package com.my.spring.DAO;

import java.util.List;

import com.my.spring.model.News;
import com.my.spring.model.ValueOutput;
import com.my.spring.model.ValueOutputPojo;
import com.my.spring.utils.DataWrapper;

public interface ValueOutputDao {
	boolean addValueOutput(ValueOutput ValueOutput);
    boolean deleteValueOutput(Long id);
    boolean deleteValueOutputs(String id);
    boolean updateValueOutput(ValueOutput ValueOutput);
    DataWrapper<List<ValueOutputPojo>> getValueOutputList(String projectList);
	DataWrapper<List<ValueOutput>> getValueOutputByProjectId(Long projectId,String projectName, String[] projectLists);
	boolean addValueOutputList(List<ValueOutput> ValueOutputList);
	DataWrapper<List<ValueOutput>> getValueOutputListByProjectName(String projectName);
	DataWrapper<List<ValueOutput>> getValueOutputLists(Integer pageIndex, Integer pageSize, ValueOutput valueOutput,String dates);
	ValueOutput getById(Long fileId);
}
