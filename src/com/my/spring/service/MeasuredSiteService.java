package com.my.spring.service;

import java.util.List;

import com.my.spring.model.MeasuredSite;
import com.my.spring.utils.DataWrapper;
public interface MeasuredSiteService {
    DataWrapper<Void> addMeasuredSite(MeasuredSite building, String token,String siteDetail);
    DataWrapper<Void> deleteMeasuredSite(Long id,String token);
	DataWrapper<List<MeasuredSite>> getMeasuredSiteListByBuildingId(Long buildingId,String token);
	DataWrapper<Void> updateMeasuredSite(Long id, String token, String siteNewName, Long paperOfMeasuredId,Long checkUser);
}
