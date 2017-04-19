package com.my.spring.serviceImpl;

import com.my.spring.DAO.ValueOutputDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.ValueOutput;
import com.my.spring.model.ValueOutputPojo;
import com.my.spring.model.User;
import com.my.spring.service.ValueOutputService;
import com.my.spring.service.FileService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("ValueOutputService")
public class ValueOutputServiceImpl implements ValueOutputService {
    @Autowired
    ValueOutputDao ValueOutputDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    FileService fileSerivce;
    @Override
    public DataWrapper<Void> addValueOutput(ValueOutput ValueOutput,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(ValueOutput!=null){
					ValueOutput.setDate(new Date());
					if(!ValueOutputDao.addValueOutput(ValueOutput)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
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
    public DataWrapper<Void> deleteValueOutput(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!ValueOutputDao.deleteValueOutput(id)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
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
    public DataWrapper<Void> updateValueOutput(ValueOutput ValueOutput,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
 				if(ValueOutput!=null){
					if(!ValueOutputDao.updateValueOutput(ValueOutput)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
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
    public DataWrapper<List<ValueOutputPojo>> getValueOutputList(String token) {
    	List<ValueOutput> dataWrapper = new ArrayList<ValueOutput>();
    	List<ValueOutputPojo> dataWrapperPojo = new ArrayList<ValueOutputPojo>();
    	DataWrapper<List<ValueOutputPojo>> dataWrappers = new DataWrapper<List<ValueOutputPojo>>();
    	User userInMemory=SessionManager.getSession(token);
    	if(userInMemory!=null){
    		String projectList=userInMemory.getProjectList();
    		dataWrapper=ValueOutputDao.getValueOutputList(projectList).getData();
    		if(dataWrapper!=null){
    			for(int i=0;i<dataWrapper.size();i++){
    				ValueOutputPojo strone=new ValueOutputPojo();
    				strone.setFinished(dataWrapper.get(i).getFinished());
    				strone.setNum(dataWrapper.get(i).getNum());
    				strone.setId(dataWrapper.get(i).getId());
    				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	    		String str=sdf.format(dataWrapper.get(i).getDate()); 
    	    		strone.setDate(str);
    	    		strone.setProjectName(projectDao.getById(dataWrapper.get(i).getProjectId()).getName());
    				dataWrapperPojo.add(i, strone);
    			}
    			dataWrappers.setData(dataWrapperPojo);
    		}else{
    			dataWrappers.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
    		}
    	}else{
    		dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
        return dataWrappers;
    }

	@Override
	public DataWrapper<ValueOutput> getValueOutputByProjectId(Long projectId,String token) {
		// TODO Auto-generated method stub
		DataWrapper<ValueOutput> dataWrapper = new DataWrapper<ValueOutput>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			
				dataWrapper=ValueOutputDao.getValueOutputByProjectId(projectId);
			
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
	
}
