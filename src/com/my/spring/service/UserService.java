package com.my.spring.service;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.my.spring.model.User;
import com.my.spring.model.UserPadPojo;
import com.my.spring.model.UserPojo;
import com.my.spring.utils.DataWrapper;


public interface UserService {
	
	DataWrapper<Void> register(User user, HttpServletRequest request);
	DataWrapper<UserPojo> login(String userName,String password, Integer systemId);
	DataWrapper<Void> updateUser(User user,String token);
	DataWrapper<User> getUserDetails(String token);
	DataWrapper<UserPojo> getUserDetailsByAdmin(Long userId,String token);
	DataWrapper<List<UserPojo>> getUserList(Integer pageIndex, Integer pageSize,User user,String token);
	DataWrapper<Void> changeUserTypeByAdmin(Long userId, Integer userType, String token);
	DataWrapper<User> findUserLike(User user,String token);
	DataWrapper<Void> addUser(User user,String token, MultipartFile file, HttpServletRequest request);
	DataWrapper<String> updateUserByAdmin(User user,String token, MultipartFile file,HttpServletRequest request);
	DataWrapper<User> FindPs(User user);
	DataWrapper<Void> updateUserBySelf(String oldPs,String newPs,String token);
	DataWrapper<List<UserPadPojo>> getUserTeam(String token,Long projectId);
	DataWrapper<List<UserPojo>> getUserLists(Integer pageIndex, Integer pageSize, User user, String token);
	DataWrapper<Object> getUserInfo(String token);
	DataWrapper<Void> deleteUser(Long userId, String token, String userIdList);
	DataWrapper<Void> getIdentifyingCode(String mobile,Integer systemType);
	DataWrapper<Void> getIdentifyingInfo(String mobile, String code);
	DataWrapper<Void> registerUserInfo(User user);
	DataWrapper<Void> getUserInfoSql(User user);
	
}
