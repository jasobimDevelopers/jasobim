package com.my.spring.service;


import java.util.List;



import com.my.spring.model.ProductionPercent;
import com.my.spring.model.ProductionPercentPojo;
import com.my.spring.utils.DataWrapper;


public interface ProductionPercentService {
	
	DataWrapper<Void> addProductionPercent(String content, String token,String tel);

	DataWrapper<Void> deleteProductionPercentPercent(String ids, String token);

	DataWrapper<List<ProductionPercentPojo>> getProductionPercentList(Integer pageIndex, Integer pageSize, ProductionPercent productionPercent, String token);
}
