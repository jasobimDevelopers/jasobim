package com.my.spring.DAO;
import com.my.spring.model.OutputValue;
import com.my.spring.utils.DataWrapper;

import java.util.Date;
import java.util.List;
public interface OutputValueDao {
	OutputValue getById(Long id);
	boolean deleteOutputValue(Long id);
	boolean addOutputValue(OutputValue role);
	boolean updateOutputValue(OutputValue dp);
	OutputValue getOutputValueLists(OutputValue role);
	List<OutputValue> getBeOutputValueLists(OutputValue role);
	DataWrapper<List<com.my.spring.model.OutputValue>> getOutputValueList(Integer pageIndex, Integer pageSize,
			OutputValue outputValue, Date start, Date end);
}
