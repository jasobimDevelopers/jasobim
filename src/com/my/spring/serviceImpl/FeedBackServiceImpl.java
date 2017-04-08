package com.my.spring.serviceImpl;

import java.sql.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.DAO.FeedBackDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.FeedBack;
import com.my.spring.model.User;
import com.my.spring.service.FeedBackService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;


@Service("feedBackService")
public class FeedBackServiceImpl implements FeedBackService {
	
	@Autowired
	FeedBackDao feedBackDao;
	@Autowired
	UserDao userDao;
	



	@Override
	public DataWrapper<List<FeedBack>> getFeedBackList(Integer pageIndex, Integer pageSize, FeedBack feedBack, String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<FeedBack>> dataWrapper = new DataWrapper<List<FeedBack>>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null) {
			User adminInDB = userDao.getById(adminInMemory.getId());
			if (adminInDB.getUserType() == UserTypeEnum.Admin.getType()) {
				dataWrapper=feedBackDao.getFeedBackList(pageSize, pageIndex,feedBack);
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}


	@Override
	public DataWrapper<Void> deleteFeedBack(Long id, String token) {
		DataWrapper<Void> dataWrapper=new DataWrapper<Void>();
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			User adminInDB = userDao.getById(userInMemory.getId());
			if (adminInDB.getUserType() == UserTypeEnum.Admin.getType()) {
				if(feedBackDao.deleteFeedBack(id)){
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
			feedBack.setDate(new Date(System.currentTimeMillis()));
			feedBack.setUserName(userInMemory.getUserName());
			if(!feedBackDao.addFeedBack(feedBack)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		return dataWrapper;
	}


	

}
