package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.RelationDao;
import com.my.spring.model.QuestionCopy;
import com.my.spring.model.RectifyPojo;
import com.my.spring.model.Relation;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class RelationDaoImpl extends BaseDao<Relation> implements RelationDao {

    @Override
    public boolean addRelation(Relation role) {
        return save(role);
    }
    @Override
    public boolean deleteRelation(Relation role) {
        return delete(get(role.getId()));
    }

	
	@Override
	public List<Relation> getRelationListsByIds(Integer relationType,Long aboutId,Long userId) {
		List<Relation> gets=new ArrayList<Relation>();
		Session session=getSession();
        Criteria criteria = session.createCriteria(Relation.class);
        if(relationType!=null){
        	criteria.add(Restrictions.eq("relationType", relationType));
        }
        if(aboutId!=null){
        	 criteria.add(Restrictions.eq("aboutId", aboutId));
        }
        if(userId!=null){
            criteria.add(Restrictions.eq("userId", userId));
        }
        try {
            gets = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return gets;
	}
	@Override
	public List<Relation> getRelationLists(Relation relation) {
		List<Relation> gets=new ArrayList<Relation>();
		Session session=getSession();
        Criteria criteria = session.createCriteria(Relation.class);
        if(relation.getRelationType()!=null){
        	criteria.add(Restrictions.eq("relationType", relation.getRelationType()));
        }
        if(relation.getProjectId()!=null){
        	 criteria.add(Restrictions.eq("projectId", relation.getProjectId()));
        }
        if(relation.getUserId()!=null){
            criteria.add(Restrictions.eq("userId", relation.getUserId()));
        }
        if(relation.getState()!=null){
        	criteria.add(Restrictions.eq("state", relation.getState()));
        }
        if(relation.getAboutId()!=null){
        	criteria.add(Restrictions.eq("aboutId", relation.getAboutId()));
        }
        try {
            gets = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return gets;
	}
	@Override
	public boolean addRelationList(List<Relation> sendUserList) {
		// TODO Auto-generated method stub
		return saveList(sendUserList);
	}
	@Override
	public boolean updateRelation(Relation relation) {
		// TODO Auto-generated method stub
		return update(relation);
	}
	@Override
	public List<RectifyPojo> getRelationListsByAboutId(Relation relation) {
		
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
		List<RectifyPojo> retDataWrapper = new ArrayList<RectifyPojo>();
		String sql = "select real_name as userName,id as rectifyUserId from user where id in(select user_id from relation where about_id="
		+relation.getAboutId()+" and relation_type=0 and project_id="+relation.getProjectId()+")";
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
				 .addScalar("userName",StandardBasicTypes.STRING)
				 .addScalar("rectifyUserId", StandardBasicTypes.LONG)
				 .setResultTransformer(Transformers.aliasToBean(RectifyPojo.class)); 
		    retDataWrapper=query.list();
            
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
		return retDataWrapper;
	}
	
}
