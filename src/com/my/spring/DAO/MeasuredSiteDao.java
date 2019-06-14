package com.my.spring.DAO;

import java.util.List;

import com.my.spring.model.MeasuredSite;
import com.my.spring.utils.DataWrapper;

public interface MeasuredSiteDao {
    boolean addMeasuredSite(MeasuredSite building);
    boolean deleteMeasuredSite(Long id);
    boolean updateMeasuredSite(MeasuredSite building);
	DataWrapper<List<MeasuredSite>> getMeasuredSiteListByBuildingId(Long buildingId);
	boolean addMeasuredSiteList(List<MeasuredSite> building);
	MeasuredSite getById(Long id);
}
