package com.my.spring.serviceImpl;

import com.my.spring.DAO.ItemDataDao;
import com.my.spring.DAO.ProcessItemDao;
import com.my.spring.DAO.ProcessLogDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.ItemData;
import com.my.spring.model.ProcessLog;
import com.my.spring.model.ProcessLogPojo;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.service.ProcessLogService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
@Service("ProcessLogService")
public class ProcessLogServiceImpl implements ProcessLogService {
    @Autowired
    ProcessLogDao ProcessLogDao;
    @Autowired
    ProcessItemDao processItemDao;
    @Autowired
    ItemDataDao itemDataDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserLogService userLogSerivce;
    @Override
    public DataWrapper<Void> addProcessLog(ProcessLog ProcessLog,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(ProcessLog!=null){
				ProcessLog.setCreateDate(new Date(System.currentTimeMillis()));
				if(ProcessLog!=null){
					if(ProcessLog.getItemId()!=null){
						ItemData itemData=itemDataDao.getById(ProcessLog.getItemId());
						if(itemData!=null){
							if(itemData.getApproveUser().equals(userInMemory.getId())){
								if(!ProcessLogDao.addProcessLog(ProcessLog)) 
								{
									dataWrapper.setErrorCode(ErrorCodeEnum.Error);
								}
							}else{
								dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
							}
						}
					}else{
						dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
					}
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
    public DataWrapper<Void> deleteProcessLog(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!ProcessLogDao.deleteProcessLog(id)){
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
    public DataWrapper<List<ProcessLogPojo>> getProcessLogList(String token , Integer pageIndex, Integer pageSize, ProcessLog ProcessLog) {
    	DataWrapper<List<ProcessLogPojo>> dataWrappers = new DataWrapper<List<ProcessLogPojo>>();
    	DataWrapper<List<ProcessLog>> dataWrapper = new DataWrapper<List<ProcessLog>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	if(ProcessLog.getId()!=null){
        		UserLog userLog = new UserLog();
        		userLog.setActionDate(new Date());
        		userLog.setFileId(ProcessLog.getId());
        		userLog.setProjectPart(6);
        		userLog.setUserId(userInMemory.getId());
        		userLog.setVersion("-1");
        		userLogSerivce.addUserLog(userLog, token);
        	}
				dataWrapper=ProcessLogDao.getProcessLogList(pageIndex,pageSize,ProcessLog);
				if(dataWrapper.getData()!=null){
					List<ProcessLogPojo> ProcessLogPojoList = new ArrayList<ProcessLogPojo>();
					for(int i=0;i<dataWrapper.getData().size();i++){
						ProcessLogPojo ProcessLogPojo =new ProcessLogPojo();
						ProcessLogPojo.setId(dataWrapper.getData().get(i).getId());
						if(ProcessLogPojo!=null){
							ProcessLogPojoList.add(ProcessLogPojo);
						}
					}
					if(ProcessLogPojoList!=null && ProcessLogPojoList.size()>0){
						dataWrappers.setData(ProcessLogPojoList);
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
