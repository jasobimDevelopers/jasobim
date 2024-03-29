package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import com.my.spring.DAO.AttenceLogDao;
import com.my.spring.DAO.BaseDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.AttenceForgetFactLogs;
import com.my.spring.model.AttenceLog;
import com.my.spring.model.AttenceLogs;
import com.my.spring.utils.DataWrapper;
@Repository
public class AtttenceLogDaoImpl extends BaseDao<AttenceLog> implements AttenceLogDao {

	@Override
	public boolean addAttenceLog(AttenceLog am) {
		// TODO Auto-generated method stub
		return save(am);
	}

	@Override
	public boolean deleteAttenceLog(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public boolean updateAttenceLog(AttenceLog am) {
		// TODO Auto-generated method stub
		return update(am);
	}
	@Override
	public AttenceLog getAttenceLogById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<AttenceLogs>> getAttenceLogsList(Integer pageIndex, Integer pageSize,AttenceLog am,Integer year,Integer month) {
		// TODO Auto-generated method stub
		/*if(pageIndex==null){
			pageIndex=1;
		}
		if(pageSize==null){
			pageSize=10;
		}*/
		DataWrapper<List<AttenceLogs>> dataWrapper=new DataWrapper<List<AttenceLogs>>();
		String sql = "select e.user_id,sum(e.late_num) as late_nums,sum(e.leave_early_num) as leave_early_nums from" 
		+" (select sum(late) as late_num,sum(leave_early) as leave_early_num,1 as clock_flag,user_id from attence_log "
		+"where (start_work_time is not null and end_work_time is not null) and project_id="+
		+am.getProjectId()+" and create_date BETWEEN '"+String.valueOf(year)+"-"+String.valueOf(month)+"-01"+"' and '"
		+String.valueOf(year)+"-"+String.valueOf(month+1)+"-01"+"' GROUP BY user_id UNION "
		+"select sum(late) as late_num,sum(leave_early) as leave_early_num,2 as clock_flag,user_id from attence_log"
		+" where (start_work_time is null or end_work_time is null) and project_id="
		+am.getProjectId()+" and create_date BETWEEN '"+String.valueOf(year)+"-"+String.valueOf(month)+"-01"
		+"' and '"+String.valueOf(year)+"-"+String.valueOf(month+1)+"-01"+"' GROUP BY user_id) e GROUP BY e.user_id";
		
		/*if(pageIndex!=-1){
			sql = sql +" limit "+(pageSize*pageIndex-pageSize)+","+pageSize;
		}*/
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("user_id", StandardBasicTypes.LONG)
					 .addScalar("late_nums", StandardBasicTypes.INTEGER)
					 .addScalar("leave_early_nums", StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(AttenceLogs.class));
			 dataWrapper.setData(query.list());
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
	    
       /* result.setData(ret);
        result.setTotalNumber(totalItemNum);
        result.setCurrentPage(pageIndex);
        result.setTotalPage(totalPageNum);
        result.setNumberPerPage(pageSize);*/
        return dataWrapper;
	}
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<AttenceLogs>> getAttenceLogsList(Integer pageIndex, Integer pageSize,AttenceLog am,String start,String end) {
		DataWrapper<List<AttenceLogs>> dataWrapper=new DataWrapper<List<AttenceLogs>>();
		String sql = "select e.user_id,sum(e.late_num) as late_nums,sum(e.leave_early_num) as leave_early_nums from" 
		+" (select sum(late) as late_num,sum(leave_early) as leave_early_num,1 as clock_flag,user_id from attence_log "
		+"where (start_work_time is not null and end_work_time is not null) and project_id="+
		+am.getProjectId()+" and user_id="+am.getUserId()+" and create_date BETWEEN '"+start+"' and '"
		+end+"' GROUP BY user_id UNION "
		+"select sum(late) as late_num,sum(leave_early) as leave_early_num,2 as clock_flag,user_id from attence_log"
		+" where (start_work_time is null or end_work_time is null) and project_id="
		+am.getProjectId()+" and user_id="+am.getUserId()+" and create_date BETWEEN '"+start
		+"' and '"+end+"' GROUP BY user_id) e GROUP BY e.user_id";
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("user_id", StandardBasicTypes.LONG)
					 .addScalar("late_nums", StandardBasicTypes.INTEGER)
					 .addScalar("leave_early_nums", StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(AttenceLogs.class));
			 dataWrapper.setData(query.list());
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
        return dataWrapper;
	}


	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<AttenceLogs>> getAttenceLogsUserList(Integer pageIndex, Integer pageSize,AttenceLog am,Integer year,Integer month) {
		DataWrapper<List<AttenceLogs>> dataWrapper=new DataWrapper<List<AttenceLogs>>();
		String sql = "select DATE_FORMAT(a.create_date,'%Y-%m') as month,a.* from attence_log a "
		+" where project_id="+am.getProjectId()+" group by user_id";
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("month", StandardBasicTypes.DATE)
					 .addScalar("id", StandardBasicTypes.LONG)
					 .addScalar("start_work_time", StandardBasicTypes.STRING)
					 .addScalar("end_work_time", StandardBasicTypes.STRING)
					 .addScalar("user_id", StandardBasicTypes.LONG)
					 .addScalar("project_id", StandardBasicTypes.LONG)
					 .addScalar("late", StandardBasicTypes.INTEGER)
					 .addScalar("lat", StandardBasicTypes.DOUBLE)
					 .addScalar("lng", StandardBasicTypes.DOUBLE)
					 .addScalar("attence_model_id", StandardBasicTypes.LONG)
					 .addScalar("create_date", StandardBasicTypes.DATE)
					 .addScalar("clock_flag", StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(AttenceLogs.class));
			 dataWrapper.setData(query.list());
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
        return dataWrapper;
	}
	@Override
	public DataWrapper<Void> deleteAttenceLogByProjectId(Long id) {
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		String sql="delete * from attence_log where project_id="+id;
		Session session=getSession();
		 try{
	            @SuppressWarnings("unused")
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
	public DataWrapper<List<AttenceLog>> getAttenceLogList(Integer pageIndex, Integer pageSize, AttenceLog am) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AttenceLog getAttenceLogByInfos(Long id, Date nowDate, Long projectId) {
        List<AttenceLog> ret = new ArrayList<AttenceLog>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(AttenceLog.class);
    	if(id!=null){
        	criteria.add(Restrictions.eq("userId", id));
        }
       if(nowDate!=null){
        	criteria.add(Restrictions.eq("createDate",nowDate));
        }
        if(projectId!=null){
        	criteria.add(Restrictions.eq("projectId", projectId));
        }
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

	@SuppressWarnings("unchecked")
	@Override
	public AttenceLog getAttenceLogListByIds(AttenceLog ps) {
		 List<AttenceLog> ret = new ArrayList<AttenceLog>();
	        Session session = getSession();
	        Criteria criteria = session.createCriteria(AttenceLog.class);
	        if(ps!=null){
	        	if(ps.getUserId()!=null){
		        	criteria.add(Restrictions.eq("userId", ps.getUserId()));
		        }
	        	
		        if(ps.getProjectId()!=null){
		        	criteria.add(Restrictions.eq("projectId", ps.getProjectId()));
		        }
		        
		        if(ps.getCreateDate()!=null){
		        	criteria.add(Restrictions.eq("createDate", ps.getCreateDate()));
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
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<AttenceForgetFactLogs>> getForgetFactNumsList(Integer pageIndex, Integer pageSize,AttenceLog am,Integer year,Integer month) {
		DataWrapper<List<AttenceForgetFactLogs>> dataWrapper=new DataWrapper<List<AttenceForgetFactLogs>>();
		String sql = "select e.user_id,sum(e.fact_num) as fact_nums,sum(e.forget_num) as forget_nums from "
		+"(select count(1) as fact_num ,0 as forget_num,1 as clock_flag,user_id from attence_log" 
	    +" where (start_work_time is not null and end_work_time is not null) and project_id="+
		+am.getProjectId()+" and create_date BETWEEN '"+String.valueOf(year)+"-"+String.valueOf(month)+"-01"+"' and '"
		+String.valueOf(year)+"-"+String.valueOf(month+1)+"-01"+"' GROUP BY user_id"
		+" UNION select 0 as fact_num,count(1) as forget_num,2 as clock_flag,user_id from attence_log"
		+" where (start_work_time is null or end_work_time is null) and project_id="
		+am.getProjectId()+" and create_date BETWEEN '"+String.valueOf(year)+"-"+String.valueOf(month)+"-01"
		+"' and '"+String.valueOf(year)+"-"+String.valueOf(month+1)+"-01"+"' GROUP BY user_id) e GROUP BY e.user_id";
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("forget_nums", StandardBasicTypes.INTEGER)
					 .addScalar("fact_nums", StandardBasicTypes.INTEGER)
					 .addScalar("user_id", StandardBasicTypes.LONG)
					 .setResultTransformer(Transformers.aliasToBean(AttenceForgetFactLogs.class));
			 dataWrapper.setData(query.list());
	        }catch(Exception e){
	            e.printStackTrace();
	        }
        return dataWrapper;
	}
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<AttenceForgetFactLogs>> getForgetFactNumsList(Integer pageIndex, Integer pageSize,AttenceLog am,String start,String end) {
		DataWrapper<List<AttenceForgetFactLogs>> dataWrapper=new DataWrapper<List<AttenceForgetFactLogs>>();
		String sql = "select e.user_id,sum(e.fact_num) as fact_nums,sum(e.forget_num) as forget_nums from "
		+"(select count(1) as fact_num ,0 as forget_num,1 as clock_flag,user_id from attence_log" 
	    +" where (start_work_time is not null and end_work_time is not null) and project_id="+
		+am.getProjectId()+" and user_id="+am.getUserId()+" and create_date BETWEEN '"+start+"' and '"
		+end+"' GROUP BY user_id"
		+" UNION select 0 as fact_num,count(1) as forget_num,2 as clock_flag,user_id from attence_log"
		+" where (start_work_time is null or end_work_time is null) and project_id="
		+am.getProjectId()+" and user_id="+am.getUserId()+" and create_date BETWEEN '"+start
		+"' and '"+end+"' GROUP BY user_id) e GROUP BY e.user_id";
		
		/*if(pageIndex!=-1){
			sql = sql +" limit "+(pageSize*pageIndex-pageSize)+","+pageSize;
		}*/
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("forget_nums", StandardBasicTypes.INTEGER)
					 .addScalar("fact_nums", StandardBasicTypes.INTEGER)
					 .addScalar("user_id", StandardBasicTypes.LONG)
					 .setResultTransformer(Transformers.aliasToBean(AttenceForgetFactLogs.class));
			 dataWrapper.setData(query.list());
	        }catch(Exception e){
	            e.printStackTrace();
	        }
        return dataWrapper;
	}

}
