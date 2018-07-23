package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ProcessDataDao;
import com.my.spring.DAO.ProcessItemDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.ProcessData;
import com.my.spring.model.ProcessDataPojo;
import com.my.spring.model.ProcessItem;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.ProcessDataService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
@Service("ProcessDataService")
public class ProcessDataServiceImpl implements ProcessDataService {
    @Autowired
    ProcessDataDao ProcessDataDao;
    @Autowired
    ProcessItemDao processItemDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserLogService userLogSerivce;
    @Override
    public DataWrapper<ProcessData> addProcessData(ProcessData ProcessData,String token) {
    	DataWrapper<ProcessData> dataWrapper = new DataWrapper<ProcessData>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(ProcessData!=null){
				ProcessData.setCreateUser(userInMemory.getId());
				ProcessData.setCreateDate(new Date(System.currentTimeMillis()));
				if(!ProcessDataDao.addProcessData(ProcessData)) 
				{
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}else{
					dataWrapper.setData(ProcessData);
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
    public DataWrapper<Void> addProcessItem(ProcessItem processItem,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(processItem!=null){
				if(processItem.getProcessId()!=null){
					ProcessData pd = ProcessDataDao.getById(processItem.getProcessId());
					if(pd!=null){
						if(processItem.getWhich()<=pd.getItemNum()){
							if(!processItemDao.addProcessItem(processItem)) 
							{
								dataWrapper.setErrorCode(ErrorCodeEnum.Error);
							}
						}else{
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}
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
    public DataWrapper<Void> deleteProcessData(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!ProcessDataDao.deleteProcessData(id)){
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
    public DataWrapper<Void> updateProcessData(ProcessData ProcessData,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				ProcessData.setCreateUser(userInMemory.getId());
				if(!ProcessDataDao.updateProcessData(ProcessData)) {
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
    public DataWrapper<List<ProcessDataPojo>> getProcessDataList(String token , Integer pageIndex, Integer pageSize, ProcessData ProcessData,Integer type) {
    	DataWrapper<List<ProcessDataPojo>> dataWrappers = new DataWrapper<List<ProcessDataPojo>>();
    	DataWrapper<List<ProcessData>> dataWrapper = new DataWrapper<List<ProcessData>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
	        	if(ProcessData.getId()!=null){
	        		UserLog userLog = new UserLog();
	        		userLog.setActionDate(new Date());
	        		userLog.setFileId(ProcessData.getId());
	        		userLog.setProjectPart(6);
	        		userLog.setUserId(userInMemory.getId());
	        		userLog.setVersion("-1");
	        		userLogSerivce.addUserLog(userLog, token);
	        	}
        		if(type!=null){
        			if(type==0){
        				ProcessData.setName("施工任务单");
        			}
        			if(type==1){
        				ProcessData.setName("预付单");
        			}
        		}
				dataWrapper=ProcessDataDao.getProcessDataList(pageIndex,pageSize,ProcessData);
				if(dataWrapper.getData()!=null){
					List<ProcessDataPojo> ProcessDataPojoList = new ArrayList<ProcessDataPojo>();
					for(int i=0;i<dataWrapper.getData().size();i++){
						ProcessDataPojo ProcessDataPojo =new ProcessDataPojo();
						ProcessDataPojo.setItemNum(dataWrapper.getData().get(i).getItemNum());
						ProcessDataPojo.setProjectId(dataWrapper.getData().get(i).getProjectId().toString());
						ProcessDataPojo.setName(dataWrapper.getData().get(i).getName());
						ProcessDataPojo.setId(dataWrapper.getData().get(i).getId());
						if(dataWrapper.getData().get(i).getCreateUser()!=null){
							User user= new User();
							user=userDao.getById(dataWrapper.getData().get(i).getCreateUser());
							if(user!=null){
								ProcessDataPojo.setCreateUser(user.getUserName());
							}
						}
						ProcessDataPojo.setCreateDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getCreateDate()));
						if(ProcessDataPojo!=null){
							ProcessDataPojoList.add(ProcessDataPojo);
						}
					}
					if(ProcessDataPojoList!=null && ProcessDataPojoList.size()>0){
						dataWrappers.setData(ProcessDataPojoList);
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
