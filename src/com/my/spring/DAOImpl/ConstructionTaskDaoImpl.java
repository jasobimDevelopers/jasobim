package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ConstructionTaskDao;
import com.my.spring.model.ConstructionTask;
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
    public DataWrapper<List<ConstructionTask>> getConstructionTasksList( Integer pageIndex, Integer pageSize, ConstructionTask constructionTask,Integer state,String userName) {
        DataWrapper<List<ConstructionTask>> retDataWrapper = new DataWrapper<List<ConstructionTask>>();
        List<ConstructionTask> ret = new ArrayList<ConstructionTask>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ConstructionTask.class);
        criteria.addOrder(Order.desc("createDate"));
        if(constructionTask.getId()!=null){
        	criteria.add(Restrictions.eq("id",constructionTask.getId()));
        }
        if(constructionTask.getTaskFlag()!=null){
        	criteria.add(Restrictions.eq("taskFlag", constructionTask.getTaskFlag()));
        }
        if(!constructionTask.getUserProjectIdList().equals("-1")){
        	criteria.add(Restrictions.or(Restrictions.like("approvalPeopleName", "%"+constructionTask.getApprovalPeopleName()+"%"),
                    Restrictions.eq("nextReceivePeopleId", constructionTask.getNextReceivePeopleId()),
                    Restrictions.eq("createUserName", constructionTask.getCreateUserName())));
        	String[] idlist = constructionTask.getUserProjectIdList().split(",");
        	Long[] idlists = new Long[idlist.length];
        	for(int j=0;j<idlist.length;j++){
        		idlists[j]=Long.valueOf(idlist[j]);
        	}
        }
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

	

	

}
