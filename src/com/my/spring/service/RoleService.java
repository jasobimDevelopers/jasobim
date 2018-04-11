package com.my.spring.service;

import java.util.List;

import com.my.spring.model.Role;
import com.my.spring.utils.DataWrapper;
public interface RoleService {
	DataWrapper<Void> deleteRoleById(String token,Long id);
	DataWrapper<Void> addRole(String token,Role role);
	DataWrapper<Void> updateRole(String token, Role role);
	DataWrapper<Void> deleteRoleByIdList(String token,String[] id);
	DataWrapper<List<Role>> getRoleList(String token);
}
