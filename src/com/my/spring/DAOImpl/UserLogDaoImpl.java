package com.my.spring.DAOImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.UserLogDao;
import com.my.spring.model.UserLog;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;


@Repository
public class UserLogDaoImpl extends BaseDao<UserLog> implements UserLogDao {

	
	@Override
	public boolean addUserLog(UserLog userLog) {
		// TODO Auto-generated method stub
		return save(userLog);
	}

	@Override
	public UserLog getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}


	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<UserLog>> getUserLogList(Integer pageSize, Integer pageIndex,UserLog UserLog) {
		// TODO Auto-generated method stub
		DataWrapper<List<UserLog>> dataWrapper = new DataWrapper<List<UserLog>>();
        List<UserLog> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(UserLog.class);
        criteria.addOrder(Order.desc("actionDate"));
        if(UserLog!=null){
        	if(UserLog.getUserId()!=null){
        		criteria.add(Restrictions.eq("userId", UserLog.getUserId()));
        	}
        	
        	if(UserLog.getActionDate()!=null)    
            	//查询制定时间之后的记录  
                criteria.add(Restrictions.ge("actionDate",UserLog.getActionDate()));  
    	    /*if(UserLog.getActionDate()!=null)                          //查询指定时间之前的记录  
    	        criteria.add(Restrictions.le("actionDate",UserLog.getActionDate()));*/  
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
	public boolean deleteUserLog(String[] id) {
		// TODO Auto-generated method stub
		boolean bool=false;
		for(int i=0;i<id.length;i++){
			bool=delete(get(Long.valueOf(id[i])));
		}
		return bool;
	}


}
