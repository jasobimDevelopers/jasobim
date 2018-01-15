package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ValueOutputDao;
import com.my.spring.model.ValueOutput;
import com.my.spring.model.ValueOutputPojo;
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
public class ValueOutputDaoImpl extends BaseDao<ValueOutput> implements ValueOutputDao {

    @Override
    public boolean addValueOutput(ValueOutput ValueOutput) {
        return save(ValueOutput);
    }

    @Override
    public boolean deleteValueOutput(Long id) {
        return delete(get(id));
    }
    @Override
    public boolean deleteValueOutputs(String id) {
    	boolean bool=false;
    	if(id!=null){
    		for(int i=0;i<id.split(",").length;i++){
    			bool= delete(get(Long.valueOf(id.split(",")[i])));
    		}
    	        
    	}
    	return bool;
    }

    @Override
    public boolean updateValueOutput(ValueOutput ValueOutput) {
        return update(ValueOutput);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ValueOutputPojo>> getValueOutputList(String projectList) {
    	String temp=null;
    	if(projectList!=null && !projectList.equals("null")){
        	String[] ss =projectList.split(",");
            for(int i=0;i<ss.length;i++){
            	if(i==0){
            		temp=ss[i];
            	}else{
            		temp+=" or project_id="+ss[i];
            	}
            }
        }
    	String sql="";
    	if(temp!=null){
    		sql = "select id,others,num,sum(finished) as finished,date,project_id "
        			+ "from value_output where project_id="+temp+" GROUP BY project_id ORDER BY date DESC";
    	}else{
    		sql = "select id,others,num,sum(finished) as finished,date,project_id "
        			+ "from value_output GROUP BY others ORDER BY date DESC";
    	}
		DataWrapper<List<ValueOutputPojo>> dataWrapper=new DataWrapper<List<ValueOutputPojo>>();
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id",StandardBasicTypes.LONG)
					 .addScalar("others", StandardBasicTypes.STRING)
					 .addScalar("num",StandardBasicTypes.DOUBLE)
					 .addScalar("project_id",StandardBasicTypes.LONG)
					 .addScalar("finished", StandardBasicTypes.DOUBLE)
					 .addScalar("date", StandardBasicTypes.DATE)
					 .setResultTransformer(Transformers.aliasToBean(ValueOutputPojo.class)); 
			 dataWrapper.setData(query.list());
	            
	        }catch(Exception e){
	            e.printStackTrace();
	            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
		return dataWrapper;
	}

	@Override
	public boolean addValueOutputList(List<ValueOutput> ValueOutputList) {
		// TODO Auto-generated method stub
		return saveList(ValueOutputList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<ValueOutput>> getValueOutputByProjectId(Long projectId,String projectName,String[] projectList) {
		 DataWrapper<List<ValueOutput>> retDataWrapper = new DataWrapper<List<ValueOutput>>();
	        List<ValueOutput> ret = new ArrayList<ValueOutput>();
	        Session session = getSession();
	        Criteria criteria = session.createCriteria(ValueOutput.class);
	        criteria.addOrder(Order.desc("date"));
	        /*if(projectList!=null){
	        	Disjunction dis = Restrictions.disjunction();
	        	for(int i=0;i<projectList.length;i++){
	        		dis.add(Restrictions.eq("projectId", Long.valueOf(projectList[i])));
	        	}
	        	criteria.add(dis);
	        }*/
	        if(projectId!=null){
	        	criteria.add(Restrictions.eq("projectId",projectId));
	        	criteria.add(Restrictions.eq("others", projectName));
	        }else{
	        	criteria.add(Restrictions.eq("others",projectName));
	        }
	        
	        /*criteria.add(Restrictions.sqlRestriction("select id,sum(finished) as finished,sum(num) as num,others,date from value_output a group by project_id ORDER BY date DESC"));
	        ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.sum("num").as("num"),"num")
						.add(Projections.sum("finished").as("finished"),"finished")
						.add(Projections.groupProperty("projectId").as("projectId"),"projectId")
						.add(Projections.property("id").as("id"),"id")
						.add(Projections.property("others").as("others"),"others")
						.add(Projections.property("date").as("date"),"date");
			criteria.setProjection(projectionList);*/
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
	public DataWrapper<List<ValueOutput>> getValueOutputListByProjectName(String projectName) {
		 DataWrapper<List<ValueOutput>> retDataWrapper = new DataWrapper<List<ValueOutput>>();
	        List<ValueOutput> ret = new ArrayList<ValueOutput>();
	        Session session = getSession();
	        Criteria criteria = session.createCriteria(ValueOutput.class);
	        //criteria.addOrder(Order.desc("date"));
	        if(projectName!=null){
	        	criteria.add(Restrictions.eq("others", projectName));
	        }
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
	public DataWrapper<List<ValueOutput>> getValueOutputLists(Integer pageSize, Integer pageIndex,ValueOutput valueOutput,String dates) {
		// TODO Auto-generated method stub
		DataWrapper<List<ValueOutput>> dataWrapper = new DataWrapper<List<ValueOutput>>();
        List<ValueOutput> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(ValueOutput.class);
        criteria.addOrder(Order.desc("date"));
        if(valueOutput!=null){
        	if(valueOutput.getDate()!=null){
        		criteria.add(Restrictions.eq("date", valueOutput.getDate()));
        	}
        	if(valueOutput.getProjectId()!=-1){
        		criteria.add(Restrictions.eq("projectId", valueOutput.getProjectId()));
        	}
        	if(valueOutput.getOthers()!=null){
        		criteria.add(Restrictions.like("others", "%"+valueOutput.getOthers()+"%"));
        	}
        	
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
        dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);

        return dataWrapper;
	}

	@Override
	public ValueOutput getById(Long fileId) {
		// TODO Auto-generated method stub
		return get(fileId);
	}
}
