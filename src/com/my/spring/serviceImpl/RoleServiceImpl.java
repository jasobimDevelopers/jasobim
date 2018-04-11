package com.my.spring.serviceImpl;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.DAO.RoleDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Role;
import com.my.spring.model.User;
import com.my.spring.service.RoleService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("roleService")
public class RoleServiceImpl implements RoleService  {
	@Autowired
	RoleDao roleDao;
	

	

	@Override
	public DataWrapper<Void> deleteRoleById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!roleDao.deleteRole(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> addRole(String token,Role role) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(user.getUserType()==UserTypeEnum.Admin.getType()){
				role.setCreateDate(new Date());
				role.setCreateUser(user.getId());
				if(!roleDao.addRole(role)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteRoleByIdList(String token,String[] id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!roleDao.deleteRoleList(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> updateRole(String token, Role role) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				Role rl= roleDao.getById(role.getId());
				rl.setUpdateDate(new Date());
				rl.setReadState(role.getReadState());
				rl.setName(role.getName());
				rl.setMenuList(role.getMenuList());
				if(!roleDao.updateRole(rl)){
					role.setUpdateDate(new Date());
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<Role>> getRoleList(String token) {
		DataWrapper<List<Role>> result = new DataWrapper<List<Role>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			result = roleDao.getRoleList();
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
}
