package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.WechatUrlDao;
import com.my.spring.model.WechatUrl;
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
public class WechatUrlDaoImpl extends BaseDao<WechatUrl> implements WechatUrlDao {

    @Override
    public boolean addWechatUrl(WechatUrl WechatUrl) {
        return save(WechatUrl);
    }

    @Override
    public boolean deleteWechatUrl(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateWechatUrl(WechatUrl WechatUrl) {
        return update(WechatUrl);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<WechatUrl>> getWechatUrlList( Integer pageIndex, Integer pageSize, WechatUrl WechatUrl) {
        DataWrapper<List<WechatUrl>> retDataWrapper = new DataWrapper<List<WechatUrl>>();
        List<WechatUrl> ret = new ArrayList<WechatUrl>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(WechatUrl.class);
//      criteria.add(Restrictions.eq("projectId", projectId));
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
	public DataWrapper<List<WechatUrl>> getWechatUrlListByUserId(Long userId) {
		 DataWrapper<List<WechatUrl>> retDataWrapper = new DataWrapper<List<WechatUrl>>();
	        
	        //ret=find("select * from User where userId=?"+userId);
		List<WechatUrl> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(WechatUrl.class);
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
	public WechatUrl getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public WechatUrl getWechatUrlEarly() {
	 List<WechatUrl> ret = null;
	 Session session = getSession();
     Criteria criteria = session.createCriteria(WechatUrl.class);
     criteria.addOrder(Order.desc("createDate"));
     try {
         ret = criteria.list();
     }catch (Exception e){
         e.printStackTrace();
     }
     if (ret != null && ret.size() > 0) {
    	 return ret.get(0);
	 }
		return null;
	}

	

	

}
