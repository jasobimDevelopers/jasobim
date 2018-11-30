package com.my.spring.DAO;

import com.my.spring.model.ProcessDataType;
import java.util.List;

public interface ProcessDataTypeDao {

	boolean addProcessDataType(ProcessDataType ProcessDataType);

	boolean deleteProcessDataType(Long ids);

	ProcessDataType getById(Long id);

	List<ProcessDataType> getProcessDataTypeList();
}
