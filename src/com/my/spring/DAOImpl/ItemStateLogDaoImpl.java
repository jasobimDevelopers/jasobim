package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ItemStateLogDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ItemStateLog;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ItemStateLogDaoImpl extends BaseDao<ItemStateLog> implements ItemStateLogDao {

    @Override
    public boolean addItemStateLog(ItemStateLog ItemStateLog) {
        return save(ItemStateLog);
    }

    @Override
    public boolean deleteItemStateLog(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateItemStateLog(ItemStateLog ItemStateLog) {
        return update(ItemStateLog);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ItemStateLog>> getItemStateLogList() {
        DataWrapper<List<ItemStateLog>> retDataWrapper = new DataWrapper<List<ItemStateLog>>();
        List<ItemStateLog> ret = new ArrayList<ItemStateLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ItemStateLog.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<ItemStateLog> getItemStateLogByProjectId(Long projectId) {
		DataWrapper<ItemStateLog> dataWrapper=new DataWrapper<ItemStateLog>();
		List<ItemStateLog> ret = new ArrayList<ItemStateLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ItemStateLog.class);
        criteria.add(Restrictions.eq("projectId",projectId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
        	dataWrapper.setData(ret.get(0));
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
		}
		return dataWrapper;
	}

	@Override
	public boolean deleteItemStateLogByProjectId(Long id) {
		String sql = "delete from item_state_log where project_id="+id;
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
	public ItemStateLog getItemStateLogByProjectIdAndSelfId(Long selfId, Long projectId) {
        List<ItemStateLog> ret = new ArrayList<ItemStateLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ItemStateLog.class);
        criteria.add(Restrictions.eq("selfId", selfId));
        criteria.add(Restrictions.eq("projectId", projectId));
//        criteria.addOrder(Order.desc("publishDate"));
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
	public List<ItemStateLog> getItemStateLogList(Long projectId, String idList) {
	        List<ItemStateLog> ret = new ArrayList<ItemStateLog>();
	        Session session = getSession();
	        Criteria criteria = session.createCriteria(ItemStateLog.class);
	        criteria.addOrder(Order.desc("actionDate"));
	        criteria.add(Restrictions.eq("projectId", projectId));
	        if(idList!=null){
	        	String[] ids = idList.split(",");
	        	List<Long> idss = new ArrayList<Long>();
	        	for(int i=0;i<ids.length;i++){
	        		idss.add(Long.valueOf(ids[i]));
	        	}
	        	criteria.add(Restrictions.in("selfId", idss));
	        }else{
	        	return ret;
	        }
	        try {
	            ret = criteria.list();
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        return ret;
	}

	@Override
	public boolean addList(List<ItemStateLog> gets) {
		// TODO Auto-generated method stub
		return saveList(gets);
	}

	@Override
	public List<ItemStateLog> getAllItemStateLogByProjectId(Long projectId) {
		 List<ItemStateLog> ret = new ArrayList<ItemStateLog>();
         Session session = getSession();
         Criteria criteria = session.createCriteria(ItemStateLog.class);
	     criteria.addOrder(Order.desc("actionDate"));
	     criteria.add(Restrictions.eq("projectId", projectId));
         try {
            ret = criteria.list();
         }catch (Exception e){
             e.printStackTrace();
         }
         return ret;
	}
	@Override
	public List<ItemStateLog> getAllItemStateLogGroupBySelfId(Long projectId) {
		List<ItemStateLog> ret = new ArrayList<ItemStateLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ItemStateLog.class);
        criteria.add(Restrictions.eq("projectId", projectId));
        criteria.setProjection(Projections.groupProperty("selfId"));
        try {
           ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
	}

	@Override
	public boolean deleteItemStateLogByProjectIdAndSelfIds(Long projectId, String selfIdList) {
		String[] selfIdLists = selfIdList.split(",");
		String sql = "delete from item_state_log where project_id="+projectId+" and self_id=";
		for(int i=0;i<selfIdLists.length;i++){
			if(i==(selfIdLists.length-1)){
				sql=sql+selfIdLists[i];
			}else{
				sql=sql+selfIdLists[i]+" or self_id=";
			}
			
		}
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
