package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProjectExamDao;
import com.my.spring.model.ProjectExam;
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
public class ProjectExamDaoImpl extends BaseDao<ProjectExam> implements ProjectExamDao {

    @Override
    public boolean addProjectExam(ProjectExam projectExam) {
        return save(projectExam);
    }

    @Override
    public boolean deleteProjectExam(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateProjectExam(ProjectExam projectExam) {
        return update(projectExam);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ProjectExam>> getProjectExamList() {
        DataWrapper<List<ProjectExam>> retDataWrapper = new DataWrapper<List<ProjectExam>>();
        List<ProjectExam> ret = new ArrayList<ProjectExam>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProjectExam.class);
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
