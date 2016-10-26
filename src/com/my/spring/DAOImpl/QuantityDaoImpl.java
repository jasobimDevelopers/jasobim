package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.QuantityDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Quantity;
import com.my.spring.model.QuantityPojo;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
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
    public DataWrapper<List<Quantity>> getQuantityList() {
        DataWrapper<List<Quantity>> retDataWrapper = new DataWrapper<List<Quantity>>();
        List<Quantity> ret = new ArrayList<Quantity>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Quantity.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }

	@Override
	public Quantity getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public boolean findQuantity(Quantity quantity) {
		String sql = "select * from quantity as a where a.project_id="+quantity.getProjectId()
		+" and a.building_num="+quantity.getBuildingNum()+" and a.unit_num="+quantity.getUnitNum()
		+" and a.floor_num="+quantity.getFloorNum()+" and a.household_num="+quantity.getHouseholdNum()
		+" and a.name="+quantity.getName()+" and a.size="+quantity.getSize()+" and a.material="
		+quantity.getMaterial()+" and a.service_type="+quantity.GetServiceType()
		+" and a.system_type="+quantity.getSystemType()+" and a.family_and_type="
		+quantity.getFamilyAndType();
		DataWrapper<List<QuantityPojo>> dataWrapper = new DataWrapper<List<QuantityPojo>>();
		Session session=getSession();
		 try{
	            Query query=session.createSQLQuery(sql);
	            if(query.list()!=null){
	            	return true;
	            }
	            session.getTransaction().commit();
	            session.flush();
	        }catch(Exception e){
	            e.printStackTrace();
	            session.getTransaction().rollback();
	            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		
		return false;
	}
}
