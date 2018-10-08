package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProcessDataDao;
import com.my.spring.model.Department;
import com.my.spring.model.ItemDataIndex;
import com.my.spring.model.Notice;
import com.my.spring.model.ProcessData;
import com.my.spring.model.ProcessDataIndex;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProcessDataDaoImpl extends BaseDao<ProcessData> implements ProcessDataDao {

    @Override
    public boolean addProcessData(ProcessData ProcessData) {
        return save(ProcessData);
    }

    @Override
    public boolean deleteProcessData(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateProcessData(ProcessData ProcessData) {
        return update(ProcessData);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ProcessData>> getProcessDataList( Integer pageIndex, Integer pageSize, ProcessData ProcessData) {
        DataWrapper<List<ProcessData>> retDataWrapper = new DataWrapper<List<ProcessData>>();
        List<ProcessData> ret = new ArrayList<ProcessData>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProcessData.class);
//      criteria.add(Restrictions.eq("projectId", projectId));
        criteria.addOrder(Order.desc("createDate"));
        if(ProcessData.getCreateUser()!=null){
        	criteria.add(Restrictions.eq("createUser", ProcessData.getCreateUser()));
        }
        if(ProcessData.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", ProcessData.getProjectId()));
        }
        if(ProcessData.getType()!=null){
        	criteria.add(Restrictions.eq("type", ProcessData.getType()));
        }
        if(ProcessData.getName()!=null){
        	criteria.add(Restrictions.eq("name", ProcessData.getName()));
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
	public ProcessData getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public ProcessData getProcessDataByProjectId(Long projectId, Integer i) {
		List<ProcessData> results= null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProcessData.class);
        criteria.add(Restrictions.eq("projectId", projectId));
        criteria.add(Restrictions.eq("type", i));
        try {
        	results = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(results.size()==0){
        	return null;
        }else{
        	return results.get(0);
        }
	}

	@Override
	public boolean deleteProcessDataByUserId(Long id) {
		String sql = "delete from process_data where user_id="+id;
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

	@Override
	public boolean addProcessDataList(List<ProcessData> newList2) {
		// TODO Auto-generated method stub
		return saveList(newList2);
	}

	@Override
	public List<ProcessDataIndex> getProcessDataListByUserId(Long id, Integer pageSize, Integer pageIndex) {
		List<ProcessDataIndex> gets=new ArrayList<ProcessDataIndex>();
		if(pageSize==null){
			pageSize=10;
		}
		if(pageIndex==null){
			pageIndex=0;
		}
		String sql = "select b.name,b.id,b.item_num as itemNum,b.project_id as projectId,b.type,a.indexs,b.create_date as createDate,b.create_user as createUser from user_index a,user_team b where a.about_type=4 and a.about_id=b.id and a.user_id="
		+id+" ORDER BY a.indexs asc limit"+ pageIndex+","+pageSize;
		Session session=getSession();
		try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id", StandardBasicTypes.LONG)
					 .addScalar("indexs", StandardBasicTypes.LONG)
					 .addScalar("name", StandardBasicTypes.INTEGER)
					 .addScalar("itemNum", StandardBasicTypes.INTEGER)
					 .addScalar("createDate",StandardBasicTypes.TIMESTAMP)
					 .addScalar("createUser", StandardBasicTypes.LONG)
					 .addScalar("projectId", StandardBasicTypes.LONG)
					 .addScalar("type", StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(ProcessDataIndex.class));
			 	gets=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	    }
		return gets;
	}

	@Override
	public Integer getProcessDataSizeByUserId(Long id) {
		Integer total=0;
		String sql = "select COUNT(*) as total from user_index where user_id="+id+" and about_type=4";
		Session session=getSession();
		try{
			 Query query = session.createSQLQuery(sql);
			 total=Integer.valueOf(query.getQueryString());
	        }catch(Exception e){
	            e.printStackTrace();
	    }
		return total;
	}	

}
