package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ContractLoftingDao;
import com.my.spring.model.ContractLofting;
import com.my.spring.model.ContractLoftingMode;
import com.my.spring.model.UserIndexs;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ContractLoftingDaoImpl extends BaseDao<ContractLofting> implements ContractLoftingDao {

    @Override
    public boolean addContractLofting(ContractLofting role) {
        return save(role);
    }
    @Override
    public boolean deleteContractLofting(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<ContractLofting>> getContractLoftingList(Integer pageIndex, Integer pageSize, ContractLofting ContractLofting) {
        DataWrapper<List<ContractLofting>> dataWrapper = new DataWrapper<List<ContractLofting>>();
        List<ContractLofting> ret = new ArrayList<ContractLofting>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ContractLofting.class);
        criteria.add(Restrictions.eq("projectId", ContractLofting.getProjectId()));
        if(ContractLofting.getId()!=null){
        	criteria.add(Restrictions.eq("pid", ContractLofting.getId()));
        }else if(ContractLofting.getName()!=null){
        	criteria.add(Restrictions.like("name", "%"+ContractLofting.getName()+"%"));
        }else{
        	criteria.add(Restrictions.isNull("pid"));
        }
//        criteria.addOrder(Order.desc("publishDate"));
       
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        dataWrapper.setData(ret);
        //dataWrapper.setTotalNumber(totalItemNum);
        //dataWrapper.setCurrentPage(pageIndex);
        //dataWrapper.setTotalPage(totalPageNum);
       // dataWrapper.setNumberPerPage(pageSize);
        return dataWrapper;
    }

	@Override
	public ContractLofting getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	
	@Override
	public boolean deleteContractLoftingList(String[] ids) {
		// TODO Auto-generated method stub
		return deleteList(ids);
	}
	@Override
	public boolean updateContractLofting(ContractLofting dp) {
		// TODO Auto-generated method stub
		return update(dp);
	}
	@Override
	public List<UserIndexs> getContractLoftingListByUserId(Long userId) {
		List<UserIndexs> gets=new ArrayList<UserIndexs>();
		String sql = "select b.name,b.id,a.indexs from user_index a,ContractLofting b where a.about_type=0 and a.about_id=b.id and a.user_id="
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
	public boolean addContractLoftingList(List<ContractLofting> parrent1) {
		// TODO Auto-generated method stub
		return saveList(parrent1);
	}
	
	@Override
	public List<ContractLoftingMode> getAllContractLofting(Long projectId) {
		List<ContractLoftingMode> gets=new ArrayList<ContractLoftingMode>();
		String sql = "select id,name from contract_lofting where project_id="+projectId;
		Session session=getSession();
		try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("id", StandardBasicTypes.LONG)
					 .addScalar("name", StandardBasicTypes.STRING)
					 .setResultTransformer(Transformers.aliasToBean(ContractLoftingMode.class));
			 	gets=query.list();
	        }catch(Exception e){
	            e.printStackTrace();
	    }
		return gets;
	}
	@Override
	public List<ContractLofting> getAllContractLoftings(Long projectId) {
		List<ContractLofting> ret = new ArrayList<ContractLofting>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(ContractLofting.class);
        criteria.add(Restrictions.eq("projectId", projectId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
	}
	@Override
	public boolean deleteContractLoftingByName(String name) {
		String sql = "delete from contract_lofting where name="+name;
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
	public boolean deleteContractLoftingByIds(List<Long> ids) {
		String sql = "delete from contract_lofting where id=";
		for(int i=0;i<ids.size();i++){
			if(i==(ids.size()-1)){
				sql=sql+ids.get(i);
			}else{
				sql=sql+ids.get(i)+" or id=";
			}
		}
		
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
	public boolean deleteContractLoftingByProjectId(Long projectId) {
		String sql = "delete from contract_lofting where project_id="+projectId;
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
