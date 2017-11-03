package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProjectStudyDao;
import com.my.spring.model.ProjectStudy;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class ProjectStudyDaoImpl extends BaseDao<ProjectStudy> implements ProjectStudyDao {

    @Override
    public boolean addProjectStudy(ProjectStudy projectStudy) {
        return save(projectStudy);
    }

    @Override
    public boolean deleteProjectStudy(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateProjectStudy(ProjectStudy projectStudy) {
        return update(projectStudy);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ProjectStudy>> getProjectStudysList( Integer pageIndex, Integer pageSize, ProjectStudy projectStudy) {
        DataWrapper<List<ProjectStudy>> retDataWrapper = new DataWrapper<List<ProjectStudy>>();
        List<ProjectStudy> ret = new ArrayList<ProjectStudy>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProjectStudy.class);
        criteria.addOrder(Order.desc("submitDate"));
   
        if (pageSize == null) {
			pageSize = 10;
		}
        if (pageIndex == null) {
			pageIndex = 1;
		}
        
        // 取总页数
        criteria.setProjection(Projections.rowCount());
        int totalItemNum = ((Long)criteria.uniqueResult()).intValue();
        int totalPageNum = DaoUtil.getTotalPageNumber(totalItemNum, pageSize);

        // 真正取值
        criteria.setProjection(null);
        if (pageSize > 0 && pageIndex > 0) {
            criteria.setMaxResults(pageSize);// 最大显示记录数
            criteria.setFirstResult((pageIndex - 1) * pageSize);// 从第几条开始
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        retDataWrapper.setTotalNumber(totalItemNum);
        retDataWrapper.setCurrentPage(pageIndex);
        retDataWrapper.setTotalPage(totalPageNum);
        retDataWrapper.setNumberPerPage(pageSize);
        return retDataWrapper;
    }

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<ProjectStudy>> getProjectStudysListByUserId(Long submitUserId) {
		DataWrapper<List<ProjectStudy>> retDataWrapper = new DataWrapper<List<ProjectStudy>>();
		List<ProjectStudy> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProjectStudy.class);
        criteria.add(Restrictions.eq("submitUserId",submitUserId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			retDataWrapper.setData(ret);
		}
		return retDataWrapper;
	}

	@Override
	public ProjectStudy getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	

	

}
