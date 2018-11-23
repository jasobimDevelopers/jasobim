package com.my.spring.DAO;

import com.my.spring.model.Budget;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface BudgetDao {
    Budget getById(Long id);
    DataWrapper<List<Budget>> getBudgetList(Integer pageIndex, Integer pageSize, Budget m);
	boolean addBudget(Budget m);
	boolean deleteBudget(Long id);
	boolean updateBudget(Budget m);
	boolean addBudgetList(List<Budget> mst);
}
