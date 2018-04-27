package com.my.spring.DAOImpl;

import java.util.ArrayList;
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

import com.my.spring.DAO.AttenceLogDao;
import com.my.spring.DAO.BaseDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.AttenceLog;
import com.my.spring.model.AttenceLogs;
import com.my.spring.model.Department;
import com.my.spring.model.DuctPojos;
import com.my.spring.utils.DaoUtil;
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

	@Override
	public DataWrapper<List<AttenceLogs>> getAttenceLogsList(Integer pageIndex, Integer pageSize,AttenceLog am,Integer year,Integer month) {
		// TODO Auto-generated method stub
		DataWrapper<List<AttenceLogs>> dataWrapper=new DataWrapper<List<AttenceLogs>>();
		String sql = "select b.* from (select DATE_FORMAT(a.create_date,'%Y-%m') as month,a.* from attence_log a ) b"
		+ " where b.month BETWEEN "+(year+"-"+month)+" and "+(year+"-"+month+1); 
		if(am.getProjectId()!=null){
			sql=sql+" and b.project_id="+am.getProjectId();
		}else{
			sql=sql;
		}
		/*if(pageIndex!=-1){
			sql = sql +" limit "+(pageSize*pageIndex-pageSize)+","+pageSize;
		}*/
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
		 
	    
       /* result.setData(ret);
        result.setTotalNumber(totalItemNum);
        result.setCurrentPage(pageIndex);
        result.setTotalPage(totalPageNum);
        result.setNumberPerPage(pageSize);*/
        return dataWrapper;
	}

	@Override
	public DataWrapper<List<AttenceLogs>> getAttenceLogsUserList(Integer pageIndex, Integer pageSize,AttenceLog am,Integer year,Integer month) {
		// TODO Auto-generated method stub
		/*if(pageIndex==null){
			pageIndex=1;
		}
		if(pageSize==null){
			pageSize=10;
		}*/
		DataWrapper<List<AttenceLogs>> dataWrapper=new DataWrapper<List<AttenceLogs>>();
		String sql = "select DATE_FORMAT(a.create_date,'%Y-%m') as month,a.* from attence_log a "
		+" where project_id="+am.getProjectId()+" group by user_id";
		/*if(pageIndex!=-1){
			sql = sql +" limit "+(pageSize*pageIndex-pageSize)+","+pageSize;
		}*/
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
		 
	    
       /* result.setData(ret);
        result.setTotalNumber(totalItemNum);
        result.setCurrentPage(pageIndex);
        result.setTotalPage(totalPageNum);
        result.setNumberPerPage(pageSize);*/
        return dataWrapper;
	}
	@Override
	public DataWrapper<Void> deleteAttenceLogByProjectId(Long id) {
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
	public DataWrapper<List<AttenceLog>> getAttenceLogList(Integer pageIndex, Integer pageSize, AttenceLog am) {
		// TODO Auto-generated method stub
		return null;
	}

}
