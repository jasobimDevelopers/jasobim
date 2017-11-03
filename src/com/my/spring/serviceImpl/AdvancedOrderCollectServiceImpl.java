package com.my.spring.serviceImpl;

import com.my.spring.DAO.AdvancedOrderCollectDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.AdvancedOrderCollect;
import com.my.spring.model.AdvancedOrderCollectPojo;
import com.my.spring.model.User;
import com.my.spring.service.AdvancedOrderCollectService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("advancedOrderCollectService")
public class AdvancedOrderCollectServiceImpl implements AdvancedOrderCollectService {
    @Autowired
    AdvancedOrderCollectDao advancedOrderCollectDao;
    @Autowired
    UserDao userDao;
    @Override
    public DataWrapper<Void> addAdvancedOrderCollect(AdvancedOrderCollect advancedOrderCollect, String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(advancedOrderCollect!=null){
				advancedOrderCollect.setSubmitUserId(userInMemory.getId());
				advancedOrderCollect.setCreateDate(new Date(System.currentTimeMillis()));
				if(!advancedOrderCollectDao.addAdvancedOrderCollect(advancedOrderCollect)) 
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
    public DataWrapper<Void> deleteAdvancedOrderCollect(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!advancedOrderCollectDao.deleteAdvancedOrderCollect(id)){
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
    public DataWrapper<List<AdvancedOrderCollectPojo>> getAdvancedOrderCollectList(String token , Integer pageIndex, Integer pageSize, AdvancedOrderCollect AdvancedOrderCollect) {
    	DataWrapper<List<AdvancedOrderCollectPojo>> dataWrappers = new DataWrapper<List<AdvancedOrderCollectPojo>>();
    	DataWrapper<List<AdvancedOrderCollect>> dataWrapper = new DataWrapper<List<AdvancedOrderCollect>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	
				dataWrapper=advancedOrderCollectDao.getAdvancedOrderCollectsList(pageIndex,pageSize,AdvancedOrderCollect);
				if(dataWrapper.getData()!=null){
					List<AdvancedOrderCollectPojo> AdvancedOrderCollectPojoList = new ArrayList<AdvancedOrderCollectPojo>();
					for(int i=0;i<dataWrapper.getData().size();i++){
						AdvancedOrderCollectPojo AdvancedOrderCollectPojo =new AdvancedOrderCollectPojo();
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
						AdvancedOrderCollectPojo.setConstructPart(dataWrapper.getData().get(i).getConstructPart());
						AdvancedOrderCollectPojo.setCreateDate(sdf.format(dataWrapper.getData().get(i).getCreateDate()));
						AdvancedOrderCollectPojo.setId(dataWrapper.getData().get(i).getId());
						AdvancedOrderCollectPojo.setLeader(dataWrapper.getData().get(i).getLeader());
						AdvancedOrderCollectPojo.setMonth(dataWrapper.getData().get(i).getMonth());
						if(dataWrapper.getData().get(i).getSubmitUserId()!=null){
							AdvancedOrderCollectPojo.setSubmitUserName(userDao.getById(dataWrapper.getData().get(i).getSubmitUserId()).getRealName());
						}
						if(AdvancedOrderCollectPojo!=null){
							AdvancedOrderCollectPojoList.add(AdvancedOrderCollectPojo);
						}
					}
					if(AdvancedOrderCollectPojoList!=null && AdvancedOrderCollectPojoList.size()>0){
						dataWrappers.setData(AdvancedOrderCollectPojoList);
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
	public DataWrapper<List<AdvancedOrderCollect>> getAdvancedOrderCollectListByUserId(Long userId,String token) {
		DataWrapper<List<AdvancedOrderCollect>> dataWrapper = new DataWrapper<List<AdvancedOrderCollect>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userId!=null){
				if(advancedOrderCollectDao.getAdvancedOrderCollectByUserId(userId)==null) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else
					return advancedOrderCollectDao.getAdvancedOrderCollectByUserId(userId);
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
	}

	

	
}
