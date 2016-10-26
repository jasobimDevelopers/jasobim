package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.model.Project;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class ProjectDaoImpl extends BaseDao<Project> implements ProjectDao {

    @Override
    public boolean addProject(Project project) {
        return save(project);
    }

    @Override
    public boolean deleteProject(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateProject(Project project) {
        return update(project);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Project>> getProjectList() {
        DataWrapper<List<Project>> retDataWrapper = new DataWrapper<List<Project>>();
        List<Project> ret = new ArrayList<Project>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Project.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }

	@Override
	public Project getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
}
