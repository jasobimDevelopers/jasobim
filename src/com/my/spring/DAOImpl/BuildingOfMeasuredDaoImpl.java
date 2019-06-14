package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.BuildingOfMeasuredDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Building;
import com.my.spring.model.BuildingOfMeasured;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class BuildingOfMeasuredDaoImpl extends BaseDao<BuildingOfMeasured> implements BuildingOfMeasuredDao {

    @Override
    public boolean addBuildingOfMeasured(BuildingOfMeasured building) {
        return save(building);
    }

    @Override
    public boolean deleteBuildingOfMeasured(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateBuildingOfMeasured(BuildingOfMeasured building) {
        return update(building);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<BuildingOfMeasured>> getBuildingOfMeasuredList() {
        DataWrapper<List<BuildingOfMeasured>> retDataWrapper = new DataWrapper<List<BuildingOfMeasured>>();
        List<BuildingOfMeasured> ret = new ArrayList<BuildingOfMeasured>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Building.class);
//        criteria.addOrder(Order.desc("publishDate"));
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
	public DataWrapper<List<BuildingOfMeasured>> getBuildingOfMeasuredByProjectId(Long projectId) {
		DataWrapper<List<BuildingOfMeasured>> dataWrapper=new DataWrapper<List<BuildingOfMeasured>>();
		List<BuildingOfMeasured> ret = new ArrayList<BuildingOfMeasured>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(BuildingOfMeasured.class);
        criteria.add(Restrictions.eq("projectId",projectId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null) {
        	dataWrapper.setData(ret);
		}
		return dataWrapper;
	}

	@Override
	public BuildingOfMeasured getBuildingOfMeasuredById(Long bfmId) {
		 return get(bfmId);
	}

}
