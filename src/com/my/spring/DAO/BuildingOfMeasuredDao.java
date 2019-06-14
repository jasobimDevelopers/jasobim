package com.my.spring.DAO;

import com.my.spring.model.BuildingOfMeasured;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface BuildingOfMeasuredDao {
    boolean addBuildingOfMeasured(BuildingOfMeasured building);
    boolean deleteBuildingOfMeasured(Long id);
    boolean updateBuildingOfMeasured(BuildingOfMeasured building);
    DataWrapper<List<BuildingOfMeasured>> getBuildingOfMeasuredList();
	DataWrapper<List<BuildingOfMeasured>> getBuildingOfMeasuredByProjectId(Long projectId);
	BuildingOfMeasured getBuildingOfMeasuredById(Long bfmId);
}
