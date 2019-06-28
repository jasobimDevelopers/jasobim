package com.my.spring.serviceImpl;

import com.my.spring.DAO.PaperPointNumsLogDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.PaperPointNumsLog;
import com.my.spring.model.User;
import com.my.spring.service.PaperPointNumsLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("paperPointNumsLogService")
//@Component("deleteBuilding")
public class PaperPointNumsLogServiceImpl implements PaperPointNumsLogService {
    @Autowired
    PaperPointNumsLogDao buildingDao;
    @Autowired
    UserDao userDao;
    @Override
    public DataWrapper<Void> addPaperPointNumsLog(PaperPointNumsLog building,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(building!=null){
				building.setUserId(userInMemory.getId());
				if(!buildingDao.addPaperPointNumsLog(building)) 
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
    public DataWrapper<Void> deletePaperPointNumsLog(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(id!=null){
				if(!buildingDao.deletePaperPointNumsLog(id)) 
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
    public DataWrapper<Void> updatePaperPointNumsLog(PaperPointNumsLog building,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(building!=null){
				if(building.getId()!=null){
					PaperPointNumsLog ppn = new PaperPointNumsLog();
					ppn=buildingDao.getPaperPointNumsById(building.getId());
					if(ppn!=null){
						if(building.getBuildingNames()!=null){
							ppn.setBuildingNames(building.getBuildingNames());
						}
						if(building.getCheckTypes()!=null){
							ppn.setCheckTypes(building.getCheckTypes());
						}
						if(!buildingDao.updatePaperPointNumsLog(ppn)) 
				            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						else
							return dataWrapper;
					}
				}
				
		        
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

}
