package com.my.spring.DAO;
import com.my.spring.model.Role;
import com.my.spring.model.RoleIndex;
import com.my.spring.model.UserIndexs;
import com.my.spring.utils.DataWrapper;
import java.util.List;
public interface RoleDao {
	Role getById(Long id);
	boolean deleteRole(Long id);
	boolean addRole(Role role);
	boolean updateRole(Role role);
	DataWrapper<List<Role>> getRoleList();
	boolean deleteRoleList(String[] ids);
	boolean deleteRoleByUserId(Long id);
	boolean addRoleList(List<Role> newList2);
	List<RoleIndex> getRoleListByUserId(Long id);
}
