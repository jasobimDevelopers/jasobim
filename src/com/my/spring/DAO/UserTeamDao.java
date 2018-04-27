package com.my.spring.DAO;
import com.my.spring.model.UserTeam;
import com.my.spring.utils.DataWrapper;
import java.util.List;
public interface UserTeamDao {
	UserTeam getById(Long id);
	boolean deleteUserTeam(Long id);
	boolean addUserTeam(UserTeam role);
	boolean deleteUserTeamList(String[] ids);
	DataWrapper<List<UserTeam>> getUserTeamList(Integer pageSize, Integer pageIndex, UserTeam userTeam);
	boolean updateUserTeamList(UserTeam userTeam);
}
