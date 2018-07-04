package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProcessItemDao;
import com.my.spring.model.ProcessItem;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProcessItemDaoImpl extends BaseDao<ProcessItem> implements ProcessItemDao {

    @Override
    public boolean addProcessItem(ProcessItem ProcessItem) {
        return save(ProcessItem);
    }

    @Override
    public boolean deleteProcessItem(Long id) {
        return delete(get(id));
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ProcessItem>> getProcessItemList( Integer pageIndex, Integer pageSize, ProcessItem ProcessItem) {
        DataWrapper<List<ProcessItem>> retDataWrapper = new DataWrapper<List<ProcessItem>>();
        List<ProcessItem> ret = new ArrayList<ProcessItem>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProcessItem.class);
//      criteria.add(Restrictions.eq("projectId", projectId));
       
        if(ProcessItem.getProcessId()!=null){
        	criteria.add(Restrictions.eq("processId", ProcessItem.getProcessId()));
        }
        if(ProcessItem.getItemId()!=null){
        	criteria.add(Restrictions.eq("itemId", ProcessItem.getItemId()));
        }
        if(ProcessItem.getWhich()!=null){
        	criteria.add(Restrictions.eq("which", ProcessItem.getWhich()));
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

	

	@Override
	public ProcessItem getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	

	

	

}
