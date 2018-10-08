package com.my.spring.serviceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserIndexDao;
import com.my.spring.DAO.UserTeamDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.UserTeam;
import com.my.spring.model.UserTeamIndex;
import com.my.spring.model.UserTeamPojo;
import com.my.spring.parameters.Parameters;
import com.my.spring.model.MaxIndex;
import com.my.spring.model.User;
import com.my.spring.model.UserIndex;
import com.my.spring.model.UserIndexUserId;
import com.my.spring.service.UserIndexService;
import com.my.spring.service.UserTeamService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("userTeamService")
public class UserTeamServiceImpl implements UserTeamService  {
	@Autowired
	UserTeamDao userTeamDao;
	@Autowired
	UserDao userDao;
	@Autowired
	UserIndexDao userIndexDao;
	@Autowired
	UserIndexService userIndexService;
	@Override
	public DataWrapper<Void> deleteUserTeamById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!userTeamDao.deleteUserTeam(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}else{
				userIndexService.deleteUserIndexByAboutId(2, id);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<UserTeam> addUserTeam(String token,UserTeam role) {
		DataWrapper<UserTeam> result = new DataWrapper<UserTeam>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				role.setCreateDate(new Date());
				role.setCreateUser(user.getId());
				if(!userTeamDao.addUserTeam(role)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}else{
					result.setData(role);
					List<UserIndex> indexList = new ArrayList<UserIndex>();
					List<UserIndexUserId> idList=userIndexDao.getUserIndexListByGroup();
					if(!idList.isEmpty()){
						for(int i=0;i<idList.size();i++){
							MaxIndex max=userIndexDao.getIndexMaxByUserId(idList.get(i).getId());
							UserIndex userIndex = new UserIndex();
							userIndex.setAboutType(2);
							userIndex.setIndexs(max.getIndexs()+1);
							userIndex.setUserId(idList.get(i).getId());
							userIndex.setAboutId(role.getId());
							indexList.add(userIndex);
						}
						userIndexDao.addUserIndexList(indexList);
					}
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteUserTeamByIdList(String token,String[] id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!userTeamDao.deleteUserTeamList(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<UserTeamPojo>> getUserTeamList(Integer pageIndex, Integer pageSize,
			UserTeam item, String token) {
		DataWrapper<List<UserTeamPojo>> result = new DataWrapper<List<UserTeamPojo>>();
		DataWrapper<List<UserTeam>> results = new DataWrapper<List<UserTeam>>();
		List<UserTeamPojo> resultsp = new ArrayList<UserTeamPojo>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			List<UserTeamIndex> get=userTeamDao.getUserTeamListByUserId(user.getId());
			if(get.isEmpty()){
				results=userTeamDao.getUserTeamList(pageSize, pageIndex, item);
				if(results.getData()!=null)
				{
					if(results.getData().size()>0){
						int indexs=1;
						for(UserTeam ut:results.getData()){
							UserTeamPojo up = new UserTeamPojo();
							up.setId(ut.getId());
							up.setIndexs((long)indexs);
							up.setRemark(ut.getRemark());
							up.setTeamUserName(ut.getTeamUserName());
							up.setProjectId(ut.getProjectId());
							up.setName(ut.getName());
							up.setCreateDate(Parameters.getSdf().format(ut.getCreateDate()));
							if(ut.getCreateUser()!=null){
								up.setCreateUser(userDao.getById(ut.getCreateUser()).getRealName());
							}
							resultsp.add(up);
							indexs++;
						}
					}
					result.setData(resultsp);
				}else{
					result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
				}
			}else{
				if(get.size()>0){
					for(UserTeamIndex ut:get){
						UserTeamPojo up = new UserTeamPojo();
						up.setId(ut.getId());
						up.setTeamUserName(ut.getTeamUserName());
						up.setIndexs(ut.getIndexs());
						up.setProjectId(ut.getProjectId());
						up.setName(ut.getName());
						up.setCreateDate(Parameters.getSdf().format(ut.getCreateDate()));
						if(ut.getCreateUser()!=null){
							up.setCreateUser(userDao.getById(ut.getCreateUser()).getRealName());
						}
						resultsp.add(up);
					}
				}
				result.setData(resultsp);
			}

		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> updateUserTeam(String token, UserTeam userTeam) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(userTeam!=null){
				UserTeam ut = userTeamDao.getById(userTeam.getId());
				if(userTeam.getName()!=null){
					ut.setName(userTeam.getName());
					ut.setUpdateDate(new Date());
				}
				if(userTeam.getTeamUserName()!=null){
					ut.setTeamUserName(userTeam.getTeamUserName());
				}
				if(!userTeamDao.updateUserTeamList(ut)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


}
