package com.my.spring.DAO;

import com.my.spring.model.User;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface UserDao {
	User getByUserName(String userName);
	User getById(Long id);
	boolean addUser(User user);
	boolean updateUser(User user);
	DataWrapper<List<User>> getUserList(Integer pageSize, Integer pageIndex);
}
