package com.my.spring.service;

import com.my.spring.model.UserEntity;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface UserService {
    DataWrapper<Void> addUser(UserEntity user);
    DataWrapper<Void> deleteUser(Long id);
    DataWrapper<Void> updateUser(UserEntity user);
    DataWrapper<List<UserEntity>> getUserList();
    DataWrapper<Void> login(String userName,String password);
}
