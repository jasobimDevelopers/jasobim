package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.NewsInfoDao;
import com.my.spring.model.NewsInfo;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NewsInfoDaoImpl extends BaseDao<NewsInfo> implements NewsInfoDao {

    @Override
    public boolean addNewsInfo(NewsInfo News) {
        return save(News);
    }

    @Override
    public boolean deleteNewsInfo(Long id) {
        return delete(get(id));
    }

   
    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<NewsInfo>> getNewsInfoList( Integer pageIndex, Integer pageSize, NewsInfo News,List<NewsInfo> nes) {
        DataWrapper<List<NewsInfo>> retDataWrapper = new DataWrapper<List<NewsInfo>>();
        List<NewsInfo> ret = new ArrayList<NewsInfo>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(NewsInfo.class);
        criteria.addOrder(Order.desc("createDate"));
        /////////////////////////////////////
        if(News!=null){
        	if(News.getId()!=null){
        		criteria.add(Restrictions.eq("id", News.getId()));
        	}
        }
        if(nes!=null){
        	 if(!nes.isEmpty()){
             	List<Long> ids = new ArrayList<Long>();
             	for(NewsInfo neitem:nes){
             		ids.add(neitem.getId());
             	}
             	criteria.add(Restrictions.not(Restrictions.in("id",ids)));
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
	public DataWrapper<List<NewsInfo>> getNewsInfoListByUserId(Long userId) {
		 DataWrapper<List<NewsInfo>> retDataWrapper = new DataWrapper<List<NewsInfo>>();
	        
	        //ret=find("select * from User where userId=?"+userId);
		List<NewsInfo> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(NewsInfo.class);
        criteria.add(Restrictions.eq("createUserId",userId));
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
	public NewsInfo getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public boolean updateNewsInfo(NewsInfo newsInfoNew) {
		// TODO Auto-generated method stub
		return update(newsInfoNew);
	}

	@Override
	public List<NewsInfo> getNewsInfoListByOptions() {
		// TODO Auto-generated method stub
		List<NewsInfo> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(NewsInfo.class);
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.eq("readState", 1));
        disjunction.add(Restrictions.eq("readStatus", 1));
        criteria.add(disjunction);
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
		return ret;
	}

	
	

}
