package com.my.spring.DAOImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.AdvancedOrderDao;
import com.my.spring.DAO.AdvancedOrderNewDao;
import com.my.spring.model.AdvancedOrder;
import com.my.spring.model.AdvancedOrderCopy;
import com.my.spring.model.AdvancedOrderNew;
import com.my.spring.model.QuestionCopy;
import com.my.spring.parameters.Parameters;
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
public class AdvancedOrderNewDaoImpl extends BaseDao<AdvancedOrderNew> implements AdvancedOrderNewDao {

    @Override
    public boolean addAdvancedOrderNew(AdvancedOrderNew AdvancedOrder) {
        return save(AdvancedOrder);
    }

    @Override
    public boolean deleteAdvancedOrderNew(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateAdvancedOrderNew(AdvancedOrderNew AdvancedOrder) {
        return update(AdvancedOrder);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<AdvancedOrderNew>> getAdvancedOrderNewList( Integer pageIndex, Integer pageSize, AdvancedOrderNew AdvancedOrder,int adminFlag,String content) {
        DataWrapper<List<AdvancedOrderNew>> retDataWrapper = new DataWrapper<List<AdvancedOrderNew>>();
        List<AdvancedOrderNew> ret = new ArrayList<AdvancedOrderNew>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(AdvancedOrderNew.class);
        criteria.addOrder(Order.desc("createDate"));
       
        if(content!=null){
        	if(Parameters.HasDigit(content)){
        		String nums=Parameters.getNumbers(content);
        		
        		criteria.add(Restrictions.eq("month", Integer.valueOf(nums)));
        		criteria.add(Restrictions.like("projectName", "%"+content.replaceAll("\\d+","")+"%"));
        	}else{
        		criteria.add(Restrictions.like("projectName", "%"+content+"%"));
        	}
        	
        	
        }
        if(AdvancedOrder.getId()!=null){
        	criteria.add(Restrictions.eq("id", AdvancedOrder.getId()));
        }
        if(AdvancedOrder.getSubmitUserId()!=null){
        	criteria.add(Restrictions.eq("submitUserId", AdvancedOrder.getSubmitUserId()));
        }
        if(AdvancedOrder.getMonth()!=null){
        	criteria.add(Restrictions.eq("month", AdvancedOrder.getMonth()));
        }
        if(AdvancedOrder.getConstructPart()!=null){
        	criteria.add(Restrictions.like("constructPart","%"+ AdvancedOrder.getConstructPart()+"%"));
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

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<AdvancedOrderNew>> getAdvancedOrderNewByUserId(Long submitUserId) {
		DataWrapper<List<AdvancedOrderNew>> retDataWrapper = new DataWrapper<List<AdvancedOrderNew>>();
		List<AdvancedOrderNew> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(AdvancedOrderNew.class);
        criteria.add(Restrictions.eq("submitUserId",submitUserId));
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
	public AdvancedOrderNew getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public List<AdvancedOrderCopy> getAdvancedOrderNewListNotRead(Long id, Integer pageSize, Integer pageIndex) {
		if(pageIndex==null){
			pageIndex=1;
		}
		if(pageSize==null){
			pageSize=10;
		}
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
		List<AdvancedOrderCopy> retDataWrapper = new ArrayList<AdvancedOrderCopy>();
		String sql = "select a.id,a.project_name,a.create_date,a.submit_user_id,a.create_user_name,a.construct_part,a.quantity_des,a.month,"
				+"a.project_id,a.status,a.content_files_id from advanced_order a,notice b where a.id=b.about_id and b.user_id="
				+id+" and b.notice_type=3 and b.read_state=0 ORDER BY b.create_date DESC";
		if(pageIndex!=-1){
			sql = sql +" limit "+(pageSize*pageIndex-pageSize)+","+pageSize;
		}
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
		    		.addScalar("id",StandardBasicTypes.LONG)
					 .addScalar("project_name", StandardBasicTypes.STRING)
					 .addScalar("create_date", StandardBasicTypes.TIMESTAMP)
					 .addScalar("submit_user_id", StandardBasicTypes.LONG)
					 .addScalar("create_user_name", StandardBasicTypes.STRING)
					 .addScalar("construct_part", StandardBasicTypes.STRING)
					 .addScalar("quantity_des",StandardBasicTypes.STRING)
					 .addScalar("month", StandardBasicTypes.INTEGER)
					 .addScalar("project_id", StandardBasicTypes.LONG)
					 .addScalar("status", StandardBasicTypes.INTEGER)
					 .addScalar("content_files_id", StandardBasicTypes.STRING)
				 .setResultTransformer(Transformers.aliasToBean(AdvancedOrderCopy.class)); 
		    retDataWrapper=query.list();
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
		return retDataWrapper;
	}

	

	

}
