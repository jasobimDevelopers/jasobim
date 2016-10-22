package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.model.ProjectEntity;
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
public class ProjectDaoImpl extends BaseDao<ProjectEntity> implements ProjectDao {

    @Override
    public boolean addProject(ProjectEntity project) {
        return save(project);
    }

    @Override
    public boolean deleteProject(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateProject(ProjectEntity project) {
        return update(project);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ProjectEntity>> getProjectList() {
        DataWrapper<List<ProjectEntity>> retDataWrapper = new DataWrapper<List<ProjectEntity>>();
        List<ProjectEntity> ret = new ArrayList<ProjectEntity>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProjectEntity.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }
}
