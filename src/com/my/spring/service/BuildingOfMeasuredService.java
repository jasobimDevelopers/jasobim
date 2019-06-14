package com.my.spring.service;

import java.util.List;

import com.my.spring.model.BuildingOfMeasured;
import com.my.spring.utils.DataWrapper;

public interface BuildingOfMeasuredService {
    DataWrapper<Void> addBuildingOfMeasured(BuildingOfMeasured building, String token);
    DataWrapper<Void> deleteBuildingOfMeasured(Long id,String token);
    DataWrapper<Void> updateBuildingOfMeasured(BuildingOfMeasured building,String token);
	DataWrapper<List<BuildingOfMeasured>> getBuildingOfMeasuredByProjectId(Long projectId,String token);
}
