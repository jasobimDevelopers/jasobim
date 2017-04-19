package com.my.spring.serviceImpl;

import com.my.spring.DAO.DuctDao;
import com.my.spring.DAO.DuctLogDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Duct;
import com.my.spring.model.DuctLog;
import com.my.spring.model.DuctLogPojo;
import com.my.spring.model.User;
import com.my.spring.service.DuctLogService;
import com.my.spring.service.FileService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2016/6/22.
 */
@Service("DuctLogService")
public class DuctLogServiceImpl implements DuctLogService {
    @Autowired
    DuctLogDao DuctLogDao;
    @Autowired
    DuctDao ductDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileService fileSerivce;
    @Override
    public DataWrapper<Void> addDuctLog(DuctLog DuctLog,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(DuctLog!=null){
				Duct duct=new Duct();
				if(DuctLog.getDuctId()!=null){
					duct=ductDao.getDuctById(DuctLog.getDuctId());
				}
				/////添加上传时间（精确到秒）
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			    Date date;
				try {
					date = sdfs.parse(df.format(new Date()));
					duct.setDate(date);
					DuctLog.setDate(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				DuctLog.setUserId(userInMemory.getId());
				duct.setState(DuctLog.getState());
				ductDao.updateDuct(duct);
				if(!DuctLogDao.addDuctLog(DuctLog)) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else
					return dataWrapper;
		        
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteDuctLog(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!DuctLogDao.deleteDuctLog(id)) 
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
	public DataWrapper<List<DuctLogPojo>> getDuctLogByDuctId(Long ductId,String token,DuctLog DuctLog) {
		// TODO Auto-generated method stub
		DataWrapper<List<DuctLogPojo>> dataWrapper = new DataWrapper<List<DuctLogPojo>>();
		List<DuctLog> DuctLogList = new ArrayList<DuctLog>();
		List<DuctLogPojo> DuctLogPojoList = new ArrayList<DuctLogPojo>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	
        	DuctLogList=DuctLogDao.getDuctLogByDuctId(ductId).getData();
        	
        	String[] stateList=new String[]{"未定义","出库","安装","完成"};
        	if(DuctLogList!=null){
        		for(int i=0;i<DuctLogList.size();i++){

            		///////////////////////
        			DuctLogPojo DuctLogPojo=new DuctLogPojo();
        			DuctLogPojo.setId(DuctLogList.get(i).getId());
            		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            		String str=sdf.format(DuctLogList.get(i).getDate()); 
            		DuctLogPojo.setDate(str);
            		DuctLogPojo.setState(stateList[DuctLogList.get(i).getState()]);
            		if(DuctLogList.get(i).getUserId()!=null){
            			User user=userDao.getById(DuctLogList.get(i).getUserId());
            			if(user.getUserName()!=null){
            				DuctLogPojo.setUserName(user.getUserName());
            			}
            		}
            		DuctLogPojoList.add(i,DuctLogPojo);
            	}
        	}
        	else{
        		dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        	}
        	dataWrapper.setData(DuctLogPojoList);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
	}
	
	
	
	
	
		
}
