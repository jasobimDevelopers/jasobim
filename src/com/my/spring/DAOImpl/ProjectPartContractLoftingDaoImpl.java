package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProjectPartContractLoftingDao;
import com.my.spring.model.ProjectPartContractLofting;
import com.my.spring.model.UserIndexs;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProjectPartContractLoftingDaoImpl extends BaseDao<ProjectPartContractLofting> implements ProjectPartContractLoftingDao {

    @Override
    public boolean addProjectPartContractLofting(ProjectPartContractLofting role) {
        return save(role);
    }
    @Override
    public boolean addProjectPartContractLoftingList(List<ProjectPartContractLofting> role) {
        return saveList(role);
    }
    @Override
    public boolean deleteProjectPartContractLofting(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public List<ProjectPartContractLofting> getProjectPartContractLoftingList(Integer pageIndex, Integer pageSize, ProjectPartContractLofting ProjectPartContractLofting) {
        List<ProjectPartContractLofting> ret2 = new ArrayList<ProjectPartContractLofting>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProjectPartContractLofting.class);
        criteria.add(Restrictions.eq("projectId", ProjectPartContractLofting.getProjectId()));
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.groupProperty("name"));
        criteria.setProjection(projectionList);
        criteria.addOrder(Order.asc("id"));
        try {
            ret2 = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
       
        return ret2;
    }

	@Override
	public ProjectPartContractLofting getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	
	@Override
	public boolean deleteProjectPartContractLoftingList(String[] ids) {
		// TODO Auto-generated method stub
		return deleteList(ids);
	}
	@Override
	public boolean updateProjectPartContractLofting(ProjectPartContractLofting dp) {
		// TODO Auto-generated method stub
		return update(dp);
	}
	@Override
	public List<UserIndexs> getProjectPartContractLoftingListByUserId(Long userId) {
		List<UserIndexs> gets=new ArrayList<UserIndexs>();
		String sql = "select b.name,b.id,a.indexs from user_index a,ProjectPartContractLofting b where a.about_type=0 and a.about_id=b.id and a.user_id="
		+userId+" ORDER BY a.indexs asc";
		Session session=getSession();
		try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id", StandardBasicTypes.LONG)
					 .addScalar("name", StandardBasicTypes.STRING)
					 .addScalar("indexs", StandardBasicTypes.LONG)
					 .setResultTransformer(Transformers.aliasToBean(UserIndexs.class));
			 	gets=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	    }
		return gets;
	}
	@Override
	public List<ProjectPartContractLofting> getProjectPartContractLoftingListByContractLoftingId(Long id) {
        List<ProjectPartContractLofting> ret = new ArrayList<ProjectPartContractLofting>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProjectPartContractLofting.class);
        criteria.add(Restrictions.eq("contractLoftingId", id));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return ret;
	}
	
}
