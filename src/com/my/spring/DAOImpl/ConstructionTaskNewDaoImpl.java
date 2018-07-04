package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ConstructionTaskNewDao;
import com.my.spring.model.ConstructionTaskNew;
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
public class ConstructionTaskNewDaoImpl extends BaseDao<ConstructionTaskNew> implements ConstructionTaskNewDao {

    @Override
    public boolean addConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew) {
        return save(ConstructionTaskNew);
    }

    @Override
    public boolean deleteConstructionTaskNew(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew) {
        return update(ConstructionTaskNew);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ConstructionTaskNew>> getConstructionTaskNewList( Integer pageIndex, Integer pageSize, ConstructionTaskNew ConstructionTaskNew) {
        DataWrapper<List<ConstructionTaskNew>> retDataWrapper = new DataWrapper<List<ConstructionTaskNew>>();
        List<ConstructionTaskNew> ret = new ArrayList<ConstructionTaskNew>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ConstructionTaskNew.class);
//      criteria.add(Restrictions.eq("projectId", projectId));
        criteria.addOrder(Order.desc("createDate"));
        if(ConstructionTaskNew.getCreateUser()!=null){
        	criteria.add(Restrictions.eq("createUser", ConstructionTaskNew.getCreateUser()));
        }
        if(ConstructionTaskNew.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", ConstructionTaskNew.getProjectId()));
        }
        if(ConstructionTaskNew.getName()!=null){
        	criteria.add(Restrictions.eq("name", ConstructionTaskNew.getName()));
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
	public ConstructionTaskNew getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}	

}
