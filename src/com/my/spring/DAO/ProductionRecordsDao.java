package com.my.spring.DAO;

import java.util.List;
import com.my.spring.model.ProductionRecords;
/**
* @author 徐雨祥
* @version 创建时间：2018年8月31日 下午1:51:31
* 类说明
*/
public interface ProductionRecordsDao {
	 boolean addProductionRecords(ProductionRecords am);
     boolean deleteProductionRecords(Long id);
     boolean updateProductionRecords(ProductionRecords am);
     ProductionRecords getById(Long id);
	 List<ProductionRecords> getProductionRecordsList(Long constructionLogId);
}
