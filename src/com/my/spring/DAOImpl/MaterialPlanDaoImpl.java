package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MaterialPlanDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MaterialPlan;
import com.my.spring.parameters.Parameters;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.loader.criteria.CriteriaJoinWalker;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.hibernate.persister.entity.OuterJoinLoadable;
import org.hibernate.sql.DisjunctionFragment;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Repository
public class MaterialPlanDaoImpl extends BaseDao<MaterialPlan> implements MaterialPlanDao {

    @Override
    public boolean addMaterialPlan(MaterialPlan File) {
        return save(File);
    }
    
    @Override
    public boolean updateMaterialPlan(MaterialPlan File) {
        return update(File);
    }
    @Override
    public boolean deleteMaterialPlan(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<MaterialPlan>> getMaterialPlanList(MaterialPlan MaterialPlan,String dates) {
        DataWrapper<List<MaterialPlan>> retDataWrapper = new DataWrapper<List<MaterialPlan>>();
        List<MaterialPlan> ret = new ArrayList<MaterialPlan>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MaterialPlan.class);
        Disjunction djs = Restrictions.disjunction();  
        Conjunction con = Restrictions.conjunction();
        Conjunction con1 = Restrictions.conjunction();
        Conjunction con2 = Restrictions.conjunction();
        if(!dates.equals("") && dates!=null){
        	 try {
        		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        		 Date input=sdf.parse(dates);
				 Calendar rightNow = Calendar.getInstance();
				 rightNow.setTime(input);
				 rightNow.add(Calendar.MONTH,1);//日期加1月
				 rightNow.add(Calendar.DATE,-1);//天数减一
				 Date dt1=rightNow.getTime();
				 con.add(Restrictions.ge("startTime",input));
				 con.add(Restrictions.le("startTime",dt1));
				 con1.add(Restrictions.ge("endTime",input));
				 con1.add(Restrictions.le("endTime", dt1));
				 con2.add(Restrictions.le("startTime", input));
				 con2.add(Restrictions.ge("endTime", dt1));
				 djs.add(con);
				 djs.add(con1);
				 djs.add(con2);
        		 criteria.add(djs);
        		 CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;//转型  
        		    SessionImplementor sessions = criteriaImpl.getSession();//获取SESSION  
        		    SessionFactoryImplementor factory = sessions.getFactory();//获取FACTORY  
        		    CriteriaQueryTranslator translator = new CriteriaQueryTranslator(factory, criteriaImpl, criteriaImpl  
        		        .getEntityOrClassName(), CriteriaQueryTranslator.ROOT_SQL_ALIAS);  
        		    String[] implementors = factory.getImplementors(criteriaImpl.getEntityOrClassName());  
        		    CriteriaJoinWalker walker = new CriteriaJoinWalker((OuterJoinLoadable) factory  
        		        .getEntityPersister(implementors[0]), translator, factory, criteriaImpl, criteriaImpl  
        		        .getEntityOrClassName(), sessions.getLoadQueryInfluencers());  
        		    String sql = walker.getSQLString();  //转化成sql语句

        		
        		 ///////
        		
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	 
        }
		
        if(MaterialPlan.getProjectId()!=null){
        	criteria.add(Restrictions.eq("projectId", MaterialPlan.getProjectId()));
        }
        if(MaterialPlan.getName()!=null && !MaterialPlan.getName().equals("")){
        	 criteria.add(Restrictions.eq("name", MaterialPlan.getName()));
        }
        if(MaterialPlan.getPid()!=null){
        	criteria.add(Restrictions.eq("pid", MaterialPlan.getPid()));
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
    public List<MaterialPlan> getMaterialPlanListLike(Long projectId, String name) {
        List<MaterialPlan> ret = new ArrayList<MaterialPlan>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MaterialPlan.class);
        criteria.add(Restrictions.eq("projectId",projectId));
        if(name!=null){
        	 criteria.add(Restrictions.eq("name", name));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
    

	@Override
	public MaterialPlan getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<MaterialPlan> getByName(String name) {
		DataWrapper<MaterialPlan> dataWrapper = new DataWrapper<MaterialPlan>();
		String sql="select * from floder where name="+name;
		Session session=getSession();
		 try{
	            Query query=session.createSQLQuery(sql).addEntity(MaterialPlan.class);
	            List<MaterialPlan> test=query.list();
	            dataWrapper.setData(test.get(0));
	        }catch(Exception e){
	            e.printStackTrace();
	            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
		return dataWrapper;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<MaterialPlan> getAllMaterialPlan() {
        List<MaterialPlan> ret = new ArrayList<MaterialPlan>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MaterialPlan.class);
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

	@Override
	public boolean deleteMaterialPlanList(String[] ids) {
		// TODO Auto-generated method stub
		boolean bool=false;
		for(String s:ids){
			bool=delete(get(Long.valueOf(s)));
		}
		return bool;
	}

	@Override
	public List<MaterialPlan> getMaterialPlanIndexList(Long projectId, Long pid) {
        List<MaterialPlan> ret = new ArrayList<MaterialPlan>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(MaterialPlan.class);
        //criteria.add(Restrictions.eq("fileType", 0));
        if(projectId!=null){
        	criteria.add(Restrictions.eq("projectId", projectId));
        }
        if(pid!=null){
        	 criteria.add(Restrictions.eq("parrentId", pid));
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
	}
	
}
