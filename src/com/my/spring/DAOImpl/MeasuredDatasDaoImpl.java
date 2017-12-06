package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MeasuredDatasDao;
import com.my.spring.model.MeasuredDatas;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class MeasuredDatasDaoImpl extends BaseDao<MeasuredDatas> implements MeasuredDatasDao {

	@Override
	public boolean addMeasuredDatasList(List<MeasuredDatas> itemList) {
		// TODO Auto-generated method stub
		return saveList(itemList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<MeasuredDatas>> getMeasuredDatasList(Integer pageSize, Integer pageIndex,
			MeasuredDatas item) 
	{
		DataWrapper<List<MeasuredDatas>> retDataWrapper = new DataWrapper<List<MeasuredDatas>>();
        List<MeasuredDatas> ret = new ArrayList<MeasuredDatas>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MeasuredDatas.class);
        
        ///////////////////////////////
        if(item.getMeasuredDataId()!=null){
        	criteria.add(Restrictions.eq("measuredDataId", item.getMeasuredDataId()));
        }
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
	public MeasuredDatas getMeasuredDatasBySelfId(Long selfId) {
		List<MeasuredDatas> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(MeasuredDatas.class);
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
	public MeasuredDatas getMeasuredDatasById(Long id) {
		@SuppressWarnings("unused")
		DataWrapper<MeasuredDatas> dataWrapper = new DataWrapper<MeasuredDatas>();
		return get(id);
	}

	@Override
	public boolean addMeasuredDatas(MeasuredDatas measuredData) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteMeasuredDatasByMeasuredDataId(Long measuredDataId) {
		String sql = "delete from measured_data_list where measured_data_id="+measuredDataId;
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql);
			 if(query.executeUpdate()==1){
				 return true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 return false;
	}
}

    
