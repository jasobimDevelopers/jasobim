package com.my.spring.service;


import java.util.List;

import com.my.spring.model.User;
import com.my.spring.utils.DataWrapper;


public interface UserService {
	DataWrapper<Void> register(User user);
	DataWrapper<Void> login(String userName,String password);
	DataWrapper<Void> updateUser(User user,String token);
	DataWrapper<User> getUserDetails(String token);
	DataWrapper<User> getUserDetailsByAdmin(Long userId,String token);
	DataWrapper<List<User>> getUserList(Integer pageIndex, Integer pageSize,String token);
	DataWrapper<Void> changeUserTypeByAdmin(Long userId, Integer userType, String token);

}
