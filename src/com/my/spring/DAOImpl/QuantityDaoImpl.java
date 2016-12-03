package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ParameterMode;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.QuantityDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Quantity;
import com.my.spring.model.QuantityPojo;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class QuantityDaoImpl extends BaseDao<Quantity> implements QuantityDao {

    @Override
    public boolean addQuantity(Quantity quantity) {
        return save(quantity);
    }

    @Override
    public boolean deleteQuantity(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateQuantity(Quantity quantity) {
        return update(quantity);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Quantity>> getQuantityList(Long projectId,Integer pageSize,Integer pageIndex,Quantity quantity) {
    	DataWrapper<List<Quantity>> retDataWrapper = new DataWrapper<List<Quantity>>();
        List<Quantity> ret = new ArrayList<Quantity>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Quantity.class);
        ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("value").as("value"))
						.add(Projections.property("unit").as("unit"))
						.add(Projections.property("projectId").as("projectId"))
						.add(Projections.property("systemType").as("systemType"))
						.add(Projections.property("serviceType").as("serviceType"))
						.add(Projections.property("familyAndType").as("familyAndType"))
						.add(Projections.property("size").as("size"))	
						.add(Projections.property("material").as("material"))
						.add(Projections.property("name").as("name"))
						.add(Projections.property("typeName").as("typeName"))
								
						.add(Projections.groupProperty("projectId"))
						.add(Projections.groupProperty("systemType"))
						.add(Projections.groupProperty("serviceType"))
						.add(Projections.groupProperty("familyAndType"))
						.add(Projections.groupProperty("size"))
						.add(Projections.groupProperty("material"))
						.add(Projections.groupProperty("name"))
						.add(Projections.groupProperty("typeName"));
		if(quantity.getProfessionType()!=null){
			projectionList.add(Projections.property("professionType").as("professionType"))
						  .add(Projections.groupProperty("professionType"));
		}
		if(quantity.getBuildingNum()!=null){
			projectionList.add(Projections.groupProperty("buildingNum"))
			              .add(Projections.property("buildingNum").as("buildingNum"));
		}	
		if(quantity.getFloorNum()!=null){
			projectionList.add(Projections.groupProperty("floorNum"))
						  .add(Projections.property("floorNum").as("floorNum"));
		}
		if(quantity.getHouseholdNum()!=null){
			projectionList.add(Projections.groupProperty("householdNum"))
						  .add(Projections.property("householdNum").as("householdNum"));;
		}
        ///////////////////////////////
        criteria.add(Restrictions.eq("projectId", projectId));
        criteria.setProjection(projectionList);
        if(quantity.getProfessionType()!=null){
        	criteria.add(Restrictions.eq("professionType", quantity.getProfessionType()));
        }
        if(quantity.getBuildingNum()!=null){
        	criteria.add(Restrictions.eq("buildingNum", quantity.getBuildingNum()));
        }
        if(quantity.getHouseholdNum()!=null){
        	criteria.add(Restrictions.eq("householdNum", quantity.getHouseholdNum()));
        }
        if(quantity.getFloorNum()!=null){
        	criteria.add(Restrictions.eq("floorNum", quantity.getFloorNum()));
        }
        	
        /////////////////////////////////////
   
        if (pageSize == null) {
			pageSize = 10;
		}
        if (pageIndex == null) {
			pageIndex = 1;
		}
        
        
        int totalItemNum =  criteria.list().size();
		int totalPageNum = DaoUtil.getTotalPageNumber(totalItemNum, pageSize);
		
		
		criteria.setResultTransformer(Transformers.aliasToBean(Quantity.class));
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

	@Override
	public Quantity getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean findQuantity(Quantity quantity) {
		String sql = "select * from quantity as a where a.project_id="+quantity.getProjectId()
		+" and a.building_num="+quantity.getBuildingNum()+" and a.unit_num="+quantity.getUnitNum()
		+" and a.floor_num="+quantity.getFloorNum()+" and a.household_num="+quantity.getHouseholdNum()
		+" and a.name='"+quantity.getName()+"' and a.size='"+quantity.getSize()+"' and a.material='"
		+quantity.getMaterial()+"' and a.service_type='"+quantity.getServiceType()
		+"' and a.system_type='"+quantity.getSystemType()+"' and a.family_and_type='"
		+quantity.getFamilyAndType()+"' and a.profession_type="+quantity.getProfessionType();
		DataWrapper<List<QuantityPojo>> dataWrapper = new DataWrapper<List<QuantityPojo>>();
		Session session=getSession();
		try{
	            Query query=session.createSQLQuery(sql);
	            dataWrapper.setData(query.list());
	            if(dataWrapper.getData()!=null&&dataWrapper.getData().size()>0){
	            	return true;
	            }
	    }catch(Exception e){
	        	dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	            e.printStackTrace();
	           
	    }
		
		return false;
	}

	@Override
	public boolean addQuantityList(List<Quantity> quantityList) {
		// TODO Auto-generated method stub
		return saveList(quantityList);
	}

	@Override
	public List<Quantity> getAllQuantity() {
//		String sql = "select * from quantity ";
//		List<Quantity> dataWrapper = new ArrayList<Quantity>();
//		Session session=getSession();
//		try{
//	            
//	            dataWrapper=query.list();
//	            if(dataWrapper!=null&&dataWrapper.size()>0){
//	            	return dataWrapper;
//	            }
//	    }catch(Exception e){
//	            e.printStackTrace();
//	    }
//		
//		return null;
		List<Quantity> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(Quantity.class);
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
		return ret;
	}

	@Override
	public boolean deleteQuantityByProjectId(Long id) {
		String sql = "delete from quantity where project_id="+id;
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql);
			 if(query.executeUpdate()==1){
				 return true;
			 }
			 
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
		return false;
	}

	@Override
	public boolean exportQuantity(String filePath,Long projectId) {
		// TODO Auto-generated method stub
		
		Session session=getSession();
		try{
			ProcedureCall procedureCall = session.createStoredProcedureCall("exportQuantity");
			procedureCall.registerParameter("file_path", String.class, ParameterMode.IN).bindValue(filePath);
			procedureCall.registerParameter("project_id", Long.class, ParameterMode.IN).bindValue(projectId);
			procedureCall.getOutputs();
	    }catch(Exception e){
	        e.printStackTrace();
	        return false;
	    }
		
		return true;
	}

	@Override
	public DataWrapper<List<Quantity>> getQuantityListNum(Long projectId, Integer pageSize, Integer pageIndex,
			Quantity quantity) {
		String sql = "select count(*) as num,SUM(length) as lengthnum,SUM(area) as areanum,"
				 +"project_id,building_num,unit_num,household_num,system_type,"
				 +"service_type,family_and_type,size,material,name,type_name,profession_type from item "
				 + "GROUP BY project_id,building_num,unit_num,household_num,"
				 + "system_type,service_type,family_and_type,size,material,name,type_name";
		DataWrapper<List<QuantityPojo>> dataWrapper=new DataWrapper<List<QuantityPojo>>();
		DataWrapper<List<Quantity>> dataWrappers=new DataWrapper<List<Quantity>>();
		Session session=getSession();
		 try{
			 Query query = session.createSQLQuery(sql)
					 .addScalar("num",StandardBasicTypes.LONG)
					 .addScalar("lengthnum", StandardBasicTypes.DOUBLE)
					 .addScalar("areanum",StandardBasicTypes.DOUBLE)
					 .addScalar("project_id",StandardBasicTypes.LONG)
					 .addScalar("building_num", StandardBasicTypes.INTEGER)
					 .addScalar("unit_num", StandardBasicTypes.INTEGER)
					 .addScalar("household_num", StandardBasicTypes.INTEGER)
					 .addScalar("system_type", StandardBasicTypes.STRING)
					 .addScalar("service_type", StandardBasicTypes.STRING)
					 .addScalar("family_and_type", StandardBasicTypes.STRING)
					 .addScalar("size", StandardBasicTypes.STRING)
					 .addScalar("material", StandardBasicTypes.STRING)
					 .addScalar("type_name", StandardBasicTypes.STRING)
					 .addScalar("name", StandardBasicTypes.STRING)
					 .addScalar("profession_type", StandardBasicTypes.INTEGER)
					 .setResultTransformer(Transformers.aliasToBean(QuantityPojo.class)); 
			 dataWrapper.setData(query.list());
			 List <Quantity> quantityList=new ArrayList<Quantity>();
			 if(dataWrapper.getData()!=null && dataWrapper.getData().size()>0){
					if(dataWrapper.getData()!=null){
			    		for(QuantityPojo pojo:dataWrapper.getData()){
			    			
			    			Quantity test=new Quantity();
			    			Long st=pojo.getProject_id();
			    			test.setProjectId(st);
			    			test.setBuildingNum(pojo.getBuilding_num());
			    			test.setFloorNum(pojo.getFloor_num());
			    			test.setUnitNum(pojo.getUnit_num());
			    			test.setHouseholdNum(pojo.getHousehold_num());
//			    			test.setTypeName(pojo.getType_name());
			    			String familyAndType=pojo.getFamily_and_type();
			    			String typeName=pojo.getType_name();
			    			if(typeName==null){
			    				typeName="";
			    			}
//			    			test.setTypeName(typeName);
			    			if(familyAndType==null){
			    				familyAndType="";
			    			}
			    			test.setFamilyAndType(familyAndType);
			    			String serviceType=pojo.getService_type();
			    			if(serviceType==null){
			    				serviceType="";
			    			}
			    			test.setServiceType(serviceType);
			    			String systemType=pojo.getSystem_type();
			    			if(systemType==null){
			    				systemType="";
			    			}
			    			test.setSystemType(systemType);
			    			test.setProfessionType(pojo.getProfession_type());
			    			String names=pojo.getName();
			    			if(names==null){
			    				names="";
			    			}
			    			test.setName(names);
			    			String size=pojo.getSize();
			    			if(size==null){
			    				size="";
			    			}
			    			test.setSize(size);
			    			String material=pojo.getMaterial();
			    			if(material.equals("")){
			    				material="";
			    			}
			    			test.setMaterial(material);
			    			if(pojo.getLengthnum()!=0){
			    				test.setValue((pojo.getLengthnum()/1000));
			    				test.setUnit("米（m）");
			    			}
			    			if(pojo.getAreanum()!=0){
			    				test.setValue(pojo.getAreanum());
			    				test.setUnit("平米（m2）");
			    			}
			    			if(pojo.getNum()!=0 && pojo.getAreanum()==0 && pojo.getLengthnum()==0){
			    				test.setValue(new Double(pojo.getNum()));
			    				test.setUnit("个");
			    			}
			    			quantityList.add(test);
			    		}
			    		dataWrappers.setData(quantityList);
			    	}else{
			    		dataWrappers.setErrorCode(ErrorCodeEnum.Error);
			    	}
			 }
	        }catch(Exception e){
	            e.printStackTrace();
	            //dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
		return dataWrappers;
	}

	@Override
	public boolean findSameQuantityAndDo(List<Quantity> quantityList) {
		List<Quantity> quantityLists=new ArrayList<Quantity>();
		List<Quantity> sameQuantityLists=new ArrayList<Quantity>();
        Session session = getSession();
        for(int i=0;i<quantityList.size();i++){
        	Criteria criteria = session.createCriteria(Quantity.class);
	        criteria.add(Restrictions.eq("name",quantityList.get(i).getName()))
	        .add(Restrictions.eq("professionType", quantityList.get(i).getProfessionType()))
	        .add(Restrictions.eq("projectId", quantityList.get(i).getProjectId()))
	        .add(Restrictions.eq("buildingNum", quantityList.get(i).getBuildingNum()))
	        .add(Restrictions.eq("floorNum", quantityList.get(i).getFloorNum()))
	        .add(Restrictions.eq("unitNum", quantityList.get(i).getUnit()))
	        .add(Restrictions.eq("householdNum",quantityList.get(i).getHouseholdNum()))
	        .add(Restrictions.eq("familyAndType", quantityList.get(i).getFamilyAndType()))
	        .add(Restrictions.eq("systemType", quantityList.get(i).getSystemType()))
	        .add(Restrictions.eq("serviceType", quantityList.get(i).getServiceType()))
	        .add(Restrictions.eq("size", quantityList.get(i).getSize()))
	        .add(Restrictions.eq("material", quantityList.get(i).getMaterial()))
	        .add(Restrictions.eq("typeName",quantityList.get(i).getTypeName()));
	        try {
	        	quantityLists = criteria.list();
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        if(quantityLists.size()>0){
	        	sameQuantityLists.add(quantityList.get(i));
	        }
        }
		return false;
	}

	@Override
	public DataWrapper<List<Quantity>> testGroupBy(int pageSize,int pageIndex) {
		// TODO Auto-generated method stub
		
		DataWrapper<List<Quantity>> dataWrapper = new DataWrapper<List<Quantity>>();
		List<Quantity> ret = null;
		
		Session session = getSession();
		Criteria criteria = session.createCriteria(Quantity.class);
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("value").as("value"))
						.add(Projections.property("unit").as("unit"))
						.add(Projections.property("projectId").as("projectId"))
						.add(Projections.property("systemType").as("systemType"))
						.add(Projections.property("serviceType").as("serviceType"))
						.add(Projections.property("familyAndType").as("familyAndType"))
						.add(Projections.property("size").as("size"))	
						.add(Projections.property("material").as("material"))
						.add(Projections.property("name").as("name"))
						.add(Projections.property("typeName").as("typeName"))
						.add(Projections.property("professionType").as("professionType"))
		
						.add(Projections.groupProperty("projectId"))
						.add(Projections.groupProperty("systemType"))
						.add(Projections.groupProperty("serviceType"))
						.add(Projections.groupProperty("familyAndType"))
						.add(Projections.groupProperty("size"))
						.add(Projections.groupProperty("material"))
						.add(Projections.groupProperty("name"))
						.add(Projections.groupProperty("typeName"))
						.add(Projections.groupProperty("professionType"));
		
		
		criteria.add(Restrictions.eq("projectId", new Long(79)));
		criteria.setProjection(projectionList);
		
		
		int totalItemNum =  criteria.list().size();
		int totalPageNum = DaoUtil.getTotalPageNumber(totalItemNum, pageSize);
		
		
		criteria.setResultTransformer(Transformers.aliasToBean(Quantity.class));
		if (pageSize > 0 && pageIndex > 0) {
			criteria.setMaxResults(pageSize);// 最大显示记录数
			criteria.setFirstResult((pageIndex - 1) * pageSize);// 从第几条开始
		 }
		
	
		try {
			ret =criteria.list();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);

		dataWrapper.setData(ret);
		
		return dataWrapper;
	}
}
