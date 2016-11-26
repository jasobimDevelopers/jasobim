package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.QuantityDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Quantity;
import com.my.spring.model.QuantityPojo;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
        ///////////////////////////////
        criteria.add(Restrictions.eq("projectId", projectId));
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
		+quantity.getMaterial()+"' and a.service_type='"+quantity.GetServiceType()
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
		String sql = "select * from quantity ";
		List<Quantity> dataWrapper = new ArrayList<Quantity>();
		Session session=getSession();
		try{
	            Query query=session.createSQLQuery(sql);
	            dataWrapper=query.list();
	            if(dataWrapper!=null&&dataWrapper.size()>0){
	            	return dataWrapper;
	            }
	    }catch(Exception e){
	            e.printStackTrace();
	    }
		
		return null;
	}

	@Override
	public boolean exportQuantity(String filePath) {
		// TODO Auto-generated method stub
		String sql = "select * from quantity into outfile '" + filePath + "';";
		Session session=getSession();
		try{
	        Query query=session.createSQLQuery(sql);
	        query.list();
	    }catch(Exception e){
	        e.printStackTrace();
	        return false;
	    }
		
		return true;
	}
}
