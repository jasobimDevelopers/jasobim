package com.my.spring.service;

import com.my.spring.model.BuildingEntity;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface BuildingService {
    DataWrapper<Void> addBuilding(BuildingEntity building);
    DataWrapper<Void> deleteBuilding(Long id);
    DataWrapper<Void> updateBuilding(BuildingEntity building);
    DataWrapper<List<BuildingEntity>> getBuildingList();
}
