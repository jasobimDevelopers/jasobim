package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ItemDataDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.WechatUrlDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.ItemData;
import com.my.spring.model.ItemDataPojo;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.ItemDataService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
@Service("itemDataService")
public class ItemDataServiceImpl implements ItemDataService {
    @Autowired
    ItemDataDao ItemDataDao;
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
    public DataWrapper<Void> addItemData(ItemData ItemData,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(ItemData!=null){
				ItemData.setCreateUser(userInMemory.getId());
				ItemData.setCreateDate(new Date(System.currentTimeMillis()));
				if(!ItemDataDao.addItemData(ItemData)) 
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
    public DataWrapper<Void> updateItemData(ItemData ItemData,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				ItemData.setUpdateUser(userInMemory.getId());
				ItemData.setUpdateDate(new Date());
				if(!ItemDataDao.updateItemData(ItemData)) {
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
    public DataWrapper<List<ItemDataPojo>> getItemDataList(String token , Integer pageIndex, Integer pageSize, ItemData ItemData) {
    	DataWrapper<List<ItemDataPojo>> dataWrappers = new DataWrapper<List<ItemDataPojo>>();
    	DataWrapper<List<ItemData>> dataWrapper = new DataWrapper<List<ItemData>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	if(ItemData.getId()!=null){
        		UserLog userLog = new UserLog();
        		userLog.setActionDate(new Date());
        		userLog.setFileId(ItemData.getId());
        		userLog.setProjectPart(6);
        		userLog.setUserId(userInMemory.getId());
        		userLog.setVersion("-1");
        		userLogSerivce.addUserLog(userLog, token);
        	}
				dataWrapper=ItemDataDao.getItemDataList(pageIndex,pageSize,ItemData);
				if(dataWrapper.getData()!=null){
					if(!dataWrapper.getData().isEmpty()){
						List<ItemDataPojo> ItemDataPojoList = new ArrayList<ItemDataPojo>();
						for(int i=0;i<dataWrapper.getData().size();i++){
							ItemDataPojo ItemDataPojo =new ItemDataPojo();
							ItemDataPojo.setApproveUser(userDao.getById(dataWrapper.getData().get(i).getApproveUser()).getRealName());
							ItemDataPojo.setCreateDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getCreateDate()));
							ItemDataPojo.setName(dataWrapper.getData().get(i).getName());
							ItemDataPojo.setId(dataWrapper.getData().get(i).getId());
							if(dataWrapper.getData().get(i).getUpdateUser()!=null){
								ItemDataPojo.setUpdateUser(userDao.getById(dataWrapper.getData().get(i).getUpdateUser()).getRealName());
							}
							if(dataWrapper.getData().get(i).getUpdateDate()!=null){
								ItemDataPojo.setUpdateDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getUpdateDate()));
							}
							ItemDataPojo.setWorkName(dataWrapper.getData().get(i).getWorkName().toString());
							if(dataWrapper.getData().get(i).getCreateUser()!=null){
								User user= new User();
								user=userDao.getById(dataWrapper.getData().get(i).getCreateUser());
								if(user!=null){
									ItemDataPojo.setCreateUser(user.getRealName());
								}
							}
							if(ItemDataPojo!=null){
								ItemDataPojoList.add(ItemDataPojo);
							}
						}
						if(ItemDataPojoList!=null && ItemDataPojoList.size()>0){
							dataWrappers.setData(ItemDataPojoList);
							dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
							dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
							dataWrappers.setTotalPage(dataWrapper.getTotalPage());
							dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
						}
					}
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
        return dataWrappers;
    }

	
	

	
}
