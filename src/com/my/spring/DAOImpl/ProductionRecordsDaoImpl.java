package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.my.spring.DAO.ProductionRecordsDao;
import com.my.spring.DAO.BaseDao;
import com.my.spring.model.ProductionRecords;
/**
* @author 徐雨祥
* @version 创建时间：2018年8月31日 下午1:51:31
* 类说明
*/
@Repository
public class ProductionRecordsDaoImpl extends BaseDao<ProductionRecords> implements ProductionRecordsDao {

	@Override
	public boolean addProductionRecords(ProductionRecords am) {
		// TODO Auto-generated method stub
		return save(am);
	}

	@Override
	public boolean deleteProductionRecords(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public boolean updateProductionRecords(ProductionRecords am) {
		// TODO Auto-generated method stub
		return update(am);
	}
	@Override
	public ProductionRecords getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductionRecords> getProductionRecordsList(Long constructionLogId) {
		// TODO Auto-generated method stub
        List<ProductionRecords> ret = new ArrayList<ProductionRecords>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProductionRecords.class);
	    if(constructionLogId!=null){
	    	criteria.add(Restrictions.eq("constructionLogId",constructionLogId));
	    }
        
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
	}

	
}
