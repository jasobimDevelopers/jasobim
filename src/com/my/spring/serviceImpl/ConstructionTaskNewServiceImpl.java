package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ConstructionTaskNewDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.ConstructionTaskNew;
import com.my.spring.model.ConstructionTaskNewPojo;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.service.ConstructionTaskNewService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
@Service("constructionTaskNewService")
public class ConstructionTaskNewServiceImpl implements ConstructionTaskNewService {
    @Autowired
    ConstructionTaskNewDao ConstructionTaskNewDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserLogService userLogSerivce;
    @Override
    public DataWrapper<Void> addConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(ConstructionTaskNew!=null){
				ConstructionTaskNew.setCreateUser(userInMemory.getId());
				ConstructionTaskNew.setCreateDate(new Date(System.currentTimeMillis()));
				if(!ConstructionTaskNewDao.addConstructionTaskNew(ConstructionTaskNew)) 
				{
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteConstructionTaskNew(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!ConstructionTaskNewDao.deleteConstructionTaskNew(id)){
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateConstructionTaskNew(ConstructionTaskNew ConstructionTaskNew,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				ConstructionTaskNew.setCreateUser(userInMemory.getId());
				if(!ConstructionTaskNewDao.updateConstructionTaskNew(ConstructionTaskNew)) {
				    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<ConstructionTaskNewPojo>> getConstructionTaskNewList(String token , Integer pageIndex, Integer pageSize, ConstructionTaskNew ConstructionTaskNew,Integer type) {
    	DataWrapper<List<ConstructionTaskNewPojo>> dataWrappers = new DataWrapper<List<ConstructionTaskNewPojo>>();
    	DataWrapper<List<ConstructionTaskNew>> dataWrapper = new DataWrapper<List<ConstructionTaskNew>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
	        	if(ConstructionTaskNew.getId()!=null){
	        		UserLog userLog = new UserLog();
	        		userLog.setActionDate(new Date());
	        		userLog.setFileId(ConstructionTaskNew.getId());
	        		userLog.setProjectPart(6);
	        		userLog.setUserId(userInMemory.getId());
	        		userLog.setVersion("-1");
	        		userLogSerivce.addUserLog(userLog, token);
	        	}
        		if(type!=null){
        			if(type==0){
        				ConstructionTaskNew.setName("施工任务单");
        			}
        			if(type==1){
        				ConstructionTaskNew.setName("预付单");
        			}
        		}
				dataWrapper=ConstructionTaskNewDao.getConstructionTaskNewList(pageIndex,pageSize,ConstructionTaskNew);
				if(dataWrapper.getData()!=null){
					List<ConstructionTaskNewPojo> ConstructionTaskNewPojoList = new ArrayList<ConstructionTaskNewPojo>();
					for(int i=0;i<dataWrapper.getData().size();i++){
						ConstructionTaskNewPojo ConstructionTaskNewPojo =new ConstructionTaskNewPojo();
						ConstructionTaskNewPojo.setId(dataWrapper.getData().get(i).getId());
						if(dataWrapper.getData().get(i).getCreateUser()!=null){
							User user= new User();
							user=userDao.getById(dataWrapper.getData().get(i).getCreateUser());
							if(user!=null){
								ConstructionTaskNewPojo.setCreateUser(user.getUserName());
							}
							
						}
						if(ConstructionTaskNewPojo!=null){
							ConstructionTaskNewPojoList.add(ConstructionTaskNewPojo);
						}
					}
					if(ConstructionTaskNewPojoList!=null && ConstructionTaskNewPojoList.size()>0){
						dataWrappers.setData(ConstructionTaskNewPojoList);
						dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
						dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
						dataWrappers.setTotalPage(dataWrapper.getTotalPage());
						dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
					}else{
						dataWrappers.setErrorCode(ErrorCodeEnum.Error);
					}
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
        return dataWrappers;
    }


}
