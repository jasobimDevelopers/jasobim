package com.my.spring.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.User;
import com.my.spring.model.UserPojo;
import com.my.spring.utils.DataWrapper;


public interface UserService {
	DataWrapper<Void> register(User user);
	DataWrapper<Void> login(String userName,String password);
	DataWrapper<Void> updateUser(User user,String token);
	DataWrapper<User> getUserDetails(String token);
	DataWrapper<UserPojo> getUserDetailsByAdmin(Long userId,String token);
	DataWrapper<List<UserPojo>> getUserList(Integer pageIndex, Integer pageSize,User user,String token);
	DataWrapper<Void> changeUserTypeByAdmin(Long userId, Integer userType, String token);
	DataWrapper<List<User>> findUserLike(User user,String token);
	DataWrapper<Void> deleteUser(Long userId, String token);
	DataWrapper<Void> addUser(User user,String token, MultipartFile file, HttpServletRequest request);
	DataWrapper<Void> updateUserByAdmin(User user,String token, MultipartFile file,HttpServletRequest request);
}
