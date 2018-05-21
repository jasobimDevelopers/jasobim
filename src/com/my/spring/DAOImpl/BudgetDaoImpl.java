package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.BudgetDao;
import com.my.spring.model.Budget;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BudgetDaoImpl extends BaseDao<Budget> implements BudgetDao {

    @Override
    public boolean addBudget(Budget m) {
        return save(m);
    }

    @Override
    public boolean deleteBudget(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateBudget(Budget m) {
        return update(m);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Budget>> getBudgetList( Integer pageIndex, Integer pageSize, Budget m) {
        DataWrapper<List<Budget>> retDataWrapper = new DataWrapper<List<Budget>>();
        List<Budget> ret = new ArrayList<Budget>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Budget.class);
        criteria.addOrder(Order.asc("uploadDate"));
       
        if(m.getUserId()!=null){
        	criteria.add(Restrictions.eq("userId", m.getUserId()));
        }
        if(m.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", m.getProjectId()));
        }
        /////////////////////////////////////
   
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


	@Override
	public Budget getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public boolean addBudgetList(List<Budget> mst) {
		// TODO Auto-generated method stub
		return saveList(mst);
	}


	

	

}
