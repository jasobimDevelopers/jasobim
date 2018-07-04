package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProcessLogDao;
import com.my.spring.model.ProcessLog;
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
public class ProcessLogDaoImpl extends BaseDao<ProcessLog> implements ProcessLogDao {

    @Override
    public boolean addProcessLog(ProcessLog ProcessLog) {
        return save(ProcessLog);
    }

    @Override
    public boolean deleteProcessLog(Long id) {
        return delete(get(id));
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ProcessLog>> getProcessLogList( Integer pageIndex, Integer pageSize, ProcessLog ProcessLog) {
        DataWrapper<List<ProcessLog>> retDataWrapper = new DataWrapper<List<ProcessLog>>();
        List<ProcessLog> ret = new ArrayList<ProcessLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProcessLog.class);
//      criteria.add(Restrictions.eq("projectId", projectId));
        criteria.addOrder(Order.desc("createDate"));
        if(ProcessLog.getProcessId()!=null){
        	criteria.add(Restrictions.eq("processId", ProcessLog.getProcessId()));
        }
        if(ProcessLog.getItemId()!=null){
        	criteria.add(Restrictions.eq("itemId", ProcessLog.getItemId()));
        }
        if(ProcessLog.getCurrentNode()!=null){
        	criteria.add(Restrictions.eq("currentNode", ProcessLog.getCurrentNode()));
        }
        if(ProcessLog.getEndFlag()!=null){
        	criteria.add(Restrictions.eq("endFlag", ProcessLog.getEndFlag()));
        }
        if(ProcessLog.getItemState()!=null){
        	criteria.add(Restrictions.eq("item_state", ProcessLog.getItemState()));
        }
        /////////////////////////////////////
   
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

	@Override
	public ProcessLog getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
}
