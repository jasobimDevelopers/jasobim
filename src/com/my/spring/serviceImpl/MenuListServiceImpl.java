package com.my.spring.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.spring.DAO.MenuListDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.MenuList;
import com.my.spring.model.MenuListCopy;
import com.my.spring.model.MenuListPojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.MenuListService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MenuNodeUtil;
import com.my.spring.utils.SessionManager;

@Service("menuListService")
public class MenuListServiceImpl implements MenuListService  {
	@Autowired
	MenuListDao menuListDao;
	@Autowired
	UserDao userDao;

	@Override
	public DataWrapper<MenuListPojo> getById(String token,Long id) {
		DataWrapper<MenuListPojo> result = new DataWrapper<MenuListPojo>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			MenuList mu = menuListDao.getById(id);
			if(mu!=null){
				MenuListPojo mp = new MenuListPojo();
				mp.setMenuName(mu.getMenuName());
				mp.setPid(mu.getPid());
				mp.setMenuPath(mu.getMenuPath());
				mp.setRemark(mu.getRemark());
				mp.setCreateDate(Parameters.getSdf().format(mu.getCreateDate()));
				if(mu.getCreateUser()!=null){
					User users = userDao.getById(mu.getCreateUser());
					if(users!=null){
						mp.setCreateUser(users.getRealName());
					}
				}
				result.setData(mp);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteMenuListById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(user.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!menuListDao.deleteMenuList(id)){
						result.setErrorCode(ErrorCodeEnum.Error);
					}
				}else{
					result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
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
	public DataWrapper<Void> addMenuList(String token,MenuList ml) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(user.getUserType()==UserTypeEnum.Admin.getType()){
				if(ml!=null){
					ml.setCreateDate(new Date());
					ml.setCreateUser(user.getId());
					if(!menuListDao.addMenuList(ml)){
						result.setErrorCode(ErrorCodeEnum.Error);
					}
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
	public DataWrapper<Void> deleteMenuListByIdList(String token,String[] id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<String> getMenuListMapByIdList(String token,String[] id) {
		DataWrapper<List<MenuList>> result = new DataWrapper<List<MenuList>>();
		DataWrapper<String> results = new DataWrapper<String>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			result=menuListDao.getMenuListByIdLists(id);
			if(result.getData()!=null)
			{
				@SuppressWarnings("rawtypes")
				List dataList = new ArrayList();  
				for(MenuList ss:result.getData()){
					@SuppressWarnings("rawtypes")
					HashMap dataRecord1 = new HashMap();  
					dataRecord1.put("id", ss.getId().toString());
					dataRecord1.put("menuPath", ss.getMenuPath());
					dataRecord1.put("name", ss.getMenuName());
					if(ss.getPid()==null){
						dataRecord1.put("pid", "");
					}else{
						dataRecord1.put("pid", ss.getPid().toString());
					}
					String str=Parameters.getSdf().format(ss.getCreateDate());
					dataRecord1.put("createDate", str);
					if(ss.getCreateUser()!=null){
						User userss = userDao.getById(ss.getCreateUser());
						if(userss!=null){
							String userName=userss.getUserName();
							dataRecord1.put("createUser", userName);
						}else{
							dataRecord1.put("createUser", "");
						}
					}
					dataList.add(dataRecord1);
				}
				MenuNodeUtil nodeUtil = new MenuNodeUtil();
				String resultString=nodeUtil.getJasonStringOfMenu(dataList);
				results.setData(resultString);
			}else{
				results.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
			}
		}else{
			results.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return results;
	}
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<String> getMenuListMapByIdList(String[] id) {
		DataWrapper<List<MenuList>> result = new DataWrapper<List<MenuList>>();
		DataWrapper<String> results = new DataWrapper<String>();
		result=menuListDao.getMenuListByIdLists(id);
		if(result.getData()!=null)
		{
			@SuppressWarnings("rawtypes")
			List dataList = new ArrayList();  
			for(MenuList ss:result.getData()){
				@SuppressWarnings("rawtypes")
				HashMap dataRecord1 = new HashMap();  
				dataRecord1.put("id", ss.getId().toString());
				dataRecord1.put("menuPath", ss.getMenuPath());
				dataRecord1.put("name", ss.getMenuName());
				if(ss.getPid()==null){
					dataRecord1.put("pid", "");
				}else{
					dataRecord1.put("pid", ss.getPid().toString());
				}
				String str=Parameters.getSdf().format(ss.getCreateDate());
				dataRecord1.put("createDate", str);
				if(ss.getCreateUser()!=null){
					User userss = userDao.getById(ss.getCreateUser());
					if(userss!=null){
						String userName=userss.getUserName();
						dataRecord1.put("createUser", userName);
					}else{
						dataRecord1.put("createUser", "");
					}
				}
				
				dataList.add(dataRecord1);
			}
			MenuNodeUtil nodeUtil = new MenuNodeUtil();
			String resultString=nodeUtil.getJasonStringOfMenu(dataList);
			results.setData(resultString);
		}else{
			results.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
		}
		return results;
	}

	@Override
	public DataWrapper<List<MenuList>> getMenuLists(String token) {
		DataWrapper<List<MenuList>> result = new DataWrapper<List<MenuList>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			result=menuListDao.getMenuLists();
			if(result.getData()==null)
			{
				result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<String> getMenuListMapByIdList(List<MenuListCopy> menu) {
		DataWrapper<String> result = new DataWrapper<String>();
		List<MenuListCopy> mpp=menuListDao.getMenuParrentByChild(menu);
		@SuppressWarnings("rawtypes")
		List dataList = new ArrayList();  
		if(menu.size()>0)
		{
			if(mpp!=null){
				for(MenuListCopy ssp:mpp){
					@SuppressWarnings("rawtypes")
					HashMap dataRecord1p = new HashMap();  
					dataRecord1p.put("id", ssp.getId().toString());
					dataRecord1p.put("menuPath", ssp.getMenuPath());
					dataRecord1p.put("name", ssp.getMenuName());
					if(ssp.getPid()==null){
						dataRecord1p.put("pid", "");
					}else{
						dataRecord1p.put("pid", ssp.getPid().toString());
					}
					String str=Parameters.getSdf().format(ssp.getCreateDate());
					dataRecord1p.put("createDate", str);
					if(ssp.getCreateUser()!=null){
						User userss = userDao.getById(ssp.getCreateUser());
						if(userss!=null){
							String userName=userss.getUserName();
							dataRecord1p.put("createUser", userName);
						}else{
							dataRecord1p.put("createUser", "");
						}
					}
					dataList.add(dataRecord1p);
				}
			}
			for(MenuListCopy ss:menu){
				@SuppressWarnings("rawtypes")
				HashMap dataRecord1 = new HashMap();  
				dataRecord1.put("id", ss.getId().toString());
				dataRecord1.put("menuPath", ss.getMenuPath());
				dataRecord1.put("name", ss.getMenuName());
				if(ss.getPid()==null){
					dataRecord1.put("pid", "");
				}else{
					dataRecord1.put("pid", ss.getPid().toString());
				}
				String str=Parameters.getSdf().format(ss.getCreateDate());
				dataRecord1.put("createDate", str);
				if(ss.getCreateUser()!=null){
					User userss = userDao.getById(ss.getCreateUser());
					if(userss!=null){
						String userName=userss.getUserName();
						dataRecord1.put("createUser", userName);
					}else{
						dataRecord1.put("createUser", "");
					}
				}
				
				dataList.add(dataRecord1);
			}
			MenuNodeUtil nodeUtil = new MenuNodeUtil();
			String resultString=nodeUtil.getJasonStringOfMenu(dataList);
			result.setData(resultString);
		}else{
			result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> updateMenu(String token, MenuList item) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(user.getUserType()==UserTypeEnum.Admin.getType()){
				if(item!=null){
					MenuList ml = new MenuList();
					ml=menuListDao.getById(ml.getId());
					if(item.getMenuName()!=null){
						ml.setMenuName(item.getMenuName());
					}
					if(item.getMenuPath()!=null){
						ml.setMenuPath(item.getMenuPath());
					}
					if(item.getPid()!=null){
						ml.setPid(item.getPid());
					}
					ml.setUpdateDate(new Date());
					if(!menuListDao.updateMenuList(ml)){
						result.setErrorCode(ErrorCodeEnum.Error);
					}
				}else{
					result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

}
