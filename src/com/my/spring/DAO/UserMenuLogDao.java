package com.my.spring.DAO;
import com.my.spring.model.UserMenuLog;
import java.util.List;
public interface UserMenuLogDao {
	UserMenuLog getById(Long id);
	boolean deleteUserMenuLog(Long id);
	boolean addUserMenuLog(UserMenuLog role);
	boolean updateUserMenuLog(UserMenuLog dp);
	List<UserMenuLog> getUserMenuLogListByUserId(Long userId);
	boolean addUserMenuLogList(List<UserMenuLog> gets);
}
