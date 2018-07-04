package com.my.spring.serviceImpl;

import com.my.spring.DAO.ProcessItemDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.ProcessItem;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.service.ProcessItemService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service("ProcessItemService")
public class ProcessItemServiceImpl implements ProcessItemService {
    @Autowired
    ProcessItemDao ProcessItemDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserLogService userLogSerivce;
    @Override
    public DataWrapper<Void> addProcessItem(ProcessItem ProcessItem,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(ProcessItem!=null){
				if(!ProcessItemDao.addProcessItem(ProcessItem)) 
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
    public DataWrapper<Void> deleteProcessItem(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!ProcessItemDao.deleteProcessItem(id)){
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
    public DataWrapper<List<ProcessItem>> getProcessItemList(String token , Integer pageIndex, Integer pageSize, ProcessItem ProcessItem) {
    	DataWrapper<List<ProcessItem>> dataWrapper = new DataWrapper<List<ProcessItem>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	if(ProcessItem.getId()!=null){
        		UserLog userLog = new UserLog();
        		userLog.setActionDate(new Date());
        		userLog.setFileId(ProcessItem.getId());
        		userLog.setProjectPart(6);
        		userLog.setUserId(userInMemory.getId());
        		userLog.setVersion("-1");
        		userLogSerivce.addUserLog(userLog, token);
        	}
			dataWrapper=ProcessItemDao.getProcessItemList(pageIndex,pageSize,ProcessItem);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

	
	

	
}
