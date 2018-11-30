package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import com.my.spring.DAO.MechanicPriceDao;
import com.my.spring.DAO.BaseDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MechanicPrice;
import com.my.spring.model.MechanicPriceNum;
import com.my.spring.model.MechanicDataOfHour;
import com.my.spring.model.MechanicDataPeople;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
@Repository
public class MechanicPriceDaoImpl extends BaseDao<MechanicPrice> implements MechanicPriceDao {

	@Override
	public boolean addMechanicPrice(MechanicPrice am) {
		// TODO Auto-generated method stub
		return save(am);
	}

	@Override
	public boolean deleteMechanicPrice(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	
	@Override
	public boolean updateMechanicPrice(MechanicPrice am) {
		// TODO Auto-generated method stub
		return update(am);
	}
	@Override
	public MechanicPrice getMechanicPriceById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	
	@Override
	public DataWrapper<Void> deleteMechanicPriceByMechanicId(Long id) {
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		String sql="delete * from attence_log where project_id="+id;
		Session session=getSession();
		 try{
	            Query query=session.createSQLQuery(sql);
	            session.getTransaction().commit();
	            session.flush();
	        }catch(Exception e){
	            e.printStackTrace();
	            session.getTransaction().rollback();
	            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<MechanicPrice>> getMechanicPriceList(Integer pageIndex, Integer pageSize, MechanicPrice am) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public MechanicPrice getMechanicPriceLists(Integer pageIndex, Integer pageSize, MechanicPrice am) {
		List<MechanicPrice> retDataWrapper = new ArrayList<MechanicPrice>();
        List<MechanicPrice> ret = new ArrayList<MechanicPrice>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MechanicPrice.class);
        criteria.addOrder(Order.desc("createDate"));
        
        if(am.getCreateDate()!=null){
        	criteria.add(Restrictions.eq("createDate", am.getCreateDate()));
        }
        if(am.getMechanicId()!=null){
        	criteria.add(Restrictions.eq("mechanicId", am.getMechanicId()));
        }
        if(am.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", am.getProjectId()));
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
        /*retDataWrapper.setData(ret);
        retDataWrapper.setTotalNumber(totalItemNum);
        retDataWrapper.setCurrentPage(pageIndex);
        retDataWrapper.setTotalPage(totalPageNum);
        retDataWrapper.setNumberPerPage(pageSize);*/
        if(ret!=null){
        	if(ret.size()>0){
        		return ret.get(0);
        	}
        }
        return null;
	}

	@Override
	public boolean addMechanicPriceList(List<MechanicPrice> am) {
		// TODO Auto-generated method stub
		return saveList(am);
	}

	@Override
	public List<MechanicPrice> getMechanicPriceListByMechanicId(Integer pageIndex, Integer pageSize, Long mechanicId,Date date1,Date date2) {
        List<MechanicPrice> ret = new ArrayList<MechanicPrice>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MechanicPrice.class);
        criteria.add(Restrictions.ge("createDate",date1)); 
        criteria.add(Restrictions.le("createDate",date2));
        criteria.addOrder(Order.desc("createDate"));
        if(mechanicId!=null){
        	criteria.add(Restrictions.eq("mechanicId",mechanicId));
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
      
        return ret;
	}

	@Override
	public List<MechanicPriceNum> getMechanicPriceNumByProjectId(String startday, String endday, Long projectId) {
		List<MechanicPriceNum> ret = new ArrayList<MechanicPriceNum>();
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
		String sql = "select b.id,b.real_name,b.work_name,a.num,b.day_salary,b.project_id from (select sum(hour) as num,mechanic_id from mechanic_price where create_date BETWEEN '"+
				startday+"' and '"+endday+"' GROUP BY mechanic_id) a,mechanic b where a.mechanic_id=b.id and b.project_id="+projectId;
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
				 .addScalar("id",StandardBasicTypes.LONG)
				 .addScalar("real_name", StandardBasicTypes.STRING)
				 .addScalar("work_name",StandardBasicTypes.STRING)
				 .addScalar("num",StandardBasicTypes.INTEGER)
				 .addScalar("day_salary", StandardBasicTypes.INTEGER)
				 .addScalar("project_id", StandardBasicTypes.LONG)
				 .setResultTransformer(Transformers.aliasToBean(MechanicPriceNum.class)); 
		    ret=query.list();
            
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }

		return ret;
	}

	@Override
	public MechanicPrice getMechanicPriceListByInfos(Long id, Long projectId, Date format) {
		 List<MechanicPrice> ret = new ArrayList<MechanicPrice>();
	        Session session = getSession();
	        Criteria criteria = session.createCriteria(MechanicPrice.class);
	        if(id!=null){
	        	if(id!=null){
		        	criteria.add(Restrictions.eq("mechanicId", id));
		        }
	        	
		        if(projectId!=null){
		        	criteria.add(Restrictions.eq("projectId", projectId));
		        }
		        
		        if(format!=null){
		        	criteria.add(Restrictions.eq("createDate", format));
		        }
		        try {
		            ret = criteria.list();
		        }catch (Exception e){
		            e.printStackTrace();
		        }
		        if (ret != null && ret.size() > 0) {
					return ret.get(0);
				}
	        }
			return null;
	}

	@Override
	public List<MechanicDataOfHour> getMechanicHourByProjectId(String startday, String endday, Long projectId) {
		List<MechanicDataOfHour> ret = new ArrayList<MechanicDataOfHour>();
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
		String sql = "select create_date,sum(hour) as hours from mechanic_price where project_id="+
		projectId+" and create_date BETWEEN '"+startday+"' and '"+endday+"' GROUP BY create_date";
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
				 .addScalar("create_date",StandardBasicTypes.DATE)
				 .addScalar("hours", StandardBasicTypes.INTEGER)
				 .setResultTransformer(Transformers.aliasToBean(MechanicDataOfHour.class)); 
		    ret=query.list();
            
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }

		return ret;
	}
	@Override
	public List<MechanicDataPeople> getMechanicPeopleByProjectId(String startday, String endday, Long projectId) {
		List<MechanicDataPeople> ret = new ArrayList<MechanicDataPeople>();
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
		String sql = "select create_date,count(*) as people_num from mechanic_price where project_id="+projectId
				+" and create_date BETWEEN '"+startday+"' and '"+endday+"' GROUP BY create_date";
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
				 .addScalar("create_date",StandardBasicTypes.DATE)
				 .addScalar("people_num", StandardBasicTypes.INTEGER)
				 .setResultTransformer(Transformers.aliasToBean(MechanicDataPeople.class)); 
		    	ret=query.list();
        }catch(Exception e){
            e.printStackTrace();
            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        }
		return ret;
	}

}
