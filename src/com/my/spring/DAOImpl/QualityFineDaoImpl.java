package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.QualityFineDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.QualityFine;
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
public class QualityFineDaoImpl extends BaseDao<QualityFine> implements QualityFineDao {

    @Override
    public boolean addQualityFine(QualityFine building) {
        return save(building);
    }

    @Override
    public boolean deleteQualityFine(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateQualityFine(QualityFine building) {
        return update(building);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<QualityFine>> getQualityFineList(Integer pageIndex,Integer pageSize,QualityFine qf) {
        DataWrapper<List<QualityFine>> retDataWrapper = new DataWrapper<List<QualityFine>>();
        List<QualityFine> ret = new ArrayList<QualityFine>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(QualityFine.class);
        criteria.addOrder(Order.desc("createDate"));
        if(qf!=null){
        	if(qf.getId()!=null){
            	criteria.add(Restrictions.eq("id", qf.getId()));
        	}
        	if(qf.getProjectId()!=null){
        		criteria.add(Restrictions.eq("projectId", qf.getProjectId()));
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
	public DataWrapper<QualityFine> getQualityFineByProjectId(Long projectId) {
		DataWrapper<QualityFine> dataWrapper=new DataWrapper<QualityFine>();
		List<QualityFine> ret = new ArrayList<QualityFine>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(QualityFine.class);
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
	public boolean deleteQualityFineByProjectId(Long id) {
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
