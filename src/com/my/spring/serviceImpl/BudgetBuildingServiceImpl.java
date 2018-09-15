package com.my.spring.serviceImpl;

import com.my.spring.DAO.BudgetBuildingDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.BudgetBuilding;
import com.my.spring.model.User;
import com.my.spring.service.BudgetBuildingService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service("budgetBuildingService")
public class BudgetBuildingServiceImpl implements BudgetBuildingService {
    @Autowired
    BudgetBuildingDao budgetBuildingDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserLogService userLogSerivce;
    @Override
    public DataWrapper<BudgetBuilding> addBudgetBuilding(BudgetBuilding m,String token) {
    	DataWrapper<BudgetBuilding> dataWrapper = new DataWrapper<BudgetBuilding>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(m!=null){
				m.setCreateDate(new Date());
				m.setCreateUser(userInMemory.getId());
				if(!budgetBuildingDao.addBudgetBuilding(m)){
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}else{
					dataWrapper.setData(m);
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
    public DataWrapper<Void> deleteBudgetBuilding(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if(userInMemory != null) {
			if(id!=null){
				if(!budgetBuildingDao.deleteBudgetBuilding(id)){
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
    public DataWrapper<Void> updateBudgetBuilding(BudgetBuilding m,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	m.setCreateUser(userInMemory.getId());
			if(!budgetBuildingDao.updateBudgetBuilding(m)) {
			    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<BudgetBuilding>> getBudgetBuildingList(String token , Integer pageIndex, Integer pageSize, BudgetBuilding m) {
    	DataWrapper<List<BudgetBuilding>> dataWrapper = new DataWrapper<List<BudgetBuilding>>();
    	User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) 
        {
			dataWrapper=budgetBuildingDao.getBudgetBuildingList(pageIndex,pageSize,m);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

}
