package com.my.spring.DAO;
import com.my.spring.model.MaterialPlan;
import com.my.spring.utils.DataWrapper;
import java.util.List;
public interface MaterialPlanDao {
	MaterialPlan getById(Long id);
	DataWrapper<MaterialPlan> getByName(String name);
	boolean deleteMaterialPlan(Long id);
	boolean addMaterialPlan(MaterialPlan file);
	DataWrapper<List<MaterialPlan>> getMaterialPlanList(MaterialPlan floder,String dates);
	boolean updateMaterialPlan(MaterialPlan floder);
	List<MaterialPlan> getAllMaterialPlan();
	boolean deleteMaterialPlanList(String[] ids);
	List<MaterialPlan> getMaterialPlanListLike(Long projectId, String name);
	List<MaterialPlan> getMaterialPlanIndexList(Long projectId, Long pid);
}
