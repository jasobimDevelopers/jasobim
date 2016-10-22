package com.my.spring.DAO;

import com.my.spring.model.RoleEntity;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface RoleDao {
    boolean addRole(RoleEntity role);
    boolean deleteRole(Long id);
    boolean updateRole(RoleEntity role);
    DataWrapper<List<RoleEntity>> getRoleList();
}
