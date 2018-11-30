package com.my.spring.serviceImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.spring.DAO.UserMenuLogDao;
import com.my.spring.DAO.MenuListDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.UserMenuLog;
import com.my.spring.model.MenuList;
import com.my.spring.model.User;
import com.my.spring.service.UserMenuLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("UserMenuLogService")
public class UserMenuLogServiceImpl implements UserMenuLogService  {
	@Autowired
	UserMenuLogDao UserMenuLogDao;
	@Autowired
	UserDao userDao;
	@Autowired
	MenuListDao menuListDao;
	@Override
	public DataWrapper<Void> deleteUserMenuLogById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!UserMenuLogDao.deleteUserMenuLog(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<UserMenuLog> addUserMenuLog(String token,UserMenuLog role) {
		DataWrapper<UserMenuLog> result = new DataWrapper<UserMenuLog>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				if(!UserMenuLogDao.addUserMenuLog(role)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}else{
					result.setData(role);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}



	

	@Override
	public DataWrapper<Void> updateUserMenuLog(String token, UserMenuLog UserMenuLog) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(UserMenuLog.getId()!=null){
				UserMenuLog get = UserMenuLogDao.getById(UserMenuLog.getId());
				if(UserMenuLog.getLockStatus()!=null){
					get.setLockStatus(UserMenuLog.getLockStatus());
					UserMenuLogDao.updateUserMenuLog(get);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.Error);
		}
		return result;
	}

	@Override
	public DataWrapper<List<UserMenuLog>> getUserMenuLogList(String toke) {
		// TODO Auto-generated method stub
		DataWrapper<List<UserMenuLog>> result = new DataWrapper<List<UserMenuLog>>();
		List<UserMenuLog> gets = new ArrayList<UserMenuLog>();
		User user = SessionManager.getSession(toke);
		List<UserMenuLog> getDatas = new ArrayList<UserMenuLog>();
		if(user!=null){
			gets = UserMenuLogDao.getUserMenuLogListByUserId(user.getId());
			if(gets.isEmpty()){
				List<MenuList> menuList = new ArrayList<MenuList>(); 
				menuList=menuListDao.getAllMenuList();
				for(int i=0;i<menuList.size();i++){
					UserMenuLog um = new UserMenuLog();
					um.setLockStatus(0);
					um.setUserId(user.getId());
					if(menuList.get(i).getPid()!=null){
						um.setMenuChildId(menuList.get(i).getPid());
					}else{
						um.setMenuPid(menuList.get(i).getId());
					}
					um.setMenuPath(menuList.get(i).getMenuPath());
					getDatas.add(um);
				}
				if(UserMenuLogDao.addUserMenuLogList(getDatas)){
					result.setData(getDatas);
				}
			}else{
				result.setData(gets);
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


	
}
