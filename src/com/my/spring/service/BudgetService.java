package com.my.spring.service;
import com.my.spring.model.Budget;
import com.my.spring.model.BudgetPojo;
import com.my.spring.utils.DataWrapper;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
public interface BudgetService {
    DataWrapper<List<BudgetPojo>> getBudgetList(String token, Integer pageIndex, Integer pageSize, Budget m);
	DataWrapper<Void> addBudget(Budget m, String token);
	DataWrapper<Void> deleteBudget(Long id, String token);
	DataWrapper<Void> updateBudget(Budget m, String token);
	DataWrapper<Void> importBudget(MultipartFile file, HttpServletRequest request, String token, Budget Budget);
}
