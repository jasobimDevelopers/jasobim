package com.my.spring.serviceImpl;

import com.my.spring.DAO.BudgetBuildingDao;
import com.my.spring.DAO.BudgetDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.AttenceModelPojo;
import com.my.spring.model.Budget;
import com.my.spring.model.BudgetBuilding;
import com.my.spring.model.BudgetPojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.BudgetService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MD5Util;
import com.my.spring.utils.ReadBudgetExcel;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

@Service("budgetService")
public class BudgetServiceImpl implements BudgetService {
    @Autowired
    BudgetDao BudgetDao;
    @Autowired
    BudgetBuildingDao budgetBuildingDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserLogService userLogSerivce;
    @Override
    public DataWrapper<Void> addBudget(Budget m,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(m!=null){
				m.setUploadDate(new Date());
				m.setUserId(userInMemory.getId());
				if(!BudgetDao.addBudget(m)){
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
    public DataWrapper<Void> deleteBudget(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if(userInMemory != null) {
			if(id!=null){
				if(!BudgetDao.deleteBudget(id)){
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
    public DataWrapper<Void> updateBudget(Budget m,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	m.setUserId(userInMemory.getId());
        	if(m!=null){
        		Budget ms = BudgetDao.getById(m.getId());
        		if(ms!=null){
        			m.setUploadDate(ms.getUploadDate());
        		}
        	}
			if(!BudgetDao.updateBudget(m)) {
			    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<BudgetPojo>> getBudgetList(String token , Integer pageIndex, Integer pageSize, Budget m) {
    	DataWrapper<List<BudgetPojo>> dataWrappers = new DataWrapper<List<BudgetPojo>>();
    	DataWrapper<List<Budget>> dataWrapper = new DataWrapper<List<Budget>>();
    	List<BudgetPojo> mPojoList = new ArrayList<BudgetPojo>();
    	User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) 
        {
			dataWrapper=BudgetDao.getBudgetList(pageIndex,pageSize,m);
			if(dataWrapper.getData()!=null)
			{		
				if(dataWrapper.getData().size()>0)
				{
					for(int i=0;i<dataWrapper.getData().size();i++)
					{
						BudgetPojo mPojo =new BudgetPojo();
						mPojo.setId(dataWrapper.getData().get(i).getId());
						mPojo.setProjectCode(dataWrapper.getData().get(i).getProjectCode());
						mPojo.setProjectId(dataWrapper.getData().get(i).getProjectId());
						mPojo.setProjectName(dataWrapper.getData().get(i).getProjectName());
						mPojo.setQuantity(dataWrapper.getData().get(i).getQuantity());
						mPojo.setSelfId(dataWrapper.getData().get(i).getSelfId());
						mPojo.setUnit(dataWrapper.getData().get(i).getUnit());
						mPojo.setArtificialCostNum(dataWrapper.getData().get(i).getArtificialCostNum());
						mPojo.setArtificialCostOne(dataWrapper.getData().get(i).getArtificialCostOne());
						mPojo.setMaterialsExpensesNum(dataWrapper.getData().get(i).getMaterialsExpensesNum());
						mPojo.setMaterialsExpensesOne(dataWrapper.getData().get(i).getMaterialsExpensesOne());
						mPojo.setMechanicalFeeNum(dataWrapper.getData().get(i).getMechanicalFeeNum());
						mPojo.setMechanicalFeeOne(dataWrapper.getData().get(i).getMechanicalFeeOne());
						mPojo.setSumOfMoneyNum(dataWrapper.getData().get(i).getSumOfMoneyNum());
						mPojo.setSumOfMoneyOne(dataWrapper.getData().get(i).getSumOfMoneyOne());
						mPojo.setQuotaNum(dataWrapper.getData().get(i).getQuotaNum());
						mPojo.setQuotaOne(dataWrapper.getData().get(i).getQuotaOne());
						if(dataWrapper.getData().get(i).getBudgetBuildingId()!=null){
							BudgetBuilding building = new BudgetBuilding();
							building=budgetBuildingDao.getById(dataWrapper.getData().get(i).getBudgetBuildingId());
							mPojo.setBuildingInfo(building.getName());
						}
						mPojo.setUploadDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getUploadDate()));
						if(mPojo!=null){
							mPojoList.add(mPojo);
						}
					}
				}
				if(mPojoList!=null && mPojoList.size()>0){
					dataWrappers.setData(mPojoList);
					dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
					dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
					dataWrappers.setTotalPage(dataWrapper.getTotalPage());
					dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
				}
				dataWrappers.setData(mPojoList);
			}
		}else{
			dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
 	   if(dataWrappers.getCallStatus()==CallStatusEnum.SUCCEED && dataWrappers.getData()==null){
       	List<BudgetPojo> pas= new ArrayList<BudgetPojo>();
       	dataWrappers.setData(pas);
       }
        return dataWrappers;
    }

	@Override
	public DataWrapper<Void> importBudget(MultipartFile file, HttpServletRequest request, String token,Budget budget) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(file!=null){
				budget.setUploadDate(new Date());
				budget.setUserId(userInMemory.getId());
				List<Budget> ms = new ArrayList<Budget>();
				ReadBudgetExcel rm = new ReadBudgetExcel();
				String newFileName = MD5Util.getMD5String(file.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
	                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				
				ms=rm.getExcelInfo(newFileName, file,budget.getProjectId(),budget.getBudgetBuildingId());
				if(ms.size()>0){
					for(int i=0;i<ms.size();i++){
						ms.get(i).setUploadDate(new Date());
						ms.get(i).setUserId(userInMemory.getId());
					}
					if(ms.size()>0){
						if(!BudgetDao.addBudgetList(ms)){
							result.setErrorCode(ErrorCodeEnum.Error);
						}
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
