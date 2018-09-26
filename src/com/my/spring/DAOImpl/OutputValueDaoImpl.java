package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.OutputValueDao;
import com.my.spring.model.OutputValue;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Repository
public class OutputValueDaoImpl extends BaseDao<OutputValue> implements OutputValueDao {

    @Override
    public boolean addOutputValue(OutputValue role) {
        return save(role);
    }
    @Override
    public boolean deleteOutputValue(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<OutputValue>> getOutputValueList(Integer pageIndex, Integer pageSize, OutputValue OutputValue,Date start, Date end) {
        DataWrapper<List<OutputValue>> dataWrapper = new DataWrapper<List<OutputValue>>();
        List<OutputValue> ret = new ArrayList<OutputValue>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(OutputValue.class);
        criteria.addOrder(Order.desc("valueDate"));
        if(OutputValue.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", OutputValue.getProjectId()));
        }
        if(OutputValue.getYear()!=null){
        	criteria.add(Restrictions.eq("year", OutputValue.getYear()));
        }
        if(OutputValue.getMonth()!=null){
        	criteria.add(Restrictions.eq("month", OutputValue.getMonth()));
        }
        if(start!=null){
        	criteria.add(Restrictions.ge("valueDate",start));  
        }
        if(end!=null){
        	criteria.add(Restrictions.le("valueDate",end));  
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
        dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);
        return dataWrapper;
    }

	@Override
	public OutputValue getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	
	@Override
	public boolean updateOutputValue(OutputValue dp) {
		// TODO Auto-generated method stub
		return update(dp);
	}
	@Override
	public OutputValue getOutputValueLists(OutputValue role) {
        List<OutputValue> ret = new ArrayList<OutputValue>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(OutputValue.class);
        if(role.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", role.getProjectId()));
        }
        if(role.getYear()!=null){
        	criteria.add(Restrictions.eq("year", role.getYear()));
        }
        if(role.getMonth()!=null){
        	criteria.add(Restrictions.lt("month", role.getMonth()));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(ret!=null){
        	if(!ret.isEmpty()){
        		return ret.get(0);
        	}
        }
        return null;
	}
	@Override
	public List<OutputValue> getBeOutputValueLists(OutputValue role) {
        List<OutputValue> ret = new ArrayList<OutputValue>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(OutputValue.class);
        if(role.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", role.getProjectId()));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
	}
	
}
