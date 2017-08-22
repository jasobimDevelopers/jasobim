package com.my.spring.service;


import java.util.List;



import com.my.spring.model.Production;
import com.my.spring.model.ProductionPojo;
import com.my.spring.utils.DataWrapper;


public interface ProductionService {
	
	DataWrapper<Void> addProduction(String content, String token,String tel);

	DataWrapper<Void> deleteProduction(String ids, String token);

	DataWrapper<List<ProductionPojo>> getProductionList(Integer pageIndex, Integer pageSize, Production Production, String token);
}
