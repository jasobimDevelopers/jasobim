package com.my.spring.DAO;

import com.my.spring.model.MeasuredDatas;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface MeasuredDatasDao {

	boolean addMeasuredDatasList(List<MeasuredDatas> itemList);


	MeasuredDatas getMeasuredDatasBySelfId(Long selfId);

	MeasuredDatas getMeasuredDatasById(Long id);
	

	DataWrapper<List<MeasuredDatas>> getMeasuredDatasList(Integer pageSize, Integer pageIndex,
			MeasuredDatas item);


	boolean addMeasuredDatas(MeasuredDatas measuredData);
	
	boolean deleteMeasuredDatasByMeasuredDataId(Long measuredDataId);
}
