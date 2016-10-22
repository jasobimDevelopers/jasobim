package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.BuildingDao;
import com.my.spring.model.BuildingEntity;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class BuildingDaoImpl extends BaseDao<BuildingEntity> implements BuildingDao {

    @Override
    public boolean addBuilding(BuildingEntity building) {
        return save(building);
    }

    @Override
    public boolean deleteBuilding(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateBuilding(BuildingEntity building) {
        return update(building);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<BuildingEntity>> getBuildingList() {
        DataWrapper<List<BuildingEntity>> retDataWrapper = new DataWrapper<List<BuildingEntity>>();
        List<BuildingEntity> ret = new ArrayList<BuildingEntity>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(BuildingEntity.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }
}
