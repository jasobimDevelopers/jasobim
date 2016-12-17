package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MinItemDao;
import com.my.spring.model.Item;
import com.my.spring.model.MinItem;
import com.my.spring.model.User;
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
public class MinItemDaoImpl extends BaseDao<MinItem> implements MinItemDao {

	@Override
	public boolean addMinItemList(List<MinItem> itemList) {
		// TODO Auto-generated method stub
		return saveList(itemList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<MinItem>> getMinItemList(Long projectId, Integer pageSize, Integer pageIndex,
			MinItem item) 
	{
		DataWrapper<List<MinItem>> retDataWrapper = new DataWrapper<List<MinItem>>();
        List<MinItem> ret = new ArrayList<MinItem>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MinItem.class);
        
        ///////////////////////////////
        criteria.add(Restrictions.eq("projectId", projectId));
        

        /////////////////////////////////////
   
        if (pageSize == null) {
			pageSize = 1000;
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
	public MinItem getMinItemBySelfId(Long selfId) {
		List<MinItem> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(MinItem.class);
        criteria.add(Restrictions.eq("selfId",selfId));
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

    
