package com.my.spring.DAO;

import com.my.spring.model.BudgetBuilding;
import com.my.spring.utils.DataWrapper;

import java.util.List;


public interface BudgetBuildingDao {
	BudgetBuilding getById(Long id);
    DataWrapper<List<BudgetBuilding>> getBudgetBuildingList(Integer pageIndex, Integer pageSize, BudgetBuilding m);
	boolean addBudgetBuilding(BudgetBuilding m);
	boolean deleteBudgetBuilding(Long id);
	boolean updateBudgetBuilding(BudgetBuilding m);
}
