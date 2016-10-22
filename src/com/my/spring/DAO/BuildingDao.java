package com.my.spring.DAO;

import com.my.spring.model.BuildingEntity;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface BuildingDao {
    boolean addBuilding(BuildingEntity building);
    boolean deleteBuilding(Long id);
    boolean updateBuilding(BuildingEntity building);
    DataWrapper<List<BuildingEntity>> getBuildingList();
}
