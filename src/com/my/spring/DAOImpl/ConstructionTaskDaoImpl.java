package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ConstructionTaskDao;
import com.my.spring.model.AdvancedOrderCopy;
import com.my.spring.model.ConstructionTask;
import com.my.spring.model.ConstructionTaskCopy;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ConstructionTaskDaoImpl extends BaseDao<ConstructionTask> implements ConstructionTaskDao {

    @Override
    public boolean addConstructionTask(ConstructionTask ConstructionTask) {
        return save(ConstructionTask);
    }

    @Override
    public boolean deleteConstructionTask(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateConstructionTask(ConstructionTask ConstructionTask) {
        return update(ConstructionTask);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ConstructionTask>> getConstructionTasksList( Integer pageIndex, Integer pageSize, ConstructionTask constructionTask,Integer state,String userName,String s,Long id) {
        DataWrapper<List<ConstructionTask>> retDataWrapper = new DataWrapper<List<ConstructionTask>>();
        List<ConstructionTask> ret = new ArrayList<ConstructionTask>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ConstructionTask.class);
        criteria.addOrder(Order.desc("createDate"));
        Disjunction res = Restrictions.disjunction() ; 
        if(constructionTask.getId()!=null){
        	criteria.add(Restrictions.eq("id",constructionTask.getId()));
        }
        if(constructionTask.getTaskFlag()!=null){
        	criteria.add(Restrictions.eq("taskFlag", constructionTask.getTaskFlag()));
        }
        if(constructionTask.getOthersAttention()!=null){
        	criteria.add(Restrictions.like("companyName", "%"+constructionTask.getOthersAttention()+"%"));
        }
        res.add(Restrictions.like("workPeopleNameList", "%"+id+"%"));
        if(constructionTask.getCompanyName()!=null){
        	criteria.add(Restrictions.like("companyName", "%"+constructionTask.getCompanyName()+"%"));
        }
        if(constructionTask.getNextReceivePeopleId()!=null){
        	criteria.add(Restrictions.like("nextReceivePeopleId", "%"+constructionTask.getNextReceivePeopleId()+"%"));
        }
        if(constructionTask.getApprovalPeopleName()!=null){
        	res.add(Restrictions.like("approvalPeopleName", "%"+constructionTask.getApprovalPeopleName()+"%"));
        }
        if(s!=null){
        	res.add(Restrictions.eq("nextReceivePeopleId", s));	
        }
        if(userName!=null){
        	res.add(Restrictions.eq("createUserName", userName));
                   
        }
        criteria.add(res);
        if(constructionTask.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", constructionTask.getProjectId()));
        }
        if(constructionTask.getTaskContent()!=null){
        	criteria.add(Restrictions.like("taskContent", "%"+constructionTask.getTaskContent()+"%"));
        }
        if(state!=null){
        	if(state==0){
        		criteria.add(Restrictions.eq("nextReceivePeopleId", userName));///未审批
        	}
        	if(state==1){
        		criteria.add(Restrictions.like("approvalPeopleName","%"+userName+"%"));///已审批
        	}
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
        retDataWrapper.setData(ret);
        retDataWrapper.setTotalNumber(totalItemNum);
        retDataWrapper.setCurrentPage(pageIndex);
        retDataWrapper.setTotalPage(totalPageNum);
        retDataWrapper.setNumberPerPage(pageSize);
        return retDataWrapper;
    }

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<ConstructionTask>> getConstructionTasksListByUserId(Long submitUserId) {
		DataWrapper<List<ConstructionTask>> retDataWrapper = new DataWrapper<List<ConstructionTask>>();
		List<ConstructionTask> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(ConstructionTask.class);
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
	public ConstructionTask getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public List<ConstructionTaskCopy> getAdvancedOrdersListNotRead(Long id, Integer pageSize, Integer pageIndex) {
		if(pageIndex==null){
			pageIndex=1;
		}
		if(pageSize==null){
			pageSize=10;
		}
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
		List<ConstructionTaskCopy> retDataWrapper = new ArrayList<ConstructionTaskCopy>();
		String sql = "select a.id,a.company_name,a.work_people_name_list,a.create_date,a.user_id,a.detail_content,"
				+"a.project_id,a.file_id_list from construction_task a,notice b where a.id=b.about_id and b.user_id="
				+id+" and b.notice_type=2 and b.read_state=0";
		if(pageIndex!=-1){
			sql = sql +" limit "+(pageSize*pageIndex-pageSize)+","+pageSize;
		}
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
		    		.addScalar("id",StandardBasicTypes.LONG)
					 .addScalar("company_name", StandardBasicTypes.STRING)
					 .addScalar("work_people_name_list", StandardBasicTypes.STRING)
					 .addScalar("create_date", StandardBasicTypes.TIMESTAMP)
					 .addScalar("user_id", StandardBasicTypes.LONG)
					 .addScalar("detail_content", StandardBasicTypes.STRING)
					 .addScalar("project_id",StandardBasicTypes.LONG)
					 .addScalar("file_id_list", StandardBasicTypes.STRING)
				 .setResultTransformer(Transformers.aliasToBean(ConstructionTaskCopy.class)); 
		    retDataWrapper=query.list();
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
		return retDataWrapper;
	}

	

	

}
