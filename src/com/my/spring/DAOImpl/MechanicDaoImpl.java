package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.MechanicDao;
import com.my.spring.DAO.BaseDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Mechanic;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
@Repository
public class MechanicDaoImpl extends BaseDao<Mechanic> implements MechanicDao {

	@Override
	public boolean addMechanic(Mechanic am) {
		// TODO Auto-generated method stub
		return save(am);
	}

	@Override
	public boolean deleteMechanic(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public boolean updateMechanic(Mechanic am) {
		// TODO Auto-generated method stub
		return update(am);
	}
	@Override
	public Mechanic getMechanicById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public DataWrapper<List<Mechanic>> getMechanicList(Integer pageIndex, Integer pageSize,Mechanic am) {
		// TODO Auto-generated method stub
		DataWrapper<List<Mechanic>> result = new DataWrapper<List<Mechanic>>();
        List<Mechanic> ret = new ArrayList<Mechanic>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Mechanic.class);
	    criteria.addOrder(Order.desc("createDate"));
	    if(am.getProjectId()!=null){
	    	criteria.add(Restrictions.eq("projectId", am.getProjectId()));
	    }
	    if(am.getCreateUser()!=null){
	    	criteria.add(Restrictions.eq("userId", am.getCreateUser()));
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
        result.setData(ret);
        result.setTotalNumber(totalItemNum);
        result.setCurrentPage(pageIndex);
        result.setTotalPage(totalPageNum);
        result.setNumberPerPage(pageSize);
        return result;
	}

	@Override
	public DataWrapper<Void> deleteMechanicByProjectId(Long id) {
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		String sql="delete * from mechanic where project_id="+id;
		Session session=getSession();
		 try{
	            Query query=session.createSQLQuery(sql);
	            session.getTransaction().commit();
	            session.flush();
	        }catch(Exception e){
	            e.printStackTrace();
	            session.getTransaction().rollback();
	            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<Mechanic>> getMechanicListByProjectId(Integer pageIndex, Integer pageSize,
			Long projectId) {
		DataWrapper<List<Mechanic>> result = new DataWrapper<List<Mechanic>>();
        List<Mechanic> ret = new ArrayList<Mechanic>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Mechanic.class);
	    criteria.addOrder(Order.desc("createDate"));
	    criteria.add(Restrictions.eq("projectId",projectId));
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
        result.setData(ret);
        result.setTotalNumber(totalItemNum);
        result.setCurrentPage(pageIndex);
        result.setTotalPage(totalPageNum);
        result.setNumberPerPage(pageSize);
        return result;
	}

}
