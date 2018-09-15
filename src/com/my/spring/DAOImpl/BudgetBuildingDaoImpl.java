package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.BudgetBuildingDao;
import com.my.spring.model.BudgetBuilding;
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
public class BudgetBuildingDaoImpl extends BaseDao<BudgetBuilding> implements BudgetBuildingDao {

    @Override
    public boolean addBudgetBuilding(BudgetBuilding m) {
        return save(m);
    }

    @Override
    public boolean deleteBudgetBuilding(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateBudgetBuilding(BudgetBuilding m) {
        return update(m);
    }

	@Override
    public DataWrapper<List<BudgetBuilding>> getBudgetBuildingList( Integer pageIndex, Integer pageSize, BudgetBuilding m) {
        DataWrapper<List<BudgetBuilding>> retDataWrapper = new DataWrapper<List<BudgetBuilding>>();
        List<BudgetBuilding> ret = new ArrayList<BudgetBuilding>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(BudgetBuilding.class);
        criteria.addOrder(Order.asc("createDate"));
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
	public BudgetBuilding getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}


}
