package com.my.spring.service;

import java.util.List;

import com.my.spring.model.ProductionRecords;
import com.my.spring.utils.DataWrapper;

/**
* @author 徐雨祥
* @version 创建时间：2018年8月31日 下午1:51:31
* 类说明
*/
public interface ProductionRecordsService {
	DataWrapper<Void> deleteProductionRecordsById(Long id,String token);
	DataWrapper<ProductionRecords> addProductionRecords(ProductionRecords constructPart,String token);
	DataWrapper<ProductionRecords> getProductionRecordsById(String token, Long id);
	DataWrapper<List<ProductionRecords>> getProductionRecordsList(String token, Long constructionLogId);
}
