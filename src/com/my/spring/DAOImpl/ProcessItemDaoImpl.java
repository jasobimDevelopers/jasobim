package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProcessItemDao;
import com.my.spring.model.AllItemData;
import com.my.spring.model.ItemIdMode;
import com.my.spring.model.ProcessItem;
import com.my.spring.model.ProcessLog;
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
public class ProcessItemDaoImpl extends BaseDao<ProcessItem> implements ProcessItemDao {

    @Override
    public boolean addProcessItem(ProcessItem ProcessItem) {
        return save(ProcessItem);
    }

    @Override
    public boolean deleteProcessItem(Long id) {
        return delete(get(id));
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ProcessItem>> getProcessItemList( Integer pageIndex, Integer pageSize, ProcessItem ProcessItem) {
        DataWrapper<List<ProcessItem>> retDataWrapper = new DataWrapper<List<ProcessItem>>();
        List<ProcessItem> ret = new ArrayList<ProcessItem>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProcessItem.class);
//      criteria.add(Restrictions.eq("projectId", projectId));
       
        if(ProcessItem.getProcessId()!=null){
        	criteria.add(Restrictions.eq("processId", ProcessItem.getProcessId()));
        }
        if(ProcessItem.getItemId()!=null){
        	criteria.add(Restrictions.eq("itemId", ProcessItem.getItemId()));
        }
        if(ProcessItem.getWhich()!=null){
        	criteria.add(Restrictions.eq("which", ProcessItem.getWhich()));
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

	

	@Override
	public ProcessItem getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	@Override
	public ProcessItem findProcessItem(ProcessLog pl) {
		ProcessItem retDataWrapper = new ProcessItem();
	        
	        //ret=find("select * from User where userId=?"+userId);
		List<ProcessItem> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProcessItem.class);
        if(pl.getCurrentNode()!=null){
        	 criteria.add(Restrictions.eq("which", pl.getCurrentNode()));
        }
        if(pl.getProcessId()!=null){
        	criteria.add(Restrictions.eq("processId", pl.getProcessId()));
        }
       
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			retDataWrapper=ret.get(0);
		}
		return retDataWrapper;
	}

	@Override
	public ItemIdMode getProcessItemByNode(Integer currentNode, Long processDataId) {
		List<ItemIdMode> ret =null;
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
		String sql = "select item_id from process_item where process_id="
		+processDataId+" and which="
		+currentNode
		+" and id=(select MAX(id) as id from process_item where process_id="
		+processDataId
		+" and which="
		+currentNode
		+") ";
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
				 .addScalar("item_id",StandardBasicTypes.LONG)
				 .setResultTransformer(Transformers.aliasToBean(ItemIdMode.class)); 
		    ret=query.list();
            
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
	    if(!ret.isEmpty()){
	    	return ret.get(0);
	    }
		return null;
	}

	@Override
	public List<ProcessItem> getProcessItemByProcessId(Long id) {
		ProcessItem retDataWrapper = new ProcessItem();
        
        //ret=find("select * from User where userId=?"+userId);
		List<ProcessItem> ret = null;
	    Session session = getSession();
	    Criteria criteria = session.createCriteria(ProcessItem.class);
	    criteria.addOrder(Order.asc("which"));
	    if(id!=null){
	    	criteria.add(Restrictions.eq("processId", id));
	    }
	   
	    try {
	        ret = criteria.list();
	    }catch (Exception e){
	        e.printStackTrace();
	    }
		return ret;
	}

	@Override
	public List<AllItemData> getProcessItemListByProcessId(Long processDataId) {
		List<AllItemData> ret =null;
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
		String sql = "select a.id,a.name,a.approve_user,b.which from item_data a,(select item_id,which from process_item where process_id="
		+processDataId+") b where a.id=b.item_id ORDER BY b.which asc";
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
		    		 .addScalar("id", StandardBasicTypes.LONG)
					 .addScalar("which", StandardBasicTypes.INTEGER)
					 .addScalar("name", StandardBasicTypes.STRING)
					 .addScalar("approve_user",StandardBasicTypes.LONG)
					 .setResultTransformer(Transformers.aliasToBean(AllItemData.class));
		    ret=query.list();
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
	   
		return ret;
	}
		
	

	

	

}
