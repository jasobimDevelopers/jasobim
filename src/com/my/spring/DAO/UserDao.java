package com.my.spring.DAO;

import com.my.spring.model.User;
import com.my.spring.model.UserCopy;
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
	DataWrapper<List<UserCopy>> getUserTeam(Long projectId);
	DataWrapper<User> findUserLike(User user);
	DataWrapper<List<User>> findUserLikeRealName(String username);
	DataWrapper<List<User>> findGetPushUsers(String username,int adminFlag);
	List<User> findUserLikeProjct(Integer id, String userList);
	DataWrapper<List<User>> getUserLists(Integer pageSize, Integer pageIndex, User user);
	DataWrapper<List<User>> getUserListByAdmin(Integer pageSize, Integer pageIndex, User user);
	boolean deleteUserList(String[] userList);
	User getByUserTel(String mobile);
	DataWrapper<List<User>> getUserListForSql(Integer pageSize, Integer pageIndex, User user);
}
