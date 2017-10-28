package com.my.spring.DAO;

import com.my.spring.model.Quantity;
import com.my.spring.utils.DataWrapper;


import java.util.List;

public interface QuantityDao {
	    boolean addQuantity(Quantity quantity);
	    boolean deleteQuantity(Long id);
	    boolean updateQuantity(Quantity quantity);
	    DataWrapper<List<Quantity>> getQuantityList(Long projectId,Integer pageSize, Integer pageIndex,Quantity quantity);
	    Quantity getById(Long id);
		//boolean findQuantity(Quantity quantity);
		boolean addQuantityList(List<Quantity> quantityList);
		boolean findSameQuantityAndDo(List<Quantity> quantityList);
		List<Quantity> getAllQuantity();
		
		String exportQuantity(Long projectId);
		boolean deleteQuantityByProjectId(Long id);
		DataWrapper<List<Quantity>> getQuantityListNums(Long projectId, Integer pageSize, Integer pageIndex,
				Quantity quantity);
		
		DataWrapper<List<Quantity>> testGroupBy(int pageSize,int pageIndex);
		DataWrapper<List<Quantity>> getQuantityListNum(Long projectId, Integer pageSize, Integer pageIndex,
				Quantity quantity);
}
