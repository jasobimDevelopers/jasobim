package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.SitePaperRelationDao;
import com.my.spring.model.SitePaperRelation;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class SitePaperRelationDaoImpl extends BaseDao<SitePaperRelation> implements SitePaperRelationDao {

    @Override
    public boolean addSitePaperRelation(SitePaperRelation SitePaperRelation) {
        return save(SitePaperRelation);
    }

    @Override
    public boolean deleteSitePaperRelation(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<SitePaperRelation>> getSitePaperRelationList(SitePaperRelation spr) {
        DataWrapper<List<SitePaperRelation>> retDataWrapper = new DataWrapper<List<SitePaperRelation>>();
        List<SitePaperRelation> ret = new ArrayList<SitePaperRelation>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(SitePaperRelation.class);
        if(spr.getBfmId()!=null){
        	criteria.add(Restrictions.eq("bfmId", spr.getBfmId()));
        }
        if(spr.getPaperOfMeasuredId()!=null){
        	criteria.add(Restrictions.eq("paperOfMeasuredId", spr.getPaperOfMeasuredId()));
        }
        if(spr.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", spr.getProjectId()));
        }
        if(spr.getSiteId()!=null){
        	criteria.add(Restrictions.eq("siteId", spr.getSiteId()));
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
	public boolean deleteSitePaperRelationByProjectId(Long id) {
		String sql = "delete from site_paper_relation where project_id="+id;
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
	public boolean addSitePaperRelation(List<SitePaperRelation> sprList) {
		// TODO Auto-generated method stub
		return saveList(sprList);
	}

	@Override
	public boolean deleteSitePaperRelationBySiteId(Long id) {
		String sql = "delete from site_paper_relation where site_id="+id;
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
}
