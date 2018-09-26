package com.my.spring.service;

import java.util.List;

import com.my.spring.model.OutputValue;
import com.my.spring.model.OutputValuePojo;
import com.my.spring.utils.DataWrapper;
public interface OutputValueService {
	DataWrapper<Void> deleteOutputValueById(String token,Long id);
	DataWrapper<OutputValue> addOutputValue(String token,OutputValue role);
	DataWrapper<List<OutputValuePojo>> getOutputValueList(Integer pageIndex, Integer pageSize, OutputValue OutputValue,
			String token,String dateStart,String dateFinished);
	DataWrapper<OutputValue> getOutputValueById(String token, Long id);
	DataWrapper<Void> updateOutputValue(String token, OutputValue outputValue);
}
