package com.my.spring.serviceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.my.spring.DAO.MenuListDao;
import com.my.spring.DAO.RoleDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserIndexDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.MaxIndex;
import com.my.spring.model.MenuListCopy;
import com.my.spring.model.Role;
import com.my.spring.model.RoleIndex;
import com.my.spring.model.RolePojo;
import com.my.spring.model.User;
import com.my.spring.model.UserIndex;
import com.my.spring.model.UserIndexUserId;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.RoleService;
import com.my.spring.service.UserIndexService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MenuNodeUtil;
import com.my.spring.utils.SessionManager;

@Service("roleService")
public class RoleServiceImpl implements RoleService  {
	@Autowired
	RoleDao roleDao;
	@Autowired
	MenuListDao menuDao;
	@Autowired
	UserDao userDao;
	@Autowired
	UserIndexDao userIndexDao;
	@Autowired
	UserIndexService userIndexService;
	@Override
	public DataWrapper<Void> deleteRoleById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!roleDao.deleteRole(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}else{
				userIndexService.deleteUserIndexByAboutId(1, id);
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
				}else{
					List<UserIndex> indexList = new ArrayList<UserIndex>();
					List<UserIndexUserId> idList=userIndexDao.getUserIndexListByGroup();
					if(!idList.isEmpty()){
						for(int i=0;i<idList.size();i++){
							MaxIndex max=userIndexDao.getIndexMaxByUserId(idList.get(i).getId(),1);
							UserIndex userIndex = new UserIndex();
							userIndex.setAboutType(1);
							userIndex.setIndexs(max.getIndexs()+1);
							userIndex.setUserId(idList.get(i).getId());
							userIndex.setAboutId(role.getId());
							indexList.add(userIndex);
						}
						userIndexDao.addUserIndexList(indexList);
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

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<RolePojo>> getRoleList(String token) {
		DataWrapper<List<RolePojo>> result = new DataWrapper<List<RolePojo>>();
		List<RolePojo> resultList = new ArrayList<RolePojo>();
		DataWrapper<List<Role>> results = new DataWrapper<List<Role>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			UserIndex userIndex = new UserIndex();
			userIndex.setAboutType(1);
			userIndex.setUserId(user.getId());
			int i=1;
			List<RoleIndex> get=roleDao.getRoleListByUserId(user.getId());
			if(get.isEmpty()){
				results = roleDao.getRoleList();
				if(results.getData()!=null){
					if(results.getData().size()>0){
						for(Role rs:results.getData()){
							RolePojo rp = new RolePojo();
							rp.setCreateDate(Parameters.getSdf().format(rs.getCreateDate()));
							rp.setIndexs((long)i);
							rp.setId(rs.getId());
							rp.setReadState(rs.getReadState());
							rp.setName(rs.getName());
							i++;
							rp.setCreateUser(userDao.getById(rs.getCreateUser()).getRealName());
							if(rs.getMenuList()!=null){
								List<MenuListCopy> mp=menuDao.getMenuListByIdList(rs.getMenuList().split(",")).getData();
								List<MenuListCopy> mpp=menuDao.getMenuParrentByChild(mp);
								@SuppressWarnings("rawtypes")
								List dataList = new ArrayList();  
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
								if(mp!=null){
									for(MenuListCopy ss:mp){
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
									rp.setMenuList(JSONArray.parse(resultString));
								}
							}
							resultList.add(rp);
						}
						result.setData(resultList);
					}
				}
			}else{
				for(RoleIndex rs:get){
					RolePojo rp = new RolePojo();
					rp.setCreateDate(Parameters.getSdf().format(rs.getCreateDate()));
					rp.setId(rs.getId());
					rp.setIndexs(rs.getIndexs());
					rp.setReadState(rs.getReadState());
					rp.setName(rs.getName());
					rp.setCreateUser(userDao.getById(rs.getCreateUser()).getRealName());
					if(rs.getMenuList()!=null){
						List<MenuListCopy> mp=menuDao.getMenuListByIdList(rs.getMenuList().split(",")).getData();
						List<MenuListCopy> mpp=menuDao.getMenuParrentByChild(mp);
						@SuppressWarnings("rawtypes")
						List dataList = new ArrayList();  
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
						if(mp!=null){
							for(MenuListCopy ss:mp){
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
							rp.setMenuList(JSONArray.parse(resultString));
						}
					}
					resultList.add(rp);
				}
				result.setData(resultList);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

}
