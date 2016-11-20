package com.my.spring.DAO;

import com.my.spring.model.Quantity;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface QuantityDao {
	    boolean addQuantity(Quantity quantity);
	    boolean deleteQuantity(Long id);
	    boolean updateQuantity(Quantity quantity);
	    DataWrapper<List<Quantity>> getQuantityList(Long projectId,Integer pageSize, Integer pageIndex,Quantity quantity);
	    Quantity getById(Long id);
		boolean findQuantity(Quantity quantity);
		boolean addQuantityList(List<Quantity> quantityList);
}
