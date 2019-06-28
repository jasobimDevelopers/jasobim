package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.BuildingDao;
import com.my.spring.DAO.PaperOfMeasuredDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Building;
import com.my.spring.model.PaperOfMeasured;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class PaperOfMeasuredDaoImpl extends BaseDao<PaperOfMeasured> implements PaperOfMeasuredDao {

    @Override
    public boolean addPaperOfMeasured(PaperOfMeasured building) {
        return save(building);
    }

    @Override
    public boolean deletePaperOfMeasured(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updatePaperOfMeasured(PaperOfMeasured building) {
        return update(building);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<PaperOfMeasured>> getPaperOfMeasuredList() {
        DataWrapper<List<PaperOfMeasured>> retDataWrapper = new DataWrapper<List<PaperOfMeasured>>();
        List<PaperOfMeasured> ret = new ArrayList<PaperOfMeasured>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(PaperOfMeasured.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<PaperOfMeasured> getPaperOfMeasuredByProjectId(Long projectId) {
		List<PaperOfMeasured> ret = new ArrayList<PaperOfMeasured>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(PaperOfMeasured.class);
        criteria.add(Restrictions.eq("projectId",projectId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
		return ret;
	}

	@Override
	public boolean deletePaperOfMeasuredByProjectId(Long id) {
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

	@Override
	public PaperOfMeasured getById(Long paperOfMeasuredId) {
		// TODO Auto-generated method stub
		return get(paperOfMeasuredId);
	}
}
