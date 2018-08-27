package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ConstructionTaskNewDao;
import com.my.spring.model.AllItemData;
import com.my.spring.model.ConstructionTaskNew;
import com.my.spring.model.ConstructionTaskNewUser;
import com.my.spring.model.DuctPojos;
import com.my.spring.model.Folder;
import com.my.spring.model.ItemNodeList;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ConstructionTaskNewDaoImpl extends BaseDao<ConstructionTaskNew> implements ConstructionTaskNewDao {

    @Override
    public boolean addConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew) {
        return save(ConstructionTaskNew);
    }

    @Override
    public boolean deleteConstructionTaskNew(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew) {
        return update(ConstructionTaskNew);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ConstructionTaskNew>> getConstructionTaskNewList( Integer pageIndex, Integer pageSize, ConstructionTaskNew ConstructionTaskNew) {
        DataWrapper<List<ConstructionTaskNew>> retDataWrapper = new DataWrapper<List<ConstructionTaskNew>>();
        List<ConstructionTaskNew> ret = new ArrayList<ConstructionTaskNew>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ConstructionTaskNew.class);
//      criteria.add(Restrictions.eq("projectId", projectId));
        criteria.addOrder(Order.desc("createDate"));
        if(ConstructionTaskNew.getCreateUser()!=null){
        	criteria.add(Restrictions.eq("createUser", ConstructionTaskNew.getCreateUser()));
        }
        if(ConstructionTaskNew.getConstructionTaskDate()!=null){
        	criteria.add(Restrictions.eq("constructionTaskDate", ConstructionTaskNew.getConstructionTaskDate()));
        }
        if(ConstructionTaskNew.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", ConstructionTaskNew.getProjectId()));
        }
        criteria.add(Restrictions.isNull("pid"));
        if(ConstructionTaskNew.getName()!=null){
        	criteria.add(Restrictions.like("name", "%"+ConstructionTaskNew.getName()+"%"));
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
	public ConstructionTaskNew getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public List<ConstructionTaskNewUser> getAboutUsers(Long projectId) {
		List<ConstructionTaskNewUser> dataWrapper=new ArrayList<ConstructionTaskNewUser>();
		String sql = "select approve_user,create_user from item_data where id in"
		+"(select b.item_id from process_data a,process_item b where project_id="+projectId
		+" and type=0 and a.id=b.process_id)";
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("approval_user", StandardBasicTypes.LONG)
					 .addScalar("create_user", StandardBasicTypes.LONG)
					 .setResultTransformer(Transformers.aliasToBean(ConstructionTaskNewUser.class));
			 dataWrapper=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return dataWrapper;
	}

	@Override
	public List<AllItemData> getAllItemData(Long id) {
		List<AllItemData> dataWrapper=new ArrayList<AllItemData>();
		////查询该任务单所对应的流程的所有节点信息
		String sql = "select c.id,a.which,c.name,c.approve_user from process_item a,process_log b,item_data c "
		+"where b.create_date=(select MAX(create_date) from process_log where about_id="+
				id+" and type=0) and a.process_id=b.process_id and a.item_id=c.id ORDER BY which ASC"; 
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id", StandardBasicTypes.LONG)
					 .addScalar("which", StandardBasicTypes.INTEGER)
					 .addScalar("name", StandardBasicTypes.STRING)
					 .addScalar("approve_user",StandardBasicTypes.LONG)
					 .setResultTransformer(Transformers.aliasToBean(AllItemData.class));
			 dataWrapper=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return dataWrapper;
	}

	@Override
	public List<ItemNodeList> getAllItemLog(Long id) {
		List<ItemNodeList> dataWrapper=new ArrayList<ItemNodeList>();
		String sql = "select item_state,current_node,end_flag from process_log where about_id="
		+id+" and type=0 ORDER BY current_node asc";
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("item_state", StandardBasicTypes.INTEGER)
					 .addScalar("current_node", StandardBasicTypes.INTEGER)
					 .addScalar("end_flag",StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(ItemNodeList.class));
			 dataWrapper=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return dataWrapper;
	}

	@Override
	public List<ConstructionTaskNew> getConstructionTaskNewByIds(Long id) {
		List<ConstructionTaskNew> ret = new ArrayList<ConstructionTaskNew>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ConstructionTaskNew.class);
        //criteria.add(Restrictions.eq("fileType", 0));
        
        Disjunction djs = Restrictions.disjunction();
        djs.add(Restrictions.eq("id", id));
        djs.add(Restrictions.eq("pid", id));
        criteria.add(djs);
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
	}	

}
