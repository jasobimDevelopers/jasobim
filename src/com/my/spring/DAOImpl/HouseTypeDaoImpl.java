package com.my.spring.DAOImpl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.HouseTypeDao;
import com.my.spring.model.HouseType;

public class HouseTypeDaoImpl extends BaseDao<HouseType> implements HouseTypeDao{

	@SuppressWarnings("unchecked")
	@Override
	public HouseType getHouseTypeByProjectId(Long projectId) {
		HouseType ht = new HouseType();
		List<HouseType> ret = new ArrayList<HouseType>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(HouseType.class);
       
        if(projectId!=null){
        	criteria.add(Restrictions.eq("projectId",projectId));
        }
       
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
        	ht=ret.get(0);
		}else{
			ht=null;
		}
       
		return ht;
	}

	@Override
	public boolean addHouseType(HouseType houseType) {
		// TODO Auto-generated method stub
		return save(houseType);
	}

	@Override
	public boolean updateHouseType(HouseType houseType) {
		// TODO Auto-generated method stub
		return update(houseType);
	}

}
