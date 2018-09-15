package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProcessLogDao;
import com.my.spring.model.Folder;
import com.my.spring.model.ProcessLog;
import com.my.spring.model.ProcessLogSql;
import com.my.spring.model.UserLogPojos;
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
public class ProcessLogDaoImpl extends BaseDao<ProcessLog> implements ProcessLogDao {

    @Override
    public boolean addProcessLog(ProcessLog ProcessLog) {
        return save(ProcessLog);
    }

    @Override
    public boolean deleteProcessLog(Long id) {
        return delete(get(id));
    }
    @Override
    public boolean deleteProcessLogByAbout(Long id,Integer type) {
    	String sql = "delete from process_log where about_id="+id+" and type="+type;
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

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ProcessLog>> getProcessLogList( Integer pageIndex, Integer pageSize, ProcessLog ProcessLog) {
        DataWrapper<List<ProcessLog>> retDataWrapper = new DataWrapper<List<ProcessLog>>();
        List<ProcessLog> ret = new ArrayList<ProcessLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProcessLog.class);
//      criteria.add(Restrictions.eq("projectId", projectId));
        criteria.addOrder(Order.desc("createDate"));
        if(ProcessLog.getProcessId()!=null){
        	criteria.add(Restrictions.eq("processId", ProcessLog.getProcessId()));
        }
        if(ProcessLog.getItemId()!=null){
        	criteria.add(Restrictions.eq("itemId", ProcessLog.getItemId()));
        }
        if(ProcessLog.getCurrentNode()!=null){
        	criteria.add(Restrictions.eq("currentNode", ProcessLog.getCurrentNode()));
        }
        if(ProcessLog.getEndFlag()!=null){
        	criteria.add(Restrictions.eq("endFlag", ProcessLog.getEndFlag()));
        }
        if(ProcessLog.getItemState()!=null){
        	criteria.add(Restrictions.eq("item_state", ProcessLog.getItemState()));
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
       public List<ProcessLog> getProcessLogListByInfos(Long aboutId,Long id) {
           List<ProcessLog> ret = new ArrayList<ProcessLog>();
           Session session = getSession();
           Criteria criteria = session.createCriteria(ProcessLog.class);
//         criteria.add(Restrictions.eq("projectId", projectId));
           criteria.addOrder(Order.desc("createDate"));
           if(aboutId!=null){
           	criteria.add(Restrictions.eq("aboutId", aboutId));
           }
           if(id!=null){
        	   criteria.add(Restrictions.gt("id", id));
           }
          
          
           /////////////////////////////////////
      
           try {
               ret = criteria.list();
           }catch (Exception e){
               e.printStackTrace();
           }
           return ret;
       }
	@Override
    public ProcessLog getProcessLogListByInfo(Long aboutId,Long id) {
        List<ProcessLog> ret = new ArrayList<ProcessLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProcessLog.class);
//      criteria.add(Restrictions.eq("projectId", projectId));
        criteria.addOrder(Order.desc("createDate"));
        if(aboutId!=null){
        	criteria.add(Restrictions.eq("aboutId", aboutId));
        }
        if(id!=null){
     	   criteria.add(Restrictions.eq("processId", id));
        }
       
       
        /////////////////////////////////////
   
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!ret.isEmpty()){
        	return ret.get(0);
        }
        return null;
    }
	@Override
	public ProcessLog getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public ProcessLog getProcessLogByItemDataId(Long id, Long id2) {
		 List<ProcessLog> ret = new ArrayList<ProcessLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProcessLog.class);
        criteria.addOrder(Order.desc("createDate"));
        criteria.add(Restrictions.eq("type",0));
        criteria.add(Restrictions.eq("aboutId",id2));
        criteria.add(Restrictions.eq("itemId",id));
        
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!ret.isEmpty()){
        	return ret.get(0);
        }
        return null;
	}
	@Override
	public List<ProcessLog> getProcessLogByAboutId(Long id, Long id2) {
		 List<ProcessLog> ret = new ArrayList<ProcessLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProcessLog.class);
        criteria.addOrder(Order.asc("createDate"));
        criteria.add(Restrictions.eq("type",0));
        criteria.add(Restrictions.eq("aboutId",id2));
        criteria.add(Restrictions.eq("processId",id));
        
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!ret.isEmpty()){
        	return ret;
        }
        return null;
	}

	@Override
	public List<ProcessLog> getProcessLogByAboutIds(Long id, Long id2) {
		 List<ProcessLog> ret = new ArrayList<ProcessLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProcessLog.class);
        criteria.addOrder(Order.desc("createDate"));
        criteria.add(Restrictions.eq("type",0));
        criteria.add(Restrictions.eq("endFlag", 2));
        criteria.add(Restrictions.eq("aboutId",id2));
        criteria.add(Restrictions.eq("processId",id));
        
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
	}

	@Override
	public List<ProcessLogSql> getProcessLogByStatus(Integer status) {
		List<ProcessLogSql> result = new ArrayList<ProcessLogSql>();
		String sql="select MAX(create_date) as createDate,id,about_id as aboutId,end_flag as endFlag from process_log where type=0 and end_flag="
				+status+" GROUP BY about_id";
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id", StandardBasicTypes.LONG)
					 .addScalar("aboutId", StandardBasicTypes.LONG)
					 .addScalar("createDate", StandardBasicTypes.TIMESTAMP)
					 .addScalar("endFlag", StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(ProcessLogSql.class));
			 	result=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
        return result;
	}
}
