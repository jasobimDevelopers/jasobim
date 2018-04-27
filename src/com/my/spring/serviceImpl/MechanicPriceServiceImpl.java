package com.my.spring.serviceImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.DAO.AttenceLogDao;
import com.my.spring.DAO.AttenceModelDao;
import com.my.spring.DAO.MechanicPriceDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserLogDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MechanicPrice;
import com.my.spring.model.MechanicPricePojo;
import com.my.spring.model.AttenceModel;
import com.my.spring.model.Files;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.MechanicPriceService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.InstanceUtil;
import com.my.spring.utils.SessionManager;

@Service("mechanicPriceService")
public class MechanicPriceServiceImpl implements MechanicPriceService{
    @Autowired
    UserDao userDao;
    @Autowired
    MechanicPriceDao mechanicPriceDao;
    @Autowired
    AttenceModelDao attenceModelDao;
    @Autowired
    UserLogDao userLogDao;
    @Autowired
    FileService fileservice;
    @Autowired
    ProjectDao projectDao;

	@Override
	public DataWrapper<Void> addMechanicPrice(MechanicPrice am, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(am.getMechanicId()!=null){
				am.setCreateDate(new Date());
				if(!mechanicPriceDao.addMechanicPrice(am)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteMechanicPrice(Long id, String token) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!mechanicPriceDao.deleteMechanicPrice(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> updateMechanicPrice(MechanicPrice am, String token) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!mechanicPriceDao.updateMechanicPrice(am)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<MechanicPricePojo>> getMechanicPriceList(String token, MechanicPrice duct, Integer pageSize,
			Integer pageIndex) {
		DataWrapper<List<MechanicPricePojo>> result = new DataWrapper<List<MechanicPricePojo>>();
		List<MechanicPricePojo> results = new ArrayList<MechanicPricePojo>();
		DataWrapper<List<MechanicPrice>> amList = new DataWrapper<List<MechanicPrice>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(amList.getData()!=null)
			{
			
			}else{
				result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> updateMechanicPrice(String[] info, String token, String dates) {
		// TODO Auto-generated method stub
		return null;
	}

}
