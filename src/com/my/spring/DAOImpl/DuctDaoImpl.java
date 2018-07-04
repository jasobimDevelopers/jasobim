package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.DuctDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Duct;
import com.my.spring.model.DuctApp;
import com.my.spring.model.DuctFloorInfo;
import com.my.spring.model.DuctPojos;
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
import java.util.Date;
import java.util.List;


@Repository
public class DuctDaoImpl extends BaseDao<Duct> implements DuctDao {

    @Override
    public boolean addDuct(Duct Duct) {
        return save(Duct);
    }

    @Override
    public boolean deleteDuct(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateDuct(Duct Duct) {
        return update(Duct);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Duct>> getDuctList(Integer pageSize,Integer pageIndex,Duct duct,String content,Date dateStart,Date dateFinished) {
    	DataWrapper<List<Duct>> retDataWrapper = new DataWrapper<List<Duct>>();
        List<Duct> ret = new ArrayList<Duct>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Duct.class);
        criteria.addOrder(Order.desc("date"));
        
        if(duct.getState()!=null){
        	criteria.add(Restrictions.eq("state", duct.getState()));
        }
        if(duct.getId()!=null){
        	criteria.add(Restrictions.eq("id", duct.getId()));
        }
        if(duct.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", duct.getProjectId()));
        }
        if(duct.getName()!=null){
        	criteria.add(Restrictions.like("name", "%"+duct.getName()+"%"));
        }
        if(duct.getUserId()!=null){
        	criteria.add(Restrictions.eq("userId", duct.getUserId()));
        }
        if(content!=null){
        	criteria.add(Restrictions.like("name", "%"+content+"%"));
        }
        if(duct.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", duct.getProjectId()));
        }	
        if(dateStart!=null)    
        	//查询制定时间之后的记录  
            criteria.add(Restrictions.ge("date",dateStart));  
	    if(dateFinished!=null)                          //查询指定时间之前的记录  
	        criteria.add(Restrictions.le("date",dateFinished));  
        /////////////////////////////////////
        //criteria.setProjection(Projections.groupProperty("state").as("num"));
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
	public DataWrapper<List<Duct>> getDuctByProjectId(Long projectId,Duct duct) {
		DataWrapper<List<Duct>> dataWrapper=new DataWrapper<List<Duct>>();
		List<Duct> ret = new ArrayList<Duct>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Duct.class);
        if(projectId!=null){
        	criteria.add(Restrictions.eq("projectId",projectId));
        }
        if(duct.getName()!=null){
        	criteria.add(Restrictions.like("name","%"+duct.getName()+"%"));
        }
        if(duct.getId()!=null){
        	criteria.add(Restrictions.eq("id",duct.getId()));
        }
        if(duct.getSelfId()!=null){
        	criteria.add(Restrictions.eq("selfId",duct.getSelfId()));
        }
        if(duct.getModelFlag()!=null){
        	criteria.add(Restrictions.eq("modelFlag",duct.getModelFlag()));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
        	dataWrapper.setData(ret);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
		}
		return dataWrapper;
	}
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<Duct> getDuctBySelfId(Long selfId,String id,Long projectId) {
		DataWrapper<Duct> dataWrapper=new DataWrapper<Duct>();
		List<Duct> ret = new ArrayList<Duct>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Duct.class);
        if(selfId==null && (id==null && projectId==null)){
        	return dataWrapper;
        }
        if(selfId!=null){
        	criteria.add(Restrictions.eq("id",selfId));
        }
        if(id!=null){
        	criteria.add(Restrictions.eq("selfId",id));
        }
        if(projectId!=null){
        	criteria.add(Restrictions.eq("projectId",projectId));
        }
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
	public boolean addDuctList(List<Duct> ductList) {
		return saveList(ductList);
	}
	
	/*
	 * 根据时间段和项目id导出预制化表单
	 * @see com.my.spring.DAO.DuctDao#exportDuct(java.lang.String, java.lang.Long, java.util.Date, java.util.Date)
	 */
	
	@Override
	public String exportDuct(Long projectId,String dateStart,String dateFinished) {
		// TODO Auto-generated method stub
		Session session = getSession();
		String result="";
		try{

			String sql="select b.id,b.name,count(*) as value,sum(length) as length,sum(area) as area,b.project_id,b.building_num,b.family_and_type,"
						+"b.size,a.name as project_name,b.date from duct b left join project a on a.id=b.project_id where b.project_id="+projectId;
						
			if(dateStart != null) {
				sql += " and b.date >= " + dateStart;
			}
			if(dateFinished != null) {
				sql += " and b.date <= " + dateFinished;
			}
			sql +=" group by size,family_and_type order by b.date desc";
			Query query = session.createSQLQuery(sql);
			List<Object[]> list = query.list();
			for(Object[] o : list) {
				result +=  o[0] +"," + o[1] +"," + o[2]+","+o[3]+","+o[4]+","+o[5]+","+o[6]+","+o[7]+","+o[8]+","+o[9]+","+o[10]+"\n";
			}
		}catch(Exception e){
	        e.printStackTrace();
	        return null;
	    }
		/*Session session=getSession();
		try{
			ProcedureCall procedureCall = session.createStoredProcedureCall("exportDuct");
			String starttime="";
			String finishedtime="";
			
			if(dateStart!=null){
	    		starttime=dateStart;
			}
			if(dateFinished!=null){
				finishedtime=dateFinished;
			}
			procedureCall.registerParameter("file_path", String.class, ParameterMode.IN).bindValue(filePath);
			procedureCall.registerParameter("project_id", Long.class, ParameterMode.IN).bindValue(projectId);
			procedureCall.registerParameter("date_start", String.class, ParameterMode.IN).bindValue(starttime);;
			procedureCall.registerParameter("date_finished", String.class, ParameterMode.IN).bindValue(finishedtime);;
			procedureCall.getOutputs();
	    }catch(Exception e){
	        e.printStackTrace();
	        return false;
	    }*/
		
		return result;
	}

	@Override
	public Duct getDuctById(Long id) {
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<DuctPojos>> getDuctLists() {
		DataWrapper<List<DuctPojos>> dataWrapper=new DataWrapper<List<DuctPojos>>();
		String sql = "select DATE_FORMAT(date,'%Y-%m') as month,count(date) as sum_date,state  from duct group by month,state";
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("month", StandardBasicTypes.STRING)
					 .addScalar("sum_date", StandardBasicTypes.LONG)
					 .addScalar("state", StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(DuctPojos.class));
			 dataWrapper.setData(query.list());
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<DuctPojos>> getDuctLists(String dateStart, String dateFinished, Duct duct, String token,
			String content) {
		DataWrapper<List<DuctPojos>> dataWrapper=new DataWrapper<List<DuctPojos>>();
		String selsql="select * from duct";
		int i=0;
		if(dateStart!=null){
			selsql+=selsql+" where date>"+dateStart;
			i++;
		}
		if(dateFinished!=null){
			if(i>=1){
				selsql=selsql+" and date<"+dateFinished;
			}else{
				selsql=selsql+" where date<"+dateFinished;
			}
			i++;
		}
		
		if(content!=null && !content.equals("")){
			if(i>=1){
				selsql=selsql+" and name like "+"'"+content+"'";
			}else{
				selsql=selsql+" where name like "+"'"+content+"'";
			}
			i++;
		}
		if(duct.getProjectId()!=null){
			if(i>=1){
				selsql=selsql+" and project_id="+duct.getProjectId();
			}else{
				selsql=selsql+" where project_id="+duct.getProjectId();
			}
			i++;
		}
		if(duct.getName()!=null && !duct.getName().equals("")){
			if(i>=1){
				selsql=selsql+" and name="+"'"+duct.getName()+"'";
			}else{
				selsql=selsql+" where name="+"'"+duct.getName()+"'";
			}
			i++;
		}
		if(duct.getState()!=null && !duct.getState().equals("")){
			if(i>=1){
				selsql=selsql+" and state="+duct.getState();
			}else{
				selsql=selsql+" where state="+duct.getState();
			}
			i++;
		}
		String sql = "select DATE_FORMAT(date,'%Y-%m') as month,count(date) as sum_date,state  from"+ "("+selsql+")"+"a group by month,state";
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("month", StandardBasicTypes.STRING)
					 .addScalar("sum_date", StandardBasicTypes.LONG)
					 .addScalar("state", StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(DuctPojos.class));
			 dataWrapper.setData(query.list());
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return dataWrapper;
	}

	@Override
	public List<DuctApp> getDuctNumsOfApp(Long projectId, Integer floorNum, Integer state, Integer professionType) {
		List<DuctApp> ret=new ArrayList<DuctApp>();
		String sql = "select id,name,size,service_type,profession_type,family_and_type,self_id,date,state,count(*) as nums,"
		+"sum(area) as areas,sum(length) as lengths from duct where project_id="+projectId;
		if(floorNum!=null){
			sql=sql+" and floor_num="+floorNum;
		}
		if(state!=null){
			sql=sql+" and state="+state;
		}
		if(professionType!=null){
			sql=sql+" and profession_type="+professionType;
		}
		sql=sql+" GROUP BY state,name,service_type,family_and_type,size";
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id", StandardBasicTypes.LONG)
					 .addScalar("name", StandardBasicTypes.STRING)
					 .addScalar("size", StandardBasicTypes.STRING)
					 .addScalar("service_type", StandardBasicTypes.STRING)
					 .addScalar("family_and_type", StandardBasicTypes.STRING)
					 .addScalar("profession_type", StandardBasicTypes.INTEGER)
					 .addScalar("self_id", StandardBasicTypes.STRING)
					 .addScalar("date", StandardBasicTypes.DATE)
					 .addScalar("state", StandardBasicTypes.INTEGER)
					 .addScalar("nums", StandardBasicTypes.INTEGER)
					 .addScalar("areas", StandardBasicTypes.DOUBLE)
					 .addScalar("lengths", StandardBasicTypes.DOUBLE)
					 .setResultTransformer(Transformers.aliasToBean(DuctApp.class));
			  	ret=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return ret;
	}

	@Override
	public List<DuctFloorInfo> getFloorNumInfo(Long projectId) {
		List<DuctFloorInfo> ret=new ArrayList<DuctFloorInfo>();
		String sql = "select floor_num,model_flag from duct where project_id="+projectId+" GROUP BY floor_num";
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("floor_num", StandardBasicTypes.INTEGER)
					 .addScalar("model_flag", StandardBasicTypes.STRING)
					 .setResultTransformer(Transformers.aliasToBean(DuctFloorInfo.class));
			  	ret=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return ret;
	}


}
