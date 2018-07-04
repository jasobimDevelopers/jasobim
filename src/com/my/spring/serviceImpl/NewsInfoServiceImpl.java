package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.NewsInfoDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.MechanicPojo;
import com.my.spring.model.NewsInfo;
import com.my.spring.model.NewsInfoPojo;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.parameters.Parameters;
import com.my.spring.parameters.ProjectDatas;
import com.my.spring.service.FileService;
import com.my.spring.service.NewsInfoService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
@Service("newsInfoService")
public class NewsInfoServiceImpl implements NewsInfoService {
    @Autowired
    NewsInfoDao NewsInfoDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileService fileService;
    @Autowired
    UserLogService userLogSerivce;
    @Override
    public DataWrapper<Void> addNewsInfo(NewsInfo NewsInfo,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(NewsInfo!=null){
				if(userInMemory.getSystemId()!=null){
	    				UserLog userLog = new UserLog();
	        			userLog.setProjectPart(ProjectDatas.NewsInfo_area.getCode());
	        			userLog.setActionDate(new Date());
	        			userLog.setActionType(1);
	        			userLog.setUserId(userInMemory.getId());
	        			userLog.setSystemType(userInMemory.getSystemId());
	        			userLog.setVersion("3.0");
	        			userLogSerivce.addUserLog(userLog, token);
	    		}
				NewsInfo.setCreateUserId(userInMemory.getId());
				NewsInfo.setCreateDate(new Date(System.currentTimeMillis()));
				if(!NewsInfoDao.addNewsInfo(NewsInfo)) 
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
    public DataWrapper<Void> deleteNewsInfo(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!NewsInfoDao.deleteNewsInfo(id)){
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
    public DataWrapper<List<NewsInfoPojo>> getNewsInfoList(String token , Integer pageIndex, Integer pageSize, NewsInfo NewsInfo) {
    	DataWrapper<List<NewsInfoPojo>> dataWrappers = new DataWrapper<List<NewsInfoPojo>>();
    	DataWrapper<List<NewsInfo>> dataWrapper = new DataWrapper<List<NewsInfo>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        		if(userInMemory.getSystemId()!=null){
        				UserLog userLog = new UserLog();
            			userLog.setProjectPart(ProjectDatas.NewsInfo_area.getCode());
            			userLog.setActionDate(new Date());
            			userLog.setActionType(0);
            			if(NewsInfo.getId()!=null){
            				userLog.setFileId(NewsInfo.getId());
            			}
            			userLog.setUserId(userInMemory.getId());
            			userLog.setSystemType(userInMemory.getSystemId());
            			userLog.setVersion("3.0");
            			userLogSerivce.addUserLog(userLog, token);
        		}
				dataWrapper=NewsInfoDao.getNewsInfoList(pageIndex,pageSize,NewsInfo);
				if(dataWrapper.getData()!=null){
					List<NewsInfoPojo> NewsInfoPojoList = new ArrayList<NewsInfoPojo>();
					for(int i=0;i<dataWrapper.getData().size();i++){
						NewsInfoPojo NewsInfoPojo =new NewsInfoPojo();
						if(NewsInfo.getId()!=null){
							NewsInfoPojo.setContent(dataWrapper.getData().get(i).getContent());
							if(dataWrapper.getData().get(i).getCreateUserId()!=null){
								NewsInfoPojo.setCreateUserId(dataWrapper.getData().get(i).getCreateUserId());
								User user=userDao.getById(dataWrapper.getData().get(i).getCreateUserId());
								NewsInfoPojo.setCreateUserName(user.getRealName());
							}
						}
						NewsInfoPojo.setCreateDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getCreateDate()));
						NewsInfoPojo.setId(dataWrapper.getData().get(i).getId());
						NewsInfoPojo.setRemark(dataWrapper.getData().get(i).getRemark());
						NewsInfoPojo.setTopic(dataWrapper.getData().get(i).getTopic());
						if(dataWrapper.getData().get(i).getCreateUserId()!=null){
							User user= new User();
							user=userDao.getById(dataWrapper.getData().get(i).getCreateUserId());
							if(user!=null){
								NewsInfoPojo.setCreateUserName(user.getRealName());
							}
						}
						if(NewsInfoPojo!=null){
							NewsInfoPojoList.add(NewsInfoPojo);
						}
					}
					if(NewsInfoPojoList!=null && NewsInfoPojoList.size()>0){
						dataWrappers.setData(NewsInfoPojoList);
						dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
						dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
						dataWrappers.setTotalPage(dataWrapper.getTotalPage());
						dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
					}else{
						dataWrappers.setErrorCode(ErrorCodeEnum.Error);
					}
				}
			}else{
				dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
			}
        if(dataWrappers.getCallStatus()==CallStatusEnum.SUCCEED && dataWrappers.getData()==null){
	       	List<NewsInfoPojo> pas= new ArrayList<NewsInfoPojo>();
	       	dataWrappers.setData(pas);
	    }
        return dataWrappers;
    }

	@Override
	public DataWrapper<List<NewsInfo>> getNewsInfoListByUserId(Long userId,String token) {
		DataWrapper<List<NewsInfo>> dataWrapper = new DataWrapper<List<NewsInfo>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userId!=null){
				if(NewsInfoDao.getNewsInfoListByUserId(userId)==null) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else
					return NewsInfoDao.getNewsInfoListByUserId(userId);
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
	}

	@Override
	public DataWrapper<String> uploadNewsInfoFiles(MultipartFile file, HttpServletRequest request, String token) {
		// TODO Auto-generated method stub
		DataWrapper<String> result = new DataWrapper<String>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			Files files=fileService.uploadFile("fileUploads/newsInfo", file, 12, request);
			if(files!=null){
				result.setData(files.getUrl());
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<NewsInfoPojo> updateNewsInfo(NewsInfo newsInfo, String token) {
		DataWrapper<NewsInfoPojo> result = new DataWrapper<NewsInfoPojo>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(newsInfo!=null){
				if(newsInfo.getId()!=null){
					if(user.getSystemId()!=null){
		    				UserLog userLog = new UserLog();
		        			userLog.setProjectPart(ProjectDatas.NewsInfo_area.getCode());
		        			userLog.setActionDate(new Date());
		        			userLog.setActionType(3);
		        			userLog.setUserId(user.getId());
		        			userLog.setSystemType(user.getSystemId());
		        			userLog.setVersion("3.0");
		        			userLogSerivce.addUserLog(userLog, token);
		    		}
					NewsInfo newsInfoNew = NewsInfoDao.getById(newsInfo.getId());
					if(newsInfoNew!=null){
						newsInfoNew.setContent(newsInfo.getContent());
						newsInfoNew.setTopic(newsInfo.getTopic());
						newsInfoNew.setRemark(newsInfo.getRemark());
						if(!NewsInfoDao.updateNewsInfo(newsInfoNew)){
							result.setErrorCode(ErrorCodeEnum.Error);
						}else{
							NewsInfoPojo gets = new NewsInfoPojo();
							gets.setCreateDate(Parameters.getSdf().format(newsInfoNew.getCreateDate()));
							gets.setCreateUserId(newsInfoNew.getCreateUserId());
							gets.setTopic(newsInfoNew.getTopic());
							gets.setRemark(newsInfoNew.getRemark());
							if(newsInfoNew.getCreateUserId()!=null){
								User users= new User();
								user=userDao.getById(newsInfoNew.getCreateUserId());
								if(user!=null){
									gets.setCreateUserName(users.getRealName());
								}
							}
							result.setData(gets);
						}
					}else{
						result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
					}
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}


}
