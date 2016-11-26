package com.my.spring.serviceImpl;

import com.my.spring.DAO.QuantityDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Building;
import com.my.spring.model.Item;
import com.my.spring.model.Quantity;
import com.my.spring.model.QuantityPojo;
import com.my.spring.model.User;
import com.my.spring.service.QuantityService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MD5Util;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("quantityService")
public class QuantityServiceImpl implements QuantityService {
    @Autowired
    QuantityDao quantityDao;
    @Autowired
    UserDao userDao;
    @Override
    public DataWrapper<Void> addQuantity(Quantity quantity,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(quantity!=null){
					if(!quantityDao.addQuantity(quantity)) 
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
    public DataWrapper<Void> deleteQuantity(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!quantityDao.deleteQuantity(id)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateQuantity(Quantity quantity,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(quantity!=null){
					if(!quantityDao.updateQuantity(quantity)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
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
    public DataWrapper<List<Quantity>> getQuantityList(Long projectId,String token,Integer pageIndex,Integer pageSize,Quantity quantity) {
    	DataWrapper<List<Quantity>> datawrapper=new DataWrapper<List<Quantity>>();
    	User userInMemory=SessionManager.getSession(token);
    	if(userInMemory!=null){
    		
    			datawrapper=quantityDao.getQuantityList(projectId,pageSize, pageIndex,quantity);
    	}else{
    		datawrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
    	
    	return datawrapper;
    }

	@Override
	public DataWrapper<Quantity> getQuantityDetailsByAdmin(Long QuantityId, String token) {
		DataWrapper<Quantity> dataWrapper = new DataWrapper<Quantity>();
		 User userInMemory = SessionManager.getSession(token);
	        if (userInMemory != null) {
				if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
					if(QuantityId!=null){
						Quantity quantity=quantityDao.getById(QuantityId);
						if(quantity==null) 
				            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
						else
							dataWrapper.setData(quantity);
				        
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
	public boolean batchImport(String filePath, MultipartFile file, String token, HttpServletRequest request,
			Long projectId) {
		if (file == null || filePath == null || filePath.equals("")) {
			return false;
		}
		DataWrapper<Void> dataWrapper=new DataWrapper<Void>();
		boolean b = false;
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
		        String newFileName = MD5Util.getMD5String(file.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
		                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		        //创建处理EXCEL
		        ReadQuantityExcel readExcel=new ReadQuantityExcel();
		        
		        //解析excel，构件信息集合。
		        List<Quantity> quantityList = readExcel.getExcelInfo(newFileName ,file);
	    		quantityDao.deleteQuantityByProjectId(projectId);
	    		quantityDao.addQuantityList(quantityList);
		
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
			
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return b;
	}
}
