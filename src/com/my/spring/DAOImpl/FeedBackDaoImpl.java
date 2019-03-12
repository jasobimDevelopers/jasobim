package com.my.spring.DAOImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.FeedBackDao;
import com.my.spring.model.FeedBack;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;

@Repository
public class FeedBackDaoImpl extends BaseDao<FeedBack> implements FeedBackDao {

	
	@Override
	public boolean addFeedBack(FeedBack feedBack) {
		// TODO Auto-generated method stub
		return save(feedBack);
	}

	@Override
	public FeedBack getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}


	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<FeedBack>> getFeedBackList(Integer pageSize, Integer pageIndex,FeedBack feedBack,String dates) {
		// TODO Auto-generated method stub
		DataWrapper<List<FeedBack>> dataWrapper = new DataWrapper<List<FeedBack>>();
        List<FeedBack> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(FeedBack.class);
        criteria.addOrder(Order.asc("date"));
        if(dates!=null && !dates.equals("")){
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    		try {
				Date input=sdf.parse(dates);
				Calendar rightNow = Calendar.getInstance();
        	    rightNow.setTime(input);
        	    rightNow.add(Calendar.DAY_OF_YEAR,1);//日期加1天
        	    Date dt1=rightNow.getTime();
        		criteria.add(Restrictions.ge("date",input));
            	//查询制定时间之后的记录  
        		criteria.add(Restrictions.le("date",dt1)); 
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        if(feedBack!=null){
        	if(feedBack.getUserName()!=null && !feedBack.getUserName().equals("")){
        		criteria.add(Restrictions.like("userName", "%" + feedBack.getUserName() + "%"));
        	}
        	if(feedBack.getContent()!=null && !feedBack.getContent().equals("")){
        		criteria.add(Restrictions.like("content", "%"+feedBack.getContent()+"%"));
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
        dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);

        return dataWrapper;
	}


	@Override
	public boolean deleteFeedBack(String[] id) {
		// TODO Auto-generated method stub
		boolean bool=false;
		for(int i=0;i<id.length;i++){
			bool=delete(get(Long.valueOf(id[i])));
		}
		return bool;
	}


}
