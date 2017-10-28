package com.my.spring.DAOImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.ParameterMode;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.UserLogDao;
import com.my.spring.model.DuctPojos;
import com.my.spring.model.UserLog;
import com.my.spring.model.UserLogPojos;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;


@Repository
public class UserLogDaoImpl extends BaseDao<UserLog> implements UserLogDao {

	
	@Override
	public boolean addUserLog(UserLog userLog) {
		// TODO Auto-generated method stub
		return save(userLog);
	}

	@Override
	public boolean addUserLogList(List<UserLog> userLogList) {
		// TODO Auto-generated method stub
		return saveList(userLogList);
	}

	@Override
	public UserLog getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}


	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<UserLog>> getUserLogList(Integer pageSize, Integer pageIndex,UserLog UserLog,Date startDate,Date finishedDate) {
		// TODO Auto-generated method stub
		DataWrapper<List<UserLog>> dataWrapper = new DataWrapper<List<UserLog>>();
        List<UserLog> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(UserLog.class);
        criteria.addOrder(Order.desc("actionDate"));
        if(UserLog!=null){
        	if(UserLog.getUserId()!=null){
        		criteria.add(Restrictions.eq("userId", UserLog.getUserId()));
        	}
        	
        	if(UserLog.getActionDate()!=null)    
            	//查询制定时间之后的记录  
                criteria.add(Restrictions.ge("actionDate",UserLog.getActionDate()));  
    	    /*if(UserLog.getActionDate()!=null)                          //查询指定时间之前的记录  
    	        criteria.add(Restrictions.le("actionDate",UserLog.getActionDate()));*/  
        	if(UserLog.getProjectId()!=null){
        		criteria.add(Restrictions.eq("projectId",UserLog.getProjectId()));
        	}
        	if(UserLog.getProjectPart()!=null){
        		criteria.add(Restrictions.eq("projectPart", UserLog.getProjectPart()));
        	}
        	if(UserLog.getSystemType()!=null){
        		criteria.add(Restrictions.eq("systemType", UserLog.getSystemType()));
        	}
        }
        if(startDate!=null)    
        	//查询制定时间之后的记录  
            criteria.add(Restrictions.ge("actionDate",startDate));  
	    if(finishedDate!=null)                          //查询指定时间之前的记录  
	        criteria.add(Restrictions.le("actionDate",finishedDate));  
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
        dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);

        return dataWrapper;
	}


	@Override
	public boolean deleteUserLog(String[] id) {
		// TODO Auto-generated method stub
		boolean bool=false;
		for(int i=0;i<id.length;i++){
			bool=delete(get(Long.valueOf(id[i])));
		}
		return bool;
	}
	@Override
	public String exportUserLog(String dateStart,String dateFinished) {
		// TODO Auto-generated method stub
		
		Session session=getSession();
		String result = "";
		try{

			String sql="select A.real_name,A.name as project_part_name,A.version,A.num,A.system_name,B.name as projectName,DATE_FORMAT(A.action_date,'%Y-%m-%d %H:%i:%s')"
					+" from (select b.real_name,d.name,a.version,a.num,e.system_name,a.project_id,a.action_date from (select *,count(*) as"
					+" num from user_log where 1=1";
			if(dateStart != null) {
				sql += " and action_date >= " + dateStart;
			}
			if(dateFinished != null) {
				sql += " and action_date <= " + dateFinished;
			}
			sql +=	" GROUP BY user_id,project_id,system_type,version,project_part,project_id) a,"
					+"user b,project_part_name d,phone_system e where a.user_id=b.id and a.project_part=d.project_part"
					+" and b.user_type=3 and a.system_type=e.system_type) A left join  project B on A.project_id=B.id";
			Query query = session.createSQLQuery(sql);
			List<Object[]> list = query.list();
			for(Object[] o : list) {
				result +=  o[0] +"," + o[1] +"," + o[2]+","+o[3]+","+o[4]+","+o[5]+","+o[6]+"\n";
			}
//			ProcedureCall procedureCall = session.createStoredProcedureCall("exportUserLog");
//			String starttime="";
//			String finishedtime="";
//			
//			if(dateStart!=null){
//	    		starttime=dateStart;
//			}
//			if(dateFinished!=null){
//				finishedtime=dateFinished;
//			}
//			procedureCall.registerParameter("file_path", String.class, ParameterMode.IN).bindValue(filePath);
//			procedureCall.registerParameter("date_start", String.class, ParameterMode.IN).bindValue(starttime);
//			procedureCall.registerParameter("date_finished", String.class, ParameterMode.IN).bindValue(finishedtime);;
//			procedureCall.getOutputs();
	    }catch(Exception e){
	        e.printStackTrace();
	        return null;
	    }
		return result;
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<UserLogPojos>> getUserLogLists(String startTime,String finishedTime,Long projectId,Integer projectPart,Integer systemType) {
		DataWrapper<List<UserLogPojos>> dataWrapper=new DataWrapper<List<UserLogPojos>>();
		String sql="";
		String sql0 = "select count(a.id) as num,a.project_part as projectPart"
				+",a.system_type as systemType,b.real_name as userName,a.action_date as actionDate,"
				+"a.version,a.project_id as projectName";
		String sql3 = ",DATE_FORMAT(a.action_date,'%Y-%m') as date";
		String sql_time= ",DATE_FORMAT(a.action_date,'%Y-%m-%d') as date";
		String sql4=" from user_log a,user b where a.user_id=b.id";
		String sql2=" group by project_part,date";
		sql=sql0+sql3+sql4;
		if(startTime!=null){
			sql=sql0+sql_time+sql4;
			sql=sql+" and a.action_date>"+"'"+startTime+"'";
		}
		if(finishedTime!=null){
			if(startTime!=null){
				sql=sql+" and a.action_date<"+"'"+finishedTime+"'";
			}else{
				sql=sql0+sql_time+sql4;
				sql=sql+" and a.action_date<"+"'"+finishedTime+"'";
			}
		}
		if(projectId!=null){
			sql=sql+" and a.project_id="+projectId;
		}
		if(projectPart!=null){
			sql=sql+" and a.project_part="+projectPart;
		}
		if(systemType!=null){
			sql=sql+" and a.system_type="+systemType;
		}
		sql=sql+sql2;
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("date", StandardBasicTypes.STRING)
					 .addScalar("num", StandardBasicTypes.LONG)
					 .addScalar("projectPart", StandardBasicTypes.STRING)
					 .addScalar("systemType", StandardBasicTypes.STRING)
					 .addScalar("userName", StandardBasicTypes.STRING)
					 .addScalar("actionDate", StandardBasicTypes.STRING)
					 .addScalar("version", StandardBasicTypes.STRING)
					 .addScalar("projectName", StandardBasicTypes.STRING)
					 .setResultTransformer(Transformers.aliasToBean(UserLogPojos.class));
			 dataWrapper.setData(query.list());
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return dataWrapper;
	}

	@Override
	public boolean loadUserLogFile(String fileUrl) {
		String sql="load data infile "+"'"+fileUrl+"'"+" ignore into table user_log character set utf-8 fields terminated by ',' lines terminated by '/r/n' ";
		Session session=getSession();
		try{
			 Query query = session.createSQLQuery(sql);
			 System.out.println(query.getComment());
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		return true;
	}

}
