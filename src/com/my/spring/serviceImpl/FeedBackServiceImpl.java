package com.my.spring.serviceImpl;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.DAO.FeedBackDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.FeedBack;
import com.my.spring.model.FeedBackPojo;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.parameters.ProjectDatas;
import com.my.spring.service.FeedBackService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;


@Service("feedBackService")
public class FeedBackServiceImpl implements FeedBackService {
	@Autowired
	FeedBackDao feedBackDao;
	@Autowired
	UserDao userDao;
	@Autowired
	UserLogService userLogService;
	@Override
	public DataWrapper<List<FeedBackPojo>> getFeedBackList(Integer pageIndex, Integer pageSize, FeedBack feedBack, String token,String dates) {
		// TODO Auto-generated method stub
		DataWrapper<List<FeedBack>> dataWrapper = new DataWrapper<List<FeedBack>>();
		List<FeedBackPojo> feedbackpojo = new ArrayList<FeedBackPojo>();
		
		DataWrapper<List<FeedBackPojo>> dataWrapperpojo = new DataWrapper<List<FeedBackPojo>>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null) {
			User adminInDB = userDao.getById(adminInMemory.getId());
			if (adminInDB.getUserType() == UserTypeEnum.Admin.getType()) {
				dataWrapper=feedBackDao.getFeedBackList(pageSize, pageIndex,feedBack,dates);
				if(dataWrapper.getData()!=null){
					for(int i=0;i<dataWrapper.getData().size();i++){
						FeedBackPojo feedbackpojos=new FeedBackPojo();
						feedbackpojos.setContent(dataWrapper.getData().get(i).getContent());
						feedbackpojos.setId(dataWrapper.getData().get(i).getId());
						feedbackpojos.setUserName(dataWrapper.getData().get(i).getUserName());
						if(dataWrapper.getData().get(i).getUserName()!=null){
							User users=userDao.getByUserName(dataWrapper.getData().get(i).getUserName());
							if(users!=null){
								feedbackpojos.setRealName(users.getRealName());
							}
						}
						feedbackpojos.setTel(dataWrapper.getData().get(i).getTel());
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    	    		String str=sdf.format(dataWrapper.getData().get(i).getDate()); 
	    	    		feedbackpojos.setDate(str);
	    	    		feedbackpojo.add(feedbackpojos);
					}
					dataWrapperpojo.setData(feedbackpojo);
					dataWrapperpojo.setCallStatus(dataWrapper.getCallStatus());
					dataWrapperpojo.setCurrentPage(dataWrapper.getCurrentPage());
					dataWrapperpojo.setNumberPerPage(dataWrapper.getNumberPerPage());
					dataWrapperpojo.setTotalNumber(dataWrapper.getTotalNumber());
					dataWrapperpojo.setTotalPage(dataWrapper.getTotalPage());
					dataWrapperpojo.setErrorCode(dataWrapper.getErrorCode());
				}
			} else {
				dataWrapperpojo.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrapperpojo.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapperpojo;
	}


	@Override
	public DataWrapper<Void> deleteFeedBack(String ids, String token) {
		DataWrapper<Void> dataWrapper=new DataWrapper<Void>();
		User userInMemory=SessionManager.getSession(token);
		String[] idList=null;
		idList=ids.split(",");
		
		if(userInMemory!=null){
			User adminInDB = userDao.getById(userInMemory.getId());
			if (adminInDB.getUserType() == UserTypeEnum.Admin.getType()) {
				if(feedBackDao.deleteFeedBack(idList)){
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
	public DataWrapper<Void> addFeedBack(String content,String token,String tel) {
		DataWrapper<Void> dataWrapper = new DataWrapper<>();
		FeedBack feedBack=new FeedBack();
		feedBack.setContent(content);
		feedBack.setTel(tel);
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			if(userInMemory.getSystemId()!=null){
				UserLog userLog = new UserLog();
    			userLog.setProjectPart(ProjectDatas.UserTeam_area.getCode());
    			userLog.setActionDate(new Date());
    			userLog.setActionType(1);
    			userLog.setUserId(userInMemory.getId());
    			userLog.setSystemType(userInMemory.getSystemId());
    			//userLog.setVersion("3.0");
    			userLogService.addUserLog(userLog,token);
    		}
			feedBack.setDate(new Date(System.currentTimeMillis()));
			feedBack.setUserName(userInMemory.getRealName());
			if(!feedBackDao.addFeedBack(feedBack)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		return dataWrapper;
	}


	

}
