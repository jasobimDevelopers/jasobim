package com.my.spring.serviceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.DAO.ContractLoftingDao;
import com.my.spring.DAO.ProjectPartContractLoftingDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserIndexDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ProjectPartContractLofting;
import com.my.spring.model.ProjectPartContractLoftingPojo;
import com.my.spring.model.ContractLoftingMode;
import com.my.spring.model.User;
import com.my.spring.service.ProjectPartContractLoftingService;
import com.my.spring.service.UserIndexService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("projectPartContractLoftingService")
public class ProjectPartContractLoftingServiceImpl implements ProjectPartContractLoftingService  {
	@Autowired
	ProjectPartContractLoftingDao ProjectPartContractLoftingDao;
	@Autowired
	UserIndexService userIndexService;
	@Autowired
	UserIndexDao userIndexDao;
	@Autowired
	UserDao userDao;
	@Autowired
	ContractLoftingDao contractLoftingDao;
	@Override
	public DataWrapper<Void> deleteProjectPartContractLoftingById(String token,Long id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!ProjectPartContractLoftingDao.deleteProjectPartContractLofting(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}else{
				userIndexService.deleteUserIndexByAboutId(0, id);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<ProjectPartContractLofting> addProjectPartContractLoftingList(String token,ProjectPartContractLofting role) {
		DataWrapper<ProjectPartContractLofting> result = new DataWrapper<ProjectPartContractLofting>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				
				List<ProjectPartContractLofting> addList = new ArrayList<ProjectPartContractLofting>();
				List<ContractLoftingMode> getids = contractLoftingDao.getAllContractLofting(role.getProjectId());
				for(int i=0;i<getids.size();i++){
					ProjectPartContractLofting pcl = new ProjectPartContractLofting();
					pcl.setCreateUser(user.getId());
					pcl.setCreateDate(new Date());
					pcl.setProjectId(role.getProjectId());
					pcl.setName(role.getName());
					pcl.setContractLoftingId(getids.get(i).getId());
					pcl.setpName(getids.get(i).getName());
					addList.add(pcl);
				}
				if(!ProjectPartContractLoftingDao.addProjectPartContractLoftingList(addList)){
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
	public DataWrapper<ProjectPartContractLofting> addProjectPartContractLofting(String token,ProjectPartContractLofting role) {
		DataWrapper<ProjectPartContractLofting> result = new DataWrapper<ProjectPartContractLofting>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
					role.setCreateUser(user.getId());
					role.setCreateDate(new Date());
					if(!ProjectPartContractLoftingDao.addProjectPartContractLofting(role)){
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
	public DataWrapper<Void> deleteProjectPartContractLoftingByIdList(String token,String[] id) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!ProjectPartContractLoftingDao.deleteProjectPartContractLoftingList(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


	@Override
	public DataWrapper<List<ProjectPartContractLoftingPojo>> getProjectPartContractLoftingList(Integer pageIndex, Integer pageSize, ProjectPartContractLofting ProjectPartContractLofting,
			String token) {
		DataWrapper<List<ProjectPartContractLoftingPojo>> dp = new DataWrapper<List<ProjectPartContractLoftingPojo>>();
		List<ProjectPartContractLoftingPojo> gets = new ArrayList<ProjectPartContractLoftingPojo>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			gets = ProjectPartContractLoftingDao.getProjectPartContractLoftingList(ProjectPartContractLofting);
			dp.setData(gets);
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dp;
	}

	@Override
	public DataWrapper<ProjectPartContractLofting> getProjectPartContractLoftingById(String token, Long id) {
		DataWrapper<ProjectPartContractLofting> dp = new DataWrapper<ProjectPartContractLofting>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			ProjectPartContractLofting dt = ProjectPartContractLoftingDao.getById(id);
			dp.setData(dt);
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dp;
	}

	@Override
	public DataWrapper<ProjectPartContractLofting> updateProjectPartContractLofting(String token, ProjectPartContractLofting ProjectPartContractLofting) {
		DataWrapper<ProjectPartContractLofting> result = new DataWrapper<ProjectPartContractLofting>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(ProjectPartContractLofting!=null){
				ProjectPartContractLofting dp = new ProjectPartContractLofting();
				dp = ProjectPartContractLoftingDao.getById(ProjectPartContractLofting.getId());
				if(ProjectPartContractLofting.getValue()!=null){
					dp.setValue(ProjectPartContractLofting.getValue());
				}
				if(!ProjectPartContractLoftingDao.updateProjectPartContractLofting(dp)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}else{
					result.setData(dp);
				}
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<ProjectPartContractLoftingPojo>> getDefaultList(Long projectId, String token) {
		DataWrapper<List<ProjectPartContractLoftingPojo>> dp = new DataWrapper<List<ProjectPartContractLoftingPojo>>();
		List<ProjectPartContractLoftingPojo> gets = new ArrayList<ProjectPartContractLoftingPojo>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			gets  = ProjectPartContractLoftingDao.getProjectPartContractLoftingList(projectId);
			dp.setData(gets);
		}else{
			dp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dp;
	}

	@Override
	public DataWrapper<Void> deleteProjectPartContractLoftingByName(String token, String name,Long projectId) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!ProjectPartContractLoftingDao.deleteProjectPartContractLoftingByName(name,projectId)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	
}
