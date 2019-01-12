package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.AwardReadStateDao;
import com.my.spring.model.AwardReadState;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class AwardReadStateDaoImpl extends BaseDao<AwardReadState> implements AwardReadStateDao {

    @Override
    public boolean addAwardReadState(AwardReadState role) {
        return save(role);
    }
    @Override
    public boolean deleteAwardReadState(AwardReadState role) {
        return delete(get(role.getId()));
    }

	
	@Override
	public List<AwardReadState> getAwardReadStateListsByIds(Integer AwardReadStateType,Long aboutId,Long userId) {
		List<AwardReadState> gets=new ArrayList<AwardReadState>();
		Session session=getSession();
        Criteria criteria = session.createCriteria(AwardReadState.class);
        if(AwardReadStateType!=null){
        	criteria.add(Restrictions.eq("AwardReadStateType", AwardReadStateType));
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
	public List<AwardReadState> getAwardReadStateLists(AwardReadState AwardReadState) {
		List<AwardReadState> gets=new ArrayList<AwardReadState>();
		Session session=getSession();
        Criteria criteria = session.createCriteria(AwardReadState.class);
        if(AwardReadState.getAwardId()!=null){
        	criteria.add(Restrictions.eq("awardId", AwardReadState.getAwardId()));
        }
        if(AwardReadState.getState()!=null){
        	 criteria.add(Restrictions.eq("state", AwardReadState.getState()));
        }
        if(AwardReadState.getUserId()!=null){
            criteria.add(Restrictions.eq("userId", AwardReadState.getUserId()));
        }
        try {
            gets = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return gets;
	}
	@Override
	public boolean addAwardReadStateList(List<AwardReadState> sendUserList) {
		// TODO Auto-generated method stub
		return saveList(sendUserList);
	}
	@Override
	public boolean updateAwardReadState(AwardReadState AwardReadState) {
		// TODO Auto-generated method stub
		return update(AwardReadState);
	}
	@Override
	public boolean updateAllAwardReadStateByUserId(Long id) {
		String sql = "update award_read_state set state=1 where user_id="+id;
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
