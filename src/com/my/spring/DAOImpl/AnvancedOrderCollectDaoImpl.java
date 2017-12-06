package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.AdvancedOrderCollectDao;
import com.my.spring.model.AdvancedOrderCollect;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AnvancedOrderCollectDaoImpl extends BaseDao<AdvancedOrderCollect> implements AdvancedOrderCollectDao {

    @Override
    public boolean addAdvancedOrderCollect(AdvancedOrderCollect AdvancedOrderCollect) {
        return save(AdvancedOrderCollect);
    }

    @Override
    public boolean deleteAdvancedOrderCollect(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateAdvancedOrderCollect(AdvancedOrderCollect AdvancedOrderCollect) {
        return update(AdvancedOrderCollect);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<AdvancedOrderCollect>> getAdvancedOrderCollectsList( Integer pageIndex, Integer pageSize, AdvancedOrderCollect AdvancedOrderCollect) {
        DataWrapper<List<AdvancedOrderCollect>> retDataWrapper = new DataWrapper<List<AdvancedOrderCollect>>();
        List<AdvancedOrderCollect> ret = new ArrayList<AdvancedOrderCollect>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(AdvancedOrderCollect.class);
        criteria.addOrder(Order.desc("createDate"));
   
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
        retDataWrapper.setData(ret);
        retDataWrapper.setTotalNumber(totalItemNum);
        retDataWrapper.setCurrentPage(pageIndex);
        retDataWrapper.setTotalPage(totalPageNum);
        retDataWrapper.setNumberPerPage(pageSize);
        return retDataWrapper;
    }

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<AdvancedOrderCollect>> getAdvancedOrderCollectByUserId(Long submitUserId) {
		DataWrapper<List<AdvancedOrderCollect>> retDataWrapper = new DataWrapper<List<AdvancedOrderCollect>>();
		List<AdvancedOrderCollect> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(AdvancedOrderCollect.class);
        criteria.add(Restrictions.eq("submitUserId",submitUserId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			retDataWrapper.setData(ret);
		}
		return retDataWrapper;
	}
	@SuppressWarnings("unchecked")
	@Override
	public AdvancedOrderCollect getAdvancedOrderCollectByOrderId(Long submitUserId) {
		AdvancedOrderCollect retDataWrapper = new AdvancedOrderCollect();
		List<AdvancedOrderCollect> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(AdvancedOrderCollect.class);
        criteria.add(Restrictions.eq("advancedOrderId",submitUserId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			retDataWrapper=ret.get(0);
		}
		return retDataWrapper;
	}

	@Override
	public AdvancedOrderCollect getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public AdvancedOrderCollect getAdvancedOrderById(Long id) {
		AdvancedOrderCollect retDataWrapper = new AdvancedOrderCollect();
		List<AdvancedOrderCollect> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(AdvancedOrderCollect.class);
        criteria.add(Restrictions.eq("advancedOrderId", id));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			retDataWrapper=ret.get(0);
		}
		return retDataWrapper;
	}

	

	

}
