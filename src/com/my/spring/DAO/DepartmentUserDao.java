package com.my.spring.DAO;

import java.util.List;
import com.my.spring.model.DepartmentUser;

public interface DepartmentUserDao {
	boolean addDepartmentUser(DepartmentUser departmentUser);
    boolean deleteDepartmentUser(Long id);
    DepartmentUser getById(Long id);
    boolean updateDepartmentUser(DepartmentUser departmentUser);
    List<DepartmentUser> getDepartmentUserList(Integer pageSize,Integer pageIndex,DepartmentUser departmentUser);
	boolean deleteDepartmentUserList(String[] ids);
}
