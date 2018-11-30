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
import com.my.spring.DAO.AttenceModelDao;
import com.my.spring.DAO.BaseDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.AttenceModel;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
@Repository
public class AtttenceModelDaoImpl extends BaseDao<AttenceModel> implements AttenceModelDao {

	@Override
	public boolean addAttenceModel(AttenceModel am) {
		// TODO Auto-generated method stub
		return save(am);
	}

	@Override
	public boolean deleteAttenceModel(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public boolean updateAttenceModel(AttenceModel am) {
		// TODO Auto-generated method stub
		return update(am);
	}
	@Override
	public AttenceModel getAttenceModelById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public DataWrapper<List<AttenceModel>> getAttenceModelList(Integer pageIndex, Integer pageSize,AttenceModel am) {
		// TODO Auto-generated method stub
		DataWrapper<List<AttenceModel>> result = new DataWrapper<List<AttenceModel>>();
        List<AttenceModel> ret = new ArrayList<AttenceModel>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(AttenceModel.class);
	    criteria.addOrder(Order.desc("createDate"));
	    if(am.getProjectId()!=null){
	    	criteria.add(Restrictions.eq("projectId", am.getProjectId()));
	    }
	    if(am.getUserId()!=null){
	    	criteria.add(Restrictions.eq("userId", am.getUserId()));
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
	public DataWrapper<Void> deleteAttenceModelByProjectId(Long id) {
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		String sql="delete * from attence_model where project_id="+id;
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
	public AttenceModel getAttenceModelByProjectId(Long projectId) {
		List<AttenceModel> ret = null;
	    Session session = getSession();
	    Criteria criteria = session.createCriteria(AttenceModel.class);
	    criteria.add(Restrictions.eq("projectId",projectId));
	    try {
	        ret = criteria.list();
	    }catch (Exception e){
	        e.printStackTrace();
	    }
	    if (ret != null && ret.size() > 0) {
	    	return ret.get(0);
		}
		return null;
	}

}
