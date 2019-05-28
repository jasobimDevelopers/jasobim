package com.my.spring.serviceImpl;

import com.my.spring.DAO.MeasuredSiteDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.MeasuredSite;
import com.my.spring.model.User;
import com.my.spring.service.MeasuredSiteService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("measuredSiteService")
//@Component("deleteBuilding")
public class MeasuredSiteServiceImpl implements MeasuredSiteService {
    @Autowired
    MeasuredSiteDao buildingDao;
    @Autowired
    UserDao userDao;
    @Override
    public DataWrapper<Void> addMeasuredSite(MeasuredSite building,String token,String siteDetail) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(building!=null){
					if(siteDetail!=null){
						String[] nameList = siteDetail.split(",");
						List<MeasuredSite> saveList = new ArrayList<MeasuredSite>();
						for(int i=0;i<nameList.length;i++){
							building.setSiteName(nameList[i]);
							saveList.add(building);
						}
						if(!buildingDao.addMeasuredSiteList(saveList)){
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}
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
    public DataWrapper<Void> deleteMeasuredSite(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!buildingDao.deleteMeasuredSite(id)) 
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
    public DataWrapper<Void> updateMeasuredSite(MeasuredSite building,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
 				if(building!=null){
					if(!buildingDao.updateMeasuredSite(building)) 
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
	public DataWrapper<MeasuredSite> getMeasuredSiteListByBuildingId(Long buildingId,String token) {
		// TODO Auto-generated method stub
		DataWrapper<MeasuredSite> dataWrapper = new DataWrapper<MeasuredSite>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			dataWrapper=buildingDao.getMeasuredSiteListByBuildingId(buildingId);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
}
