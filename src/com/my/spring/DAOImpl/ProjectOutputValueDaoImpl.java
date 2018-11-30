package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProjectOutputValueDao;
import com.my.spring.model.ProjectOutputValue;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
@Repository
public class ProjectOutputValueDaoImpl extends BaseDao<ProjectOutputValue> implements ProjectOutputValueDao {

    @Override
    public boolean addProjectOutputValue(ProjectOutputValue role) {
        return save(role);
    }
    @Override
    public boolean deleteProjectOutputValue(Long id) {
        return delete(get(id));
    }


    
	@Override
	public ProjectOutputValue getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	

	@Override
	public boolean updateProjectOutputValue(ProjectOutputValue dp) {
		// TODO Auto-generated method stub
		return update(dp);
	}
	@Override
	public ProjectOutputValue getByProjectId(Long projectId) {
		List<ProjectOutputValue> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProjectOutputValue.class);
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
