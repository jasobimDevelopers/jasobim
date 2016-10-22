package com.my.spring.service;

import com.my.spring.model.RoleEntity;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface RoleService {
	DataWrapper<Void> addRole(RoleEntity role);
    DataWrapper<Void> deleteRole(Long id);
    DataWrapper<Void> updateRole(RoleEntity role);
    DataWrapper<List<RoleEntity>> getRoleList();
}
