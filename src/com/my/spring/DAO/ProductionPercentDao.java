package com.my.spring.DAO;

import com.my.spring.model.ProductionPercent;
import com.my.spring.utils.DataWrapper;

import java.util.List;


public interface ProductionPercentDao {
	DataWrapper<List<ProductionPercent>> getProductionPercentList(Integer pageSize, Integer pageIndex, ProductionPercent ProductionPercent);

	boolean addProductionPercent(ProductionPercent ProductionPercent);

	boolean deleteProductionPercent(String[] ids);

	ProductionPercent getById(Long id);
}
