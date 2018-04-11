package com.my.spring.serviceImpl;

import com.my.spring.DAO.ValueOutputDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.ValueOutput;
import com.my.spring.model.ValueOutputPojo;
import com.my.spring.parameters.ProjectDatas;
import com.my.spring.model.Files;
import com.my.spring.model.Project;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.service.ValueOutputService;
import com.my.spring.service.FileService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    @Autowired
    UserLogService userLogSerivce;
    @Override
    public DataWrapper<Void> addValueOutput(ValueOutput ValueOutput,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(ValueOutput!=null){
					//Calendar cal = Calendar.getInstance();
					//int month = cal.get(Calendar.MONTH) + 1;
					///if()
					if(ValueOutput.getDate()==null){
						ValueOutput.setDate(new Date());
					}
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
    public DataWrapper<Void> deleteValueOutput(String id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!ValueOutputDao.deleteValueOutputs(id)) 
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
    	List<ValueOutputPojo> dataWrapperPojo = new ArrayList<ValueOutputPojo>();
    	DataWrapper<List<ValueOutputPojo>> dataWrappers = new DataWrapper<List<ValueOutputPojo>>();
    	User userInMemory=SessionManager.getSession(token);
    	Double nums=0.0;
    	Double finisheds=0.0;
    	if(userInMemory!=null){
    		
    		
    		if(dataWrapperPojo!=null){
    			for(int i=0;i<dataWrapperPojo.size();i++){
    				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	    		String str=sdf.format(dataWrapperPojo.get(i).getDate()); 
    	    		dataWrapperPojo.get(i).setDates(str);
    	    		dataWrapperPojo.get(i).setProjectName(dataWrapperPojo.get(i).getOthers());
    				nums=nums+dataWrapperPojo.get(i).getNum();
    				finisheds=finisheds+dataWrapperPojo.get(i).getFinished();
    			}
				DecimalFormat df=new DecimalFormat(".##");
    			String st=df.format(nums);
    			String st1=df.format(finisheds);
				dataWrapperPojo.get(0).setNums(Double.valueOf(st));
				dataWrapperPojo.get(0).setFinisheds(Double.valueOf(st1));
    			dataWrappers.setData(dataWrapperPojo);
    		}else{
    			dataWrappers.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
    		}
    	}else{
    		dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
    	//dataWrappersn.setData(dataWrappers.getData().get(0));
        return dataWrappers;
    }
    
    @Override
    public DataWrapper<ValueOutputPojo> getValueOutputListnew(String token,Long projectId) {
    	List<ValueOutputPojo> dataWrapperPojo = new ArrayList<ValueOutputPojo>();
    	DataWrapper<ValueOutputPojo> dataWrappers = new DataWrapper<ValueOutputPojo>();
    	User userInMemory=SessionManager.getSession(token);
    	Double nums=0.0;
    	Double finisheds=0.0;
    	if(userInMemory!=null){
    		dataWrapperPojo=ValueOutputDao.getValueOutputListnew(projectId).getData();
    		if(dataWrapperPojo!=null){
    			if(dataWrapperPojo.get(0).getProject_id()!=null){
    				Project projectss=projectDao.getById(Long.valueOf(dataWrapperPojo.get(0).getProject_id()));
    				dataWrapperPojo.get(0).setLeader(projectss.getLeader());
    				if(projectss.getPicId()!=null){
    					Files files = new Files();
    					files=fileSerivce.getById(Long.valueOf(projectss.getPicId()));
    					if(files!=null){
    						dataWrapperPojo.get(0).setProjectPicUrl(files.getUrl());
    					}
    				}
    			}
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    		String str=sdf.format(dataWrapperPojo.get(0).getDate()); 
	    		dataWrapperPojo.get(0).setDates(str);
	    		dataWrapperPojo.get(0).setProjectName(dataWrapperPojo.get(0).getOthers());
				nums=dataWrapperPojo.get(0).getNum();
				finisheds=dataWrapperPojo.get(0).getFinished();
				DecimalFormat df=new DecimalFormat(".##");
    			String st=df.format(nums);
    			String st1=df.format(finisheds);
				dataWrapperPojo.get(0).setNums(Double.valueOf(st));
				dataWrapperPojo.get(0).setFinisheds(Double.valueOf(st1));
    			dataWrappers.setData(dataWrapperPojo.get(0));
    		}else{
    			dataWrappers.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
    		}
    	}else{
    		dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
    	//dataWrappersn.setData(dataWrappers.getData().get(0));
        return dataWrappers;
    }
	@Override
	public DataWrapper<List<ValueOutputPojo>> getValueOutputByProjectName(String projectName,Long projectId,String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<ValueOutputPojo>> dataWrapper = new DataWrapper<List<ValueOutputPojo>>();
		List<ValueOutputPojo> valueOutputs=new ArrayList<ValueOutputPojo>();
        User userInMemory = SessionManager.getSession(token);
        Double nums=0.0;
    	Double finisheds=0.0;
        if(userInMemory != null) {
        	if(userInMemory.getSystemId()==0 || userInMemory.getSystemId()==1 || userInMemory.getSystemId()==-1){
        		
    			UserLog userLog = new UserLog();
    			userLog.setProjectPart(ProjectDatas.ValueOut_area.getCode());
    			userLog.setActionDate(new Date());
    			userLog.setUserId(userInMemory.getId());
    			userLog.setSystemType(userInMemory.getSystemId());
    			//userLog.setVersion("3.0");
    			if(projectId!=null){
    				userLog.setProjectId(projectId);
    			}
    			if(projectName!=null){
    				userLog.setFileId(ValueOutputDao.getValueOutputListByProjectId(projectId).getData().get(0).getId());
    			}
    			userLogSerivce.addUserLog(userLog, token);
    		}
        	String[] projectLists=null;
		    	valueOutputs=ValueOutputDao.getValueOutputList(projectName).getData();
				if(valueOutputs!=null && valueOutputs.size()>0){
	    			for(int i=0;i<valueOutputs.size();i++){
	    				ValueOutputPojo strone=new ValueOutputPojo();
	    				strone.setFinished(valueOutputs.get(i).getFinished());
	    				strone.setNum(valueOutputs.get(i).getNum());
	    				strone.setProject_id(valueOutputs.get(i).getProject_id());
	    				strone.setId(valueOutputs.get(i).getId());
	    				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    	    		String str=sdf.format(valueOutputs.get(i).getDate()); 
	    	    		strone.setDates(str);
	    	    		strone.setProjectName(valueOutputs.get(i).getOthers());
	    	    		valueOutputs.add(i, strone);
	    				nums=valueOutputs.get(i).getNum();
	    				finisheds+=valueOutputs.get(i).getFinished();
	    			}
	    			DecimalFormat df=new DecimalFormat(".##");
	    			String st=df.format(nums);
	    			String st1=df.format(finisheds);
					valueOutputs.get(0).setNums(Double.valueOf(st));
					valueOutputs.get(0).setFinisheds(Double.valueOf(st1));
	    			dataWrapper.setData(valueOutputs);
	    		}else{
	    			dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	    		}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<ValueOutputPojo>> getValueOutputByProjectId(String projectName,Long projectId,String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<ValueOutputPojo>> dataWrapper = new DataWrapper<List<ValueOutputPojo>>();
		List<ValueOutputPojo> valueOutputs=new ArrayList<ValueOutputPojo>();
		List<ValueOutput> valueOutputss=new ArrayList<ValueOutput>();
        User userInMemory = SessionManager.getSession(token);
        Double nums=0.0;
    	Double finisheds=0.0;
        if(userInMemory != null) {
        	if(userInMemory.getSystemId()==0 || userInMemory.getSystemId()==1){
        		
    			UserLog userLog = new UserLog();
    			userLog.setProjectPart(ProjectDatas.ValueOut_area.getCode());
    			userLog.setActionDate(new Date());
    			userLog.setUserId(userInMemory.getId());
    			userLog.setSystemType(userInMemory.getSystemId());
    			//userLog.setVersion("3.0");
    			if(projectId!=null){
    				userLog.setProjectId(projectId);
    			}
    			if(projectName!=null){
    				userLog.setFileId(ValueOutputDao.getValueOutputListByProjectId(projectId).getData().get(0).getId());
    			}
    			userLogSerivce.addUserLog(userLog, token);
    		}
        	String[] projectLists=null;
		    	valueOutputss=ValueOutputDao.getValueOutputByProjectId(projectId,projectName,projectLists).getData();
				if(valueOutputss!=null && valueOutputss.size()>0){
	    			for(int i=0;i<valueOutputss.size();i++){
	    				ValueOutputPojo strone=new ValueOutputPojo();
	    				strone.setFinished(valueOutputss.get(i).getFinished());
	    				strone.setNum(valueOutputss.get(i).getNum());
	    				strone.setProject_id(valueOutputss.get(i).getProjectId());
	    				strone.setId(valueOutputss.get(i).getId());
	    				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    				SimpleDateFormat sdfs=new SimpleDateFormat("yyyy-MM-dd"); 
	    	    		String str=sdfs.format(valueOutputss.get(i).getDate()); 
	    	    		String strs=sdf.format(valueOutputss.get(i).getDate());
	    	    		strone.setDates(str);
	    	    		//strone.setDate(strs);
	    	    		strone.setProjectName(valueOutputss.get(i).getOthers());
	    	    		valueOutputs.add(i, strone);
	    				nums=valueOutputss.get(i).getNum();
	    				finisheds+=valueOutputss.get(i).getFinished();
	    			}
	    			DecimalFormat df=new DecimalFormat(".##");
	    			String st=df.format(nums);
	    			String st1=df.format(finisheds);
					valueOutputs.get(0).setNums(Double.valueOf(st));
					valueOutputs.get(0).setFinisheds(Double.valueOf(st1));
	    			dataWrapper.setData(valueOutputs);
	    		}else{
	    			dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	    		}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
	
	
	
	@Override
	public DataWrapper<List<ValueOutputPojo>> getValueOutputLists(Integer pageIndex, Integer pageSize, ValueOutput ValueOutput, String token,String dates) {
		// TODO Auto-generated method stub
		List<ValueOutputPojo> dataWrapperPojo = new ArrayList<ValueOutputPojo>();
    	DataWrapper<List<ValueOutput>> dataWrappers = new DataWrapper<List<ValueOutput>>();
    	DataWrapper<List<ValueOutputPojo>> dataWrapperspojo = new DataWrapper<List<ValueOutputPojo>>();
    	User userInMemory=SessionManager.getSession(token);
    	if(userInMemory!=null){
			if(ValueOutput.getProjectId()==null){
				ValueOutput.setProjectId((long) -1);
			}
			dataWrappers=ValueOutputDao.getValueOutputLists(pageSize,pageIndex,ValueOutput,dates);
    		if(dataWrappers.getData()!=null){
    			for(int i=0;i<dataWrappers.getData().size();i++){
    				ValueOutputPojo valueOutputPojo = new ValueOutputPojo();
    				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	    		String str=sdf.format(dataWrappers.getData().get(i).getDate()); 
    	    		valueOutputPojo.setDates(str);
    	    		valueOutputPojo.setProjectName(dataWrappers.getData().get(i).getOthers());
    	    		valueOutputPojo.setFinished(dataWrappers.getData().get(i).getFinished());
    	    		valueOutputPojo.setNum(dataWrappers.getData().get(i).getNum());
    	    		valueOutputPojo.setId(dataWrappers.getData().get(i).getId());
    	    		dataWrapperPojo.add(valueOutputPojo);
    			}
    			dataWrapperspojo.setData(dataWrapperPojo);
    			dataWrapperspojo.setCallStatus(dataWrappers.getCallStatus());
    			dataWrapperspojo.setCurrentPage(dataWrappers.getCurrentPage());
    			dataWrapperspojo.setErrorCode(dataWrappers.getErrorCode());
    			dataWrapperspojo.setNumberPerPage(dataWrappers.getNumberPerPage());
    			dataWrapperspojo.setTotalNumber(dataWrappers.getTotalNumber());
    			dataWrapperspojo.setTotalPage(dataWrappers.getTotalPage());
    		}else{
    			dataWrapperspojo.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
    		}
    	}else{
    		dataWrapperspojo.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
        return dataWrapperspojo;
	}
	
}
