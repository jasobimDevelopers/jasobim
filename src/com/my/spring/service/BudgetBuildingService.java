package com.my.spring.service;
import com.my.spring.model.BudgetBuilding;
import com.my.spring.utils.DataWrapper;
import java.util.List;


public interface BudgetBuildingService {
	DataWrapper<BudgetBuilding> addBudgetBuilding(BudgetBuilding m, String token);
	DataWrapper<Void> deleteBudgetBuilding(Long id, String token);
	DataWrapper<Void> updateBudgetBuilding(BudgetBuilding news, String token);
	DataWrapper<List<BudgetBuilding>> getBudgetBuildingList(String token, Integer pageIndex, Integer pageSize,
			BudgetBuilding news);
}
