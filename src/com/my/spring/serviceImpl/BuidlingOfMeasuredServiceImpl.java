package com.my.spring.serviceImpl;

import com.my.spring.DAO.BuildingOfMeasuredDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.BuildingOfMeasured;
import com.my.spring.model.User;
import com.my.spring.service.BuildingOfMeasuredService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("buildingOfMeasuredService")
public class BuidlingOfMeasuredServiceImpl implements BuildingOfMeasuredService {
    @Autowired
    BuildingOfMeasuredDao buildingDao;
    @Autowired
    UserDao userDao;
    @Override
    public DataWrapper<Void> addBuildingOfMeasured(BuildingOfMeasured building,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				if(building!=null){
					building.setCreateUser(userInMemory.getId());
					building.setCreateDate(new Date());
					if(!buildingDao.addBuildingOfMeasured(building)) 
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
    public DataWrapper<Void> deleteBuildingOfMeasured(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!buildingDao.deleteBuildingOfMeasured(id)) 
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
    public DataWrapper<Void> updateBuildingOfMeasured(BuildingOfMeasured building,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
	 				if(building!=null){
	 					BuildingOfMeasured newOne = buildingDao.getBuildingOfMeasuredById(building.getBfmId());
						if(newOne!=null){
							newOne.setbName(building.getbName());
							if(!buildingDao.updateBuildingOfMeasured(newOne)) 
					            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
							else
								return dataWrapper;
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
	public DataWrapper<List<BuildingOfMeasured>> getBuildingOfMeasuredByProjectId(Long projectId,String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<BuildingOfMeasured>> dataWrapper = new DataWrapper<List<BuildingOfMeasured>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				dataWrapper=buildingDao.getBuildingOfMeasuredByProjectId(projectId);
			
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
}
