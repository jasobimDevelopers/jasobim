package com.my.spring.service;

import com.my.spring.model.ValueOutput;
import com.my.spring.model.ValueOutputPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;


/**
 * Created by Administrator on 2016/6/22.
 */
public interface ValueOutputService {
    DataWrapper<Void> addValueOutput(ValueOutput ValueOutput, String token);
    DataWrapper<Void> deleteValueOutput(String id,String token);
    DataWrapper<Void> updateValueOutput(ValueOutput ValueOutput,String token);
    DataWrapper<List<ValueOutputPojo>> getValueOutputList(String token);
	DataWrapper<List<ValueOutputPojo>> getValueOutputByProjectId(String projectName,Long projectId,String token);
	DataWrapper<List<ValueOutputPojo>> getValueOutputLists(Integer pageIndex, Integer pageSize, ValueOutput valueOutput,
			String token);
}
