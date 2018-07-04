package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ItemDataDao;
import com.my.spring.model.ItemData;
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
public class ItemDataDaoImpl extends BaseDao<ItemData> implements ItemDataDao {

    @Override
    public boolean addItemData(ItemData ItemData) {
        return save(ItemData);
    }


    @Override
    public boolean updateItemData(ItemData ItemData) {
        return update(ItemData);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ItemData>> getItemDataList( Integer pageIndex, Integer pageSize, ItemData ItemData) {
        DataWrapper<List<ItemData>> retDataWrapper = new DataWrapper<List<ItemData>>();
        List<ItemData> ret = new ArrayList<ItemData>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ItemData.class);
        criteria.addOrder(Order.desc("createDate"));
        if(ItemData.getCreateUser()!=null){
        	criteria.add(Restrictions.eq("createUser", ItemData.getCreateUser()));
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
	public ItemData getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}


}
