package com.my.spring.DAO;

import com.my.spring.model.User;
import com.my.spring.utils.DataWrapper;

import java.util.List;


public interface UserDao {
	List<User> getByUserNames(String userName);
	User getByUserName(String userName);
	User getById(Long id);
	boolean deleteUser(Long userId);
	boolean addUser(User user);
	boolean updateUser(User user);
	DataWrapper<List<User>> getUserList(Integer pageSize, Integer pageIndex,User user);
	DataWrapper<List<User>> getUserTeam(Integer pageSize, Integer pageIndex,Long projectId);
	DataWrapper<User> findUserLike(User user);
	DataWrapper<List<User>> findUserLikeRealName(String username);
	List<User> findUserLikeProjct(Long projectList,Long id);
}
