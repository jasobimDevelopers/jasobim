package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProjectPartContractLoftingDao;
import com.my.spring.model.ProjectPartContractLofting;
import com.my.spring.model.ProjectPartContractLoftingPojo;
import com.my.spring.model.UserIndexs;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProjectPartContractLoftingDaoImpl extends BaseDao<ProjectPartContractLofting> implements ProjectPartContractLoftingDao {

    @Override
    public boolean addProjectPartContractLofting(ProjectPartContractLofting role) {
        return save(role);
    }
    @Override
    public boolean addProjectPartContractLoftingList(List<ProjectPartContractLofting> role) {
        return saveList(role);
    }
    @Override
    public boolean deleteProjectPartContractLofting(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public List<ProjectPartContractLoftingPojo> getProjectPartContractLoftingList(ProjectPartContractLofting ProjectPartContractLofting) {
        List<ProjectPartContractLoftingPojo> ret = new ArrayList<ProjectPartContractLoftingPojo>();
		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
        String sql = "select id,p_name as name,name as partName,value,contract_lofting_id as loftingId "
		+"from project_part_contract_lofting where contract_lofting_id in "
		+"(select id from contract_lofting where project_id="+ProjectPartContractLofting.getProjectId()
		+" and pid="+ProjectPartContractLofting.getContractLoftingId()+")";
		Session session=getSession();
	    try{
		    Query query = session.createSQLQuery(sql)
				 .addScalar("id",StandardBasicTypes.LONG)
				 .addScalar("loftingId", StandardBasicTypes.LONG)
				 .addScalar("name",StandardBasicTypes.STRING)
				 .addScalar("partName",StandardBasicTypes.STRING)
				 .addScalar("value", StandardBasicTypes.DOUBLE)
				 .setResultTransformer(Transformers.aliasToBean(ProjectPartContractLoftingPojo.class)); 
		    ret=query.list();
	        
	    }catch(Exception e){
	        e.printStackTrace();
	        //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	    }

		return ret;
    }
    @SuppressWarnings("unchecked")
	@Override
    public List<ProjectPartContractLoftingPojo> getProjectPartContractLoftingList(Long projectId) {
    	 List<ProjectPartContractLoftingPojo> ret = new ArrayList<ProjectPartContractLoftingPojo>();
    		//select a.* from question a where a.project_id in (select c.project_id from user_project c where c.user_id=33)
    		String sql = "select b.id,b.id as loftingId,a.name,b.name as partName,b.value "
    		+"from contract_lofting a,project_part_contract_lofting b where a.pid is null "
    		+"and a.id=b.contract_lofting_id and a.project_id="+projectId;
    		Session session=getSession();
    	    try{
    		    Query query = session.createSQLQuery(sql)
    				 .addScalar("id",StandardBasicTypes.LONG)
    				 .addScalar("loftingId", StandardBasicTypes.LONG)
    				 .addScalar("name",StandardBasicTypes.STRING)
    				 .addScalar("partName",StandardBasicTypes.STRING)
    				 .addScalar("value", StandardBasicTypes.DOUBLE)
    				 .setResultTransformer(Transformers.aliasToBean(ProjectPartContractLoftingPojo.class)); 
    		    ret=query.list();
    	        
    	    }catch(Exception e){
    	        e.printStackTrace();
    	        //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
    	    }

    		return ret;
    }
   

	@Override
	public ProjectPartContractLofting getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	
	@Override
	public boolean deleteProjectPartContractLoftingList(String[] ids) {
		// TODO Auto-generated method stub
		return deleteList(ids);
	}
	@Override
	public boolean updateProjectPartContractLofting(ProjectPartContractLofting dp) {
		// TODO Auto-generated method stub
		return update(dp);
	}
	@Override
	public List<UserIndexs> getProjectPartContractLoftingListByUserId(Long userId) {
		List<UserIndexs> gets=new ArrayList<UserIndexs>();
		String sql = "select b.name,b.id,a.indexs from user_index a,ProjectPartContractLofting b where a.about_type=0 and a.about_id=b.id and a.user_id="
		+userId+" ORDER BY a.indexs asc";
		Session session=getSession();
		try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id", StandardBasicTypes.LONG)
					 .addScalar("name", StandardBasicTypes.STRING)
					 .addScalar("indexs", StandardBasicTypes.LONG)
					 .setResultTransformer(Transformers.aliasToBean(UserIndexs.class));
			 	gets=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	    }
		return gets;
	}
	@Override
	public List<ProjectPartContractLofting> getProjectPartContractLoftingListByContractLoftingId(Long id) {
        List<ProjectPartContractLofting> ret = new ArrayList<ProjectPartContractLofting>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProjectPartContractLofting.class);
        criteria.add(Restrictions.eq("contractLoftingId", id));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return ret;
	}
	@Override
	public boolean deleteProjectPartContractLoftingByName(String name,Long projectId) {
		String sql = "delete from project_part_contract_lofting where name='"+name+"'"+" and project_id="+projectId;
		Session session=getSession();
		boolean test=false;
		 try{
			 Query query = session.createSQLQuery(sql);
			 int temp=query.executeUpdate();
			 if(temp!=0){
				 test= true;
			 }
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return test;
	}
	@Override
	public boolean deleteProjectPartContractLoftingByProjectId(Long projectId) {
		String sql = "delete from project_part_contract_lofting where project_id="+projectId;
		Session session=getSession();
		boolean test=false;
		 try{
			 Query query = session.createSQLQuery(sql);
			 int temp=query.executeUpdate();
			 if(temp!=0){
				 test= true;
			 }
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return test;
	}
	
}
