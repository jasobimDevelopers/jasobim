package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.CheckListsDao;
import com.my.spring.model.CheckLists;
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
public class CheckListsDaoImpl extends BaseDao<CheckLists> implements CheckListsDao {

    @Override
    public boolean addCheckLists(CheckLists role) {
        return save(role);
    }
    @Override
    public boolean deleteCheckLists(Long id) {
        return delete(get(id));
    }

	@Override
	public CheckLists getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
		
	@Override
	public boolean updateCheckLists(CheckLists dp) {
		// TODO Auto-generated method stub
		return update(dp);
	}
	@Override
	public DataWrapper<List<CheckLists>> getCheckListsListByProjectId(Integer pageIndex,Integer pageSize,Long projectId,Integer type,Long pid) {
		DataWrapper<List<CheckLists>> dataWrapper=new DataWrapper<List<CheckLists>>();
		List<CheckLists> gets=new ArrayList<CheckLists>();
		Session session=getSession();
        Criteria criteria = session.createCriteria(CheckLists.class);
        criteria.addOrder(Order.desc("createDate"));
        criteria.add(Restrictions.eq("checkType", type));
        //criteria.add(Restrictions.eq("projectId", projectId));
        if(pid!=null){
        	criteria.add(Restrictions.eq("pid",pid));
        }else{
        	criteria.add(Restrictions.isNull("pid"));
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
            gets = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        dataWrapper.setData(gets);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);
        
        return dataWrapper;
	}
	
}
