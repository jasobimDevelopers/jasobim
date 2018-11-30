package com.my.spring.service;
import java.util.List;
import com.my.spring.model.ProcessDataTypePojo;
import com.my.spring.utils.DataWrapper;
public interface ProcessDataTypeService {
	
	DataWrapper<Void> addProcessDataType(String name,String token);

	DataWrapper<Void> deleteProcessDataType(Long id, String token);


	DataWrapper<List<ProcessDataTypePojo>> getProcessDataTypeList(String token);
}
