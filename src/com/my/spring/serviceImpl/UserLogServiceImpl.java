package com.my.spring.serviceImpl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.DAO.UserLogDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.UserLog;
import com.my.spring.model.UserLogPojo;
import com.my.spring.model.User;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;


@Service("userLogService")
public class UserLogServiceImpl implements UserLogService {
	@Autowired
	UserLogDao UserLogDao;
	@Autowired
	UserDao userDao;
	@Autowired
	ProjectDao projectDao;
	@Override
	public DataWrapper<List<UserLogPojo>> getUserLogList(Integer pageIndex, Integer pageSize, UserLog UserLog, String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<UserLog>> dataWrapper = new DataWrapper<List<UserLog>>();
		List<UserLogPojo> UserLogpojo = new ArrayList<UserLogPojo>();
		String[] projectPart={"模型区域","图纸区域","登录区域","交底区域","预制化区域 ","紧急事项区域","通知区域","产值区域","班组信息区域"};
		//#0.模型区域 1.图纸区域 2.登录区域 3.交底区域 4.预制化区域 5.其他
		String[] systemName={"苹果系统","安卓系统"};
		DataWrapper<List<UserLogPojo>> dataWrapperpojo = new DataWrapper<List<UserLogPojo>>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null) {
				dataWrapper=UserLogDao.getUserLogList(pageSize, pageIndex,UserLog);
				if(dataWrapper.getData()!=null){
					for(int i=0;i<dataWrapper.getData().size();i++){
						UserLogPojo UserLogpojos=new UserLogPojo();
						UserLogpojos.setId(dataWrapper.getData().get(i).getId());
						UserLogpojos.setUserName(userDao.getById(dataWrapper.getData().get(i).getUserId()).getUserName());
						UserLogpojos.setProjectName(projectDao.getById(dataWrapper.getData().get(i).getProjectId()).getName());
						DateFormat df2 = DateFormat.getDateTimeInstance();//可以精确到时分秒 
	    	    		UserLogpojos.setActionDate(df2.format(dataWrapper.getData().get(i).getActionDate()));
	    	    		UserLogpojos.setProjectPart(projectPart[dataWrapper.getData().get(i).getProjectPart()]);
	    	    		UserLogpojos.setSystemType(systemName[dataWrapper.getData().get(i).getSystemType()]);
	    	    		UserLogpojos.setVersion(dataWrapper.getData().get(i).getVersion());
	    	    		UserLogpojo.add(UserLogpojos);
					}
					dataWrapperpojo.setData(UserLogpojo);
					dataWrapperpojo.setCallStatus(dataWrapper.getCallStatus());
					dataWrapperpojo.setCurrentPage(dataWrapper.getCurrentPage());
					dataWrapperpojo.setNumberPerPage(dataWrapper.getNumberPerPage());
					dataWrapperpojo.setTotalNumber(dataWrapper.getTotalNumber());
					dataWrapperpojo.setTotalPage(dataWrapper.getTotalPage());
					dataWrapperpojo.setErrorCode(dataWrapper.getErrorCode());
				}
		} else {
			dataWrapperpojo.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapperpojo;
	}


	@Override
	public DataWrapper<Void> deleteUserLog(String ids, String token) {
		DataWrapper<Void> dataWrapper=new DataWrapper<Void>();
		User userInMemory=SessionManager.getSession(token);
		String[] idList=null;
		idList=ids.split(",");
		if(userInMemory!=null){
			User adminInDB = userDao.getById(userInMemory.getId());
			if (adminInDB.getUserType() == UserTypeEnum.Admin.getType()) {
				if(UserLogDao.deleteUserLog(idList)){
					dataWrapper.setErrorCode(ErrorCodeEnum.No_Error);
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> addUserLog(UserLog userLog,String token) {
		DataWrapper<Void> dataWrapper = new DataWrapper<>();
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			userLog.setActionDate(new Date());
			userLog.setUserId(userInMemory.getId());
			if(!UserLogDao.addUserLog(userLog)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		return dataWrapper;
	}


	

}
