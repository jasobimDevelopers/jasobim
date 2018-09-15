package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ConstructionLogDao;
import com.my.spring.model.ConstructionLog;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
@Repository
public class ConstructionLogDaoImpl extends BaseDao<ConstructionLog> implements ConstructionLogDao {

	@Override
	public boolean addConstructionLog(ConstructionLog ps) {
		// TODO Auto-generated method stub
		return save(ps);
	}

	@Override
	public boolean deleteConstructionLog(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}
	
	@Override
	public boolean deleteConstructionLogList(String[] id) {
		// TODO Auto-generated method stub
		return deleteList(id);
	}

	@Override
	public boolean updateConstructionLog(ConstructionLog ps) {
		// TODO Auto-generated method stub
		return update(ps);
	}

	@Override
	public ConstructionLog getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<ConstructionLog>> getConstructionLogsList(Integer pageIndex, Integer pageSize,
			ConstructionLog constructionLog,String realName,Date start,Date end) {
		// TODO Auto-generated method stub
		DataWrapper<List<ConstructionLog>> cls = new DataWrapper<List<ConstructionLog>>();
		List<ConstructionLog> csl = new ArrayList<ConstructionLog>();
		Session session = getSession();
        Criteria criteria = session.createCriteria(ConstructionLog.class);
        criteria.addOrder(Order.desc("createDate"));
        if(constructionLog.getId()!=null){
        	criteria.add(Restrictions.eq("id",constructionLog.getId()));
        }
        if(constructionLog.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", constructionLog.getProjectId()));
        }
        if(realName!=null){
        	criteria.add(Restrictions.like("createUserName", "%"+realName+"%"));
        }
        if(start!=null) {
     	   //查询制定时间之后的记录  
         criteria.add(Restrictions.ge("constructionDate",start));  
	     }                    
	     if(end!=null){
	     	//查询指定时间之前的记录  
	         criteria.add(Restrictions.le("constructionDate",end)); 
	     }   
        //if()
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
            csl = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        cls.setData(csl);
        cls.setTotalNumber(totalItemNum);
        cls.setCurrentPage(pageIndex);
        cls.setTotalPage(totalPageNum);
        cls.setNumberPerPage(pageSize);
        return cls;
	}

	@Override
	public DataWrapper<List<ConstructionLog>> getConstructionTasksListByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
