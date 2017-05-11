package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.NewsDao;
import com.my.spring.model.News;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class NewsDaoImpl extends BaseDao<News> implements NewsDao {

    @Override
    public boolean addNews(News News) {
        return save(News);
    }

    @Override
    public boolean deleteNews(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateNews(News News) {
        return update(News);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<News>> getNewsList( Integer pageIndex, Integer pageSize, News News) {
        DataWrapper<List<News>> retDataWrapper = new DataWrapper<List<News>>();
        List<News> ret = new ArrayList<News>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(News.class);
//      criteria.add(Restrictions.eq("projectId", projectId));
       
        if(News.getUserId()!=null){
        	criteria.add(Restrictions.eq("userId", News.getUserId()));
        }
        if(News.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", News.getProjectId()));
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

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<News>> getNewsListByUserId(Long userId) {
		 DataWrapper<List<News>> retDataWrapper = new DataWrapper<List<News>>();
	        
	        //ret=find("select * from User where userId=?"+userId);
		List<News> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(News.class);
        criteria.add(Restrictions.eq("userId",userId));
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
	public News getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	

	

}
