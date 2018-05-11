package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.SafeFineDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.SafeFine;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class SafeFineDaoImpl extends BaseDao<SafeFine> implements SafeFineDao {

    @Override
    public boolean addSafeFine(SafeFine building) {
        return save(building);
    }

    @Override
    public boolean deleteSafeFine(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateSafeFine(SafeFine building) {
        return update(building);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<SafeFine>> getSafeFineList(Integer pageIndex, Integer pageSize, SafeFine duct) {
        DataWrapper<List<SafeFine>> retDataWrapper = new DataWrapper<List<SafeFine>>();
        List<SafeFine> ret = new ArrayList<SafeFine>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(SafeFine.class);
        criteria.addOrder(Order.desc("createDate"));
        if(duct!=null){
        	if(duct.getId()!=null){
            	criteria.add(Restrictions.eq("id", duct.getId()));
        	}
        	if(duct.getProjectId()!=null){
        		criteria.add(Restrictions.eq("projectId", duct.getProjectId()));
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
        retDataWrapper.setData(ret);
        retDataWrapper.setTotalNumber(totalItemNum);
        retDataWrapper.setCurrentPage(pageIndex);
        retDataWrapper.setTotalPage(totalPageNum);
        retDataWrapper.setNumberPerPage(pageSize);
        return retDataWrapper;
    }

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<SafeFine> getSafeFineByProjectId(Long projectId) {
		DataWrapper<SafeFine> dataWrapper=new DataWrapper<SafeFine>();
		List<SafeFine> ret = new ArrayList<SafeFine>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(SafeFine.class);
        criteria.add(Restrictions.eq("projectId",projectId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
        	dataWrapper.setData(ret.get(0));
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
		}
		return dataWrapper;
	}

	@Override
	public boolean deleteSafeFineByProjectId(Long id) {
		String sql = "delete from building where project_id="+id;
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql);
			 if(query.executeUpdate()==1){
				 return true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return false;
	}
}
