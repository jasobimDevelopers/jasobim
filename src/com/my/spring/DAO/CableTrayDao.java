package com.my.spring.DAO;

import java.util.List;

import com.my.spring.model.CableTray;
import com.my.spring.utils.DataWrapper;

public interface CableTrayDao {
	boolean addCableTray(CableTray CableTray);
    boolean deleteCableTray(Long id);
    boolean updateCableTray(CableTray CableTray);
    DataWrapper<List<CableTray>> getCableTrayList();
	DataWrapper<CableTray> getCableTrayByProjectId(Long projectId);
}
