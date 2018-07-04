package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.NewsDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.WechatUrlDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.News;
import com.my.spring.model.NewsPojo;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.model.WechatUrl;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.NewsService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import com.my.spring.wechat.WechatSpider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
@Service("newsService")
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsDao NewsDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileService fileService;
    @Autowired
    UserLogService userLogSerivce;
    @Autowired
    WechatUrlDao wechatDao;
    @Override
    public DataWrapper<Void> addNews(News News,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(News!=null){
				News.setUserId(userInMemory.getId());
				if(News.getNewsDate()==null){
					News.setNewsDate(new Date(System.currentTimeMillis()));
				}
				if(!NewsDao.addNews(News)) 
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
    public DataWrapper<Void> deleteNews(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!NewsDao.deleteNews(id)){
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
    public DataWrapper<Void> updateNews(News News,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				News.setUserId(userInMemory.getId());
				if(!NewsDao.updateNews(News)) {
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
    public DataWrapper<List<NewsPojo>> getNewsList(String token , Integer pageIndex, Integer pageSize, News News) {
    	DataWrapper<List<NewsPojo>> dataWrappers = new DataWrapper<List<NewsPojo>>();
    	DataWrapper<List<News>> dataWrapper = new DataWrapper<List<News>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	if(News.getId()!=null){
        		UserLog userLog = new UserLog();
        		userLog.setActionDate(new Date());
        		userLog.setFileId(News.getId());
        		userLog.setProjectPart(6);
        		userLog.setUserId(userInMemory.getId());
        		userLog.setVersion("-1");
        		userLogSerivce.addUserLog(userLog, token);
        	}
				dataWrapper=NewsDao.getNewsList(pageIndex,pageSize,News);
				if(dataWrapper.getData()!=null){
					List<NewsPojo> NewsPojoList = new ArrayList<NewsPojo>();
					for(int i=0;i<dataWrapper.getData().size();i++){
						NewsPojo NewsPojo =new NewsPojo();
						NewsPojo.setContent(dataWrapper.getData().get(i).getContent());
						NewsPojo.setNewsDate(dataWrapper.getData().get(i).getNewsDate());
						NewsPojo.setId(dataWrapper.getData().get(i).getId());
						NewsPojo.setTitle(dataWrapper.getData().get(i).getTitle());
						if(dataWrapper.getData().get(i).getUserId()!=null){
							NewsPojo.setUserId(dataWrapper.getData().get(i).getUserId());
							User user= new User();
							user=userDao.getById(dataWrapper.getData().get(i).getUserId());
							if(user.getUserIcon()!=null){
								Files files=fileDao.getById(user.getUserIcon());
								if(files.getUrl()!=null){
									NewsPojo.setUserIconUrl(files.getUrl());
								}
							}
							if(user!=null){
								NewsPojo.setUserName(user.getUserName());
							}
							
						}
						if(NewsPojo!=null){
							NewsPojoList.add(NewsPojo);
						}
					}
					if(NewsPojoList!=null && NewsPojoList.size()>0){
						dataWrappers.setData(NewsPojoList);
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

	@Override
	public DataWrapper<List<News>> getNewsListByUserId(Long userId,String token) {
		DataWrapper<List<News>> dataWrapper = new DataWrapper<List<News>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userId!=null){
				if(NewsDao.getNewsListByUserId(userId)==null) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else
					return NewsDao.getNewsListByUserId(userId);
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
	}

	@Override
	public DataWrapper<String> getNewsListByWeixin(String token) {
		DataWrapper<String> result = new DataWrapper<String>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			WechatSpider wechatSpider = new WechatSpider("gh_c886b7f1ee09");
			String listUrl = wechatSpider.getListUrl();
			if(listUrl!=null){
				result.setData(listUrl);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		return result;
	}
	@Override
	public DataWrapper<Void> saveWechatUrl(String token) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		WechatSpider wechatSpider = new WechatSpider("gh_c886b7f1ee09");
		String listUrl = wechatSpider.getListUrl();
		if(listUrl!=null && !listUrl.equals("")){
			WechatUrl news = new WechatUrl();
			news.setUrl(listUrl);
			news.setCreateDate(new Date());
			wechatDao.addWechatUrl(news);
		}
		return result;
	}
	

	
}
