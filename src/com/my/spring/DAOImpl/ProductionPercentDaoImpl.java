package com.my.spring.DAOImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProductionPercentDao;
import com.my.spring.model.ProductionPercent;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;


@Repository
public class ProductionPercentDaoImpl extends BaseDao<ProductionPercent> implements ProductionPercentDao {

	
	@Override
	public boolean addProductionPercent(ProductionPercent ProductionPercent) {
		// TODO Auto-generated method stub
		return save(ProductionPercent);
	}

	@Override
	public ProductionPercent getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}


	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<ProductionPercent>> getProductionPercentList(Integer pageSize, Integer pageIndex,ProductionPercent ProductionPercent) {
		// TODO Auto-generated method stub
		DataWrapper<List<ProductionPercent>> dataWrapper = new DataWrapper<List<ProductionPercent>>();
        List<ProductionPercent> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProductionPercent.class);
        criteria.addOrder(Order.asc("date"));
        if(ProductionPercent!=null){
        	if(ProductionPercent.getDate()!=null){
        		criteria.add(Restrictions.like("date", "%"+ProductionPercent.getDate()+"%"));
        	}
        }
        
        if (pageSize == null) {
			pageSize = 10;
		}
        if (pageIndex == null) {
			pageIndex = 1;
		}
        
        // 取总页数
        criteria.setProjection(Projections.rowCount());
        int totalItemNum = ((Long)criteria.uniqueResult()).intValue();
        int totalPageNum = DaoUtil.getTotalPageNumber(totalItemNum, pageSize);

        // 真正取值
        criteria.setProjection(null);
        if (pageSize > 0 && pageIndex > 0) {
            criteria.setMaxResults(pageSize);// 最大显示记录数
            criteria.setFirstResult((pageIndex - 1) * pageSize);// 从第几条开始
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);

        return dataWrapper;
	}


	@Override
	public boolean deleteProductionPercent(String[] id) {
		// TODO Auto-generated method stub
		boolean bool=false;
		for(int i=0;i<id.length;i++){
			bool=delete(get(Long.valueOf(id[i])));
		}
		return bool;
	}


}
