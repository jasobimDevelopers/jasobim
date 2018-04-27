package com.my.spring.service;

import com.my.spring.model.ValueOutput;
import com.my.spring.model.ValueOutputPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;
public interface ValueOutputService {
    DataWrapper<Void> addValueOutput(ValueOutput ValueOutput, String token);
    DataWrapper<Void> deleteValueOutput(String id,String token);
    DataWrapper<Void> updateValueOutput(ValueOutput ValueOutput,String token);
    DataWrapper<List<ValueOutputPojo>> getValueOutputList(String token);
    DataWrapper<ValueOutputPojo> getValueOutputListnew(String token,Long projectId);
	DataWrapper<List<ValueOutputPojo>> getValueOutputByProjectId(String projectName,Long projectId,String token);
	DataWrapper<List<ValueOutputPojo>> getValueOutputLists(Integer pageIndex, Integer pageSize, ValueOutput valueOutput,
			String token,String dates);
	DataWrapper<List<ValueOutputPojo>> getValueOutputByProjectName(String projectName, Long projectId, String token);
	DataWrapper<ValueOutputPojo> getValueOutputByDate(Integer month, Integer year, Long projectId, String token);
	DataWrapper<String> exportValueOutput(Long projectId, String token);
}
