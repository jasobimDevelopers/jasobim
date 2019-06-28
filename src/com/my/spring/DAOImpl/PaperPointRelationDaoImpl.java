package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.PaperPointRelationDao;
import com.my.spring.model.PaperPointRelation;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class PaperPointRelationDaoImpl extends BaseDao<PaperPointRelation> implements PaperPointRelationDao {

    @Override
    public boolean addPaperPointRelation(PaperPointRelation PaperPointRelation) {
        return save(PaperPointRelation);
    }

    @Override
    public boolean deletePaperPointRelation(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<PaperPointRelation>> getPaperPointRelationList(PaperPointRelation ppr) {
        DataWrapper<List<PaperPointRelation>> retDataWrapper = new DataWrapper<List<PaperPointRelation>>();
        List<PaperPointRelation> ret = new ArrayList<PaperPointRelation>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(PaperPointRelation.class);
        if(ppr.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", ppr.getProjectId()));
        }
        if(ppr.getCheckTypeId()!=null){
        	criteria.add(Restrictions.eq("checkTypeId", ppr.getCheckTypeId()));
        }
        if(ppr.getPaperOfMeasuredId()!=null){
        	criteria.add(Restrictions.eq("paperOfMeasuredId", ppr.getPaperOfMeasuredId()));
        }
        if(ppr.getPointId()!=null){
        	criteria.add(Restrictions.eq("pointId", ppr.getPointId()));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }

	@Override
	public boolean deletePaperPointRelationByProjectId(Long id) {
		String sql = "delete from paper_point_relation where project_id="+id;
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql);
			 if(query.executeUpdate()>1){
				 return true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return false;
	}

	@Override
	public boolean deletePaperPointRelationByIds(Long checkTypeId, Long pointId) {
		String sql = "delete from paper_point_relation where check_type_id="+checkTypeId+" and point_id="+pointId;
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql);
			 if(query.executeUpdate()>0){
				 return true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return false;
	}

	@Override
	public boolean addPaperPointRelation(List<PaperPointRelation> pprs) {
		// TODO Auto-generated method stub
		return saveList(pprs);
	}
}
