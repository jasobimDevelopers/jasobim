package com.my.spring.DAO;

import com.my.spring.model.MeasuredData;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface MeasuredDataDao {

	boolean addMeasuredDataList(List<MeasuredData> itemList);
	
	boolean addMeasuredData(MeasuredData measuredData);
	
	boolean deleteMeasuredData(Long id);
	
	DataWrapper<List<MeasuredData>> getMeasuredDataList(Integer pageSize, Integer pageIndex, MeasuredData item);

	MeasuredData getMeasuredDataBySelfId(Long selfId);

	MeasuredData getMeasuredDataById(Long id);
}
