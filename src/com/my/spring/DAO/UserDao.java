package com.my.spring.DAO;

import com.my.spring.model.UserEntity;
import com.my.spring.utils.DataWrapper;


import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface UserDao {
    boolean addUser(UserEntity user);
    boolean deleteUser(Long id);
    boolean updateUser(UserEntity user);
    DataWrapper<List<UserEntity>> getUserList();
    UserEntity findByUserName(String userName);
    UserEntity getUserById(Long id);
    UserEntity getUserByToken(String token);
}
