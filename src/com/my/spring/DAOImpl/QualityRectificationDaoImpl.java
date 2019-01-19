package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.QualityRectificationDao;
import com.my.spring.model.QualityRectification;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class QualityRectificationDaoImpl extends BaseDao<QualityRectification> implements QualityRectificationDao {

    @Override
    public boolean addQualityRectification(QualityRectification role) {
        return save(role);
    }
    @Override
    public boolean deleteQualityRectification(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<QualityRectification>> getQualityRectificationList(Integer pageIndex, Integer pageSize, QualityRectification qualityManage,String ids) {
        DataWrapper<List<QualityRectification>> dataWrapper = new DataWrapper<List<QualityRectification>>();
        List<QualityRectification> ret = new ArrayList<QualityRectification>();
        Session session = getSession();
        
        Criteria criteria = session.createCriteria(QualityRectification.class);
        criteria.addOrder(Order.desc("createDate"));
        criteria.add(Restrictions.eq("projectId", qualityManage.getProjectId()));
        if(qualityManage.getId()!=null){
        	criteria.add(Restrictions.eq("id", qualityManage.getId()));
        }
        if(qualityManage.getStatus()!=null){
        	criteria.add(Restrictions.eq("status", qualityManage.getStatus()));
        }
        if(ids!=null){
        	String[] bars = ids.split(",");
        	Disjunction disjunction = Restrictions.disjunction();			
        	for(String bar : bars){
        	    disjunction.add(Restrictions.eq("id", Long.valueOf(bar)));
        	}
        	criteria.add(disjunction);
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
    @SuppressWarnings("unchecked")
   	@Override
       public List<QualityRectification> getQualityRectificationList(QualityRectification qualityManage) {
           List<QualityRectification> ret = new ArrayList<QualityRectification>();
           Session session = getSession();
           Criteria criteria = session.createCriteria(QualityRectification.class);
           criteria.addOrder(Order.desc("createDate"));
           criteria.add(Restrictions.eq("projectId", qualityManage.getProjectId()));
           if(qualityManage.getId()!=null){
           	criteria.add(Restrictions.eq("id", qualityManage.getId()));
           }
           if(qualityManage.getStatus()!=null){
           	criteria.add(Restrictions.eq("status", qualityManage.getStatus()));
           }
           if(qualityManage.getCreateUser()!=null){
        	   criteria.add(Restrictions.eq("createUser", qualityManage.getCreateUser()));
           }
           try {
               ret = criteria.list();
           }catch (Exception e){
               e.printStackTrace();
           }
           
           
           return ret;
       }

	@Override
	public QualityRectification getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	
	@Override
	public boolean deleteQualityRectificationList(String[] ids) {
		// TODO Auto-generated method stub
		return deleteList(ids);
	}
	@Override
	public boolean updateQualityRectification(QualityRectification dp) {
		// TODO Auto-generated method stub
		return update(dp);
	}
	
	
}