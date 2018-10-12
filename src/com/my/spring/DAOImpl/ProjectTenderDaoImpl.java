package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProjectTenderDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Mechanic;
import com.my.spring.model.ProcessDataIndex;
import com.my.spring.model.ProjectTender;
import com.my.spring.model.ProjectTenderIndex;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class ProjectTenderDaoImpl extends BaseDao<ProjectTender> implements ProjectTenderDao {

    @Override
    public boolean addProjectTender(ProjectTender ProjectTender) {
        return save(ProjectTender);
    }

    @Override
    public boolean deleteProjectTender(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateProjectTender(ProjectTender ProjectTender) {
        return update(ProjectTender);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ProjectTender>> getProjectTenderList() {
        DataWrapper<List<ProjectTender>> retDataWrapper = new DataWrapper<List<ProjectTender>>();
        List<ProjectTender> ret = new ArrayList<ProjectTender>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProjectTender.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<ProjectTender>> getProjectTenderByProjectId(Long projectId,Integer pageSize,Integer pageIndex) {
		DataWrapper<List<ProjectTender>> result = new DataWrapper<List<ProjectTender>>();
        List<ProjectTender> ret = new ArrayList<ProjectTender>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProjectTender.class);
	    criteria.addOrder(Order.desc("createDate"));
	    if(projectId!=null){
	    	criteria.add(Restrictions.eq("projectId", projectId));
	    }
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
        result.setData(ret);
        result.setTotalNumber(totalItemNum);
        result.setCurrentPage(pageIndex);
        result.setTotalPage(totalPageNum);
        result.setNumberPerPage(pageSize);
        return result;
	}
	
	@Override
	public ProjectTender getProjectTenderById(Long id) {
		return get(id);
	} 
	
	@Override
	public boolean deleteProjectTenderByProjectId(Long id) {
		String sql = "delete from ProjectTender where project_id="+id;
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql);
			 if(query.executeUpdate()==1){
				 return true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return false;
	}

	@Override
	public boolean deleteProjectTenderByUserId(Long id) {
		String sql = "delete from item_data where user_id="+id;
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql);
			 if(query.executeUpdate()==1){
				 return true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return false;
	}

	@Override
	public boolean addProjectTenderList(List<ProjectTender> newList2) {
		// TODO Auto-generated method stub
		return saveList(newList2);
	}

	@Override
	public List<ProjectTenderIndex> getProjectTenderListByUserId(Long id, Long projectId, Integer pageSize,
			Integer pageIndex) {
		List<ProjectTenderIndex> gets=new ArrayList<ProjectTenderIndex>();
		if(pageSize==null){
			pageSize=10;
		}
		if(pageIndex==null){
			pageIndex=0;
		}
		String sql = "select b.name,b.id,b.project_id as projectId,a.indexs,b.create_date as createDate,b.create_user_id as createUser from user_index a,project_tender b where a.about_type=5 and a.about_id=b.id and a.user_id="
		+id+" ORDER BY a.indexs asc limit "+ pageSize*pageIndex+","+pageSize*(pageIndex+1);
		Session session=getSession();
		try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id", StandardBasicTypes.LONG)
					 .addScalar("indexs", StandardBasicTypes.LONG)
					 .addScalar("name", StandardBasicTypes.STRING)
					 .addScalar("createDate",StandardBasicTypes.TIMESTAMP)
					 .addScalar("createUser", StandardBasicTypes.LONG)
					 .addScalar("projectId", StandardBasicTypes.LONG)
					 .setResultTransformer(Transformers.aliasToBean(ProjectTenderIndex.class));
			 	gets=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	    }
		return gets;
	}
	@Override
	public Integer getProjectTenderSizeByUserId(Long id) {
		Integer total=0;
		String sql = "select COUNT(*) as total from user_index where user_id="+id+" and about_type=5";
		Session session=getSession();
		try{
			 Query query = session.createSQLQuery(sql);
			 total=Integer.valueOf(query.getQueryString());
	        }catch(Exception e){
	            e.printStackTrace();
	    }
		return total;
	}	
}
