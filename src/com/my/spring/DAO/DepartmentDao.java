package com.my.spring.DAO;
import com.my.spring.model.Department;
import com.my.spring.utils.DataWrapper;
import java.util.List;
public interface DepartmentDao {
	Department getById(Long id);
	boolean deleteDepartment(Long id);
	boolean addDepartment(Department role);
	boolean deleteDepartmentList(String[] ids);
	DataWrapper<List<Department>> getDepartmentList(Integer pageIndex, Integer pageSize, Department department);
}
