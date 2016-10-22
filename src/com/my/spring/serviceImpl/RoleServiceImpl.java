package com.my.spring.serviceImpl;

import com.my.spring.DAO.RoleDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.RoleEntity;
import com.my.spring.service.RoleService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao RoleDao;
    @Override
    public DataWrapper<Void> addRole(RoleEntity role) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!RoleDao.addRole(role)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteRole(Long id) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!RoleDao.deleteRole(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateRole(RoleEntity role) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!RoleDao.updateRole(role)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<RoleEntity>> getRoleList() {
        return RoleDao.getRoleList();
    }
}
