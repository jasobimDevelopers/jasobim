package com.my.spring.DAO;

import com.my.spring.model.Building;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface BuildingDao {
    boolean addBuilding(Building building);
    boolean deleteBuilding(Long id);
    boolean updateBuilding(Building building);
    DataWrapper<List<Building>> getBuildingList();
	DataWrapper<Building> getBuildingByProjectId(Long projectId);
	boolean deleteBuildingByProjectId(Long id);
}
