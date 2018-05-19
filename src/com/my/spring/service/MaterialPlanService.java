package com.my.spring.service;
import java.text.ParseException;
import java.util.List;


import com.my.spring.model.MaterialPlan;
import com.my.spring.model.MaterialPlanPojo;
import com.my.spring.utils.DataWrapper;

public interface MaterialPlanService {
	MaterialPlan getById(Long id);
	DataWrapper<Void> addMaterialPlan(String token, MaterialPlan floder, String start, String end) throws ParseException;
	DataWrapper<List<MaterialPlanPojo>> getMaterialPlanIndexList(String token, Long projectId, Long pid);
	DataWrapper<Void> updateMaterialPlan(String token, MaterialPlan mp);
	DataWrapper<Object> getMaterialPlanList(String token, MaterialPlan mp, String dates);
	DataWrapper<Void> deleteMaterialPlan(String token, Long id);
}
