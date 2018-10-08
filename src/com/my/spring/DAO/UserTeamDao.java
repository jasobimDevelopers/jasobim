package com.my.spring.DAO;
import com.my.spring.model.RoleIndex;
import com.my.spring.model.UserTeam;
import com.my.spring.model.UserTeamIndex;
import com.my.spring.utils.DataWrapper;
import java.util.List;
public interface UserTeamDao {
	UserTeam getById(Long id);
	boolean deleteUserTeam(Long id);
	boolean addUserTeam(UserTeam role);
	boolean deleteUserTeamList(String[] ids);
	DataWrapper<List<UserTeam>> getUserTeamList(Integer pageSize, Integer pageIndex, UserTeam userTeam);
	boolean updateUserTeamList(UserTeam userTeam);
	boolean deleteUserTeamByUserId(Long id);
	boolean addUserTeamList(List<UserTeam> newList2);
	List<UserTeamIndex> getUserTeamListByUserId(Long id);
}
