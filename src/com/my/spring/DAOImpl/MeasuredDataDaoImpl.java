package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MeasuredDataDao;
import com.my.spring.model.MeasuredData;
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

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class MeasuredDataDaoImpl extends BaseDao<MeasuredData> implements MeasuredDataDao {

	@Override
	public boolean addMeasuredDataList(List<MeasuredData> itemList) {
		// TODO Auto-generated method stub
		return saveList(itemList);
	}

	@Override
	public boolean addMeasuredData(MeasuredData itemList) {
		// TODO Auto-generated method stub
		return save(itemList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<MeasuredData>> getMeasuredDataList(Integer pageSize, Integer pageIndex,
			MeasuredData item) 
	{
		DataWrapper<List<MeasuredData>> retDataWrapper = new DataWrapper<List<MeasuredData>>();
        List<MeasuredData> ret = new ArrayList<MeasuredData>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MeasuredData.class);
        criteria.addOrder(Order.desc("createDate"));
        if(item!=null){
        	if(item.getExcelType()!=null){
        		criteria.add(Restrictions.eq("excelType", item.getExcelType()));
        	}
        	if(item.getProjectId()!=null){
        		criteria.add(Restrictions.eq("projectId", item.getProjectId()));
        	}
        }
        ///////////////////////////////
        //criteria.add(Restrictions.eq("projectId", projectId));
        

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

	@SuppressWarnings("unchecked")
	@Override
	public MeasuredData getMeasuredDataBySelfId(Long selfId) {
		List<MeasuredData> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(MeasuredData.class);
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
	@Override
	public MeasuredData getMeasuredDataById(Long id) {
		@SuppressWarnings("unused")
		DataWrapper<MeasuredData> dataWrapper = new DataWrapper<MeasuredData>();
		return get(id);
	}

	@Override
	public boolean deleteMeasuredData(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}
}

    
