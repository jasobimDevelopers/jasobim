package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.UserProjectDao;
import com.my.spring.model.UserProject;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserProjectDaoImpl extends BaseDao<UserProject> implements UserProjectDao {

    @Override
    public boolean addUserProject(UserProject paper) {
        return save(paper);
    }
    @Override
    public boolean addUserProjectList(List<UserProject> paper) {
        return saveList(paper);
    }

    @Override
    public boolean deleteUserProject(Long id) {
        return delete(get(id));
    }
    @Override
	public UserProject getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	@Override
	public List<UserProject> getUserProjectListByUserId(Long id) {
		List<UserProject> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(UserProject.class);
        criteria.add(Restrictions.eq("userId",id));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			return ret;
		}
		return ret;
	}
	@Override
	public UserProject getUserProjectListByUserIdAndProjectId(Long id, Long valueOf) {
		List<UserProject> rets =null;
		UserProject ret = new UserProject();
        Session session = getSession();
        Criteria criteria = session.createCriteria(UserProject.class);
        criteria.add(Restrictions.eq("userId",id));
        criteria.add(Restrictions.eq("projectId",valueOf));
        try {
        	rets = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (!rets.isEmpty()) {
			ret = rets.get(0);
		}else{
			return null;
		}
		return ret;
	}
	@Override
	public boolean deleteUserProjectByUserId(Long id) {
		String sql = "delete from user_project where user_id="+id;
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
