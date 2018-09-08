package com.my.spring.service;

import java.util.List;

import com.my.spring.model.Department;
import com.my.spring.model.DepartmentPojo;
import com.my.spring.utils.DataWrapper;
public interface DepartmentService {
	DataWrapper<Void> deleteDepartmentById(String token,Long id);
	DataWrapper<Department> addDepartment(String token,Department role);
	DataWrapper<Void> deleteDepartmentByIdList(String token,String[] id);
	DataWrapper<List<DepartmentPojo>> getDepartmentList(Integer pageIndex, Integer pageSize, Department department,
			String token);
	DataWrapper<DepartmentPojo> getDepartmentById(String token, Long id);
	DataWrapper<Void> updateDepartment(String token, Department department);
}
