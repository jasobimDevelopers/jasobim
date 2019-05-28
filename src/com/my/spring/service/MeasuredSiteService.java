package com.my.spring.service;

import com.my.spring.model.MeasuredSite;
import com.my.spring.utils.DataWrapper;
public interface MeasuredSiteService {
    DataWrapper<Void> addMeasuredSite(MeasuredSite building, String token,String siteDetail);
    DataWrapper<Void> deleteMeasuredSite(Long id,String token);
    DataWrapper<Void> updateMeasuredSite(MeasuredSite building,String token);
	DataWrapper<MeasuredSite> getMeasuredSiteListByBuildingId(Long buildingId,String token);
}
