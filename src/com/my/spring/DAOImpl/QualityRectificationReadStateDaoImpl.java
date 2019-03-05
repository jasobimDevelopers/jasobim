package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.QualityRectificationReadStateDao;
import com.my.spring.model.QualityRectificationReadState;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class QualityRectificationReadStateDaoImpl extends BaseDao<QualityRectificationReadState> implements QualityRectificationReadStateDao {

    @Override
    public boolean addQualityRectificationReadState(QualityRectificationReadState role) {
        return save(role);
    }
    @Override
    public boolean deleteQualityRectificationReadState(QualityRectificationReadState role) {
        return delete(get(role.getId()));
    }

	
	@Override
	public List<QualityRectificationReadState> getQualityRectificationReadStateListsByIds(Integer QualityRectificationReadStateType,Long aboutId,Long userId) {
		List<QualityRectificationReadState> gets=new ArrayList<QualityRectificationReadState>();
		Session session=getSession();
        Criteria criteria = session.createCriteria(QualityRectificationReadState.class);
        if(QualityRectificationReadStateType!=null){
        	criteria.add(Restrictions.eq("QualityRectificationReadStateType", QualityRectificationReadStateType));
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
	public List<QualityRectificationReadState> getQualityRectificationReadStateLists(QualityRectificationReadState QualityRectificationReadState) {
		List<QualityRectificationReadState> gets=new ArrayList<QualityRectificationReadState>();
		Session session=getSession();
        Criteria criteria = session.createCriteria(QualityRectificationReadState.class);
        if(QualityRectificationReadState.getQualityRectificationId()!=null){
        	criteria.add(Restrictions.eq("qualityRectificationId", QualityRectificationReadState.getQualityRectificationId()));
        }
        if(QualityRectificationReadState.getQualityType()!=null){
        	 criteria.add(Restrictions.eq("qualityType", QualityRectificationReadState.getQualityType()));
        }
        if(QualityRectificationReadState.getState()!=null){
        	 criteria.add(Restrictions.eq("state", QualityRectificationReadState.getState()));
        }
        if(QualityRectificationReadState.getUserId()!=null){
            criteria.add(Restrictions.eq("userId", QualityRectificationReadState.getUserId()));
        }
        try {
            gets = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return gets;
	}
	@Override
	public boolean addQualityRectificationReadStateList(List<QualityRectificationReadState> sendUserList) {
		// TODO Auto-generated method stub
		return saveList(sendUserList);
	}
	@Override
	public boolean updateQualityRectificationReadState(QualityRectificationReadState QualityRectificationReadState) {
		// TODO Auto-generated method stub
		return update(QualityRectificationReadState);
	}
	@Override
	public boolean updateAllQualityRectifitionReadStateByUserId(Long id) {
		String sql = "update quality_rectification_read_state set state=1 where user_id="+id;
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql);
			 if(query.executeUpdate()>=1){
				 return true;
			 }
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		return false;
	}
	
}
