package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.QualityCheckReadStateDao;
import com.my.spring.model.QualityCheckReadState;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class QualityCheckReadStateDaoImpl extends BaseDao<QualityCheckReadState> implements QualityCheckReadStateDao {

    @Override
    public boolean addQualityCheckReadState(QualityCheckReadState role) {
        return save(role);
    }
    @Override
    public boolean deleteQualityCheckReadState(QualityCheckReadState role) {
        return delete(get(role.getId()));
    }

	
	@Override
	public List<QualityCheckReadState> getQualityCheckReadStateListsByIds(Integer QualityCheckReadStateType,Long aboutId,Long userId) {
		List<QualityCheckReadState> gets=new ArrayList<QualityCheckReadState>();
		Session session=getSession();
        Criteria criteria = session.createCriteria(QualityCheckReadState.class);
        criteria.addOrder(Order.desc("createDate"));
        if(QualityCheckReadStateType!=null){
        	criteria.add(Restrictions.eq("QualityCheckReadStateType", QualityCheckReadStateType));
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
	public List<QualityCheckReadState> getQualityCheckReadStateLists(QualityCheckReadState QualityCheckReadState) {
		List<QualityCheckReadState> gets=new ArrayList<QualityCheckReadState>();
		Session session=getSession();
        Criteria criteria = session.createCriteria(QualityCheckReadState.class);
        if(QualityCheckReadState.getQualityCheckId()!=null){
        	criteria.add(Restrictions.eq("qualityCheckId", QualityCheckReadState.getQualityCheckId()));
        }
        if(QualityCheckReadState.getQualityType()!=null){
        	 criteria.add(Restrictions.eq("qualityType", QualityCheckReadState.getQualityType()));
        }
        if(QualityCheckReadState.getState()!=null){
        	 criteria.add(Restrictions.eq("state", QualityCheckReadState.getState()));
        }
        if(QualityCheckReadState.getUserId()!=null){
            criteria.add(Restrictions.eq("userId", QualityCheckReadState.getUserId()));
        }
        try {
            gets = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return gets;
	}
	@Override
	public boolean addQualityCheckReadStateList(List<QualityCheckReadState> sendUserList) {
		// TODO Auto-generated method stub
		return saveList(sendUserList);
	}
	@Override
	public boolean updateQualityCheckReadState(QualityCheckReadState QualityCheckReadState) {
		// TODO Auto-generated method stub
		return update(QualityCheckReadState);
	}
	@Override
	public boolean updateAllQualityCheckReadStateByUserId(Long id) {
		String sql = "update quality_check_read_state set state=1 where user_id="+id;
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
