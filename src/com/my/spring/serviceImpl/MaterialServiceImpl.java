package com.my.spring.serviceImpl;

import com.my.spring.DAO.MaterialDao;
import com.my.spring.DAO.MaterialTypeDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Material;
import com.my.spring.model.MaterialPojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.MaterialService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Service("materialService")
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    MaterialDao materialDao;
    @Autowired
    MaterialTypeDao materialTypeDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserLogService userLogSerivce;
    @Override
    public DataWrapper<Void> addMaterial(Material m,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(m!=null){
				m.setCreateDate(new Date());
				m.setUserId(userInMemory.getId());
				if(!materialDao.addMaterial(m)){
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
    public DataWrapper<Void> deleteMaterial(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if(userInMemory != null) {
			if(id!=null){
				if(!materialDao.deleteMaterial(id)){
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
    public DataWrapper<Void> updateMaterial(Material m,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	m.setUserId(userInMemory.getId());
			if(!materialDao.updateMaterial(m)) {
			    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<MaterialPojo>> getMaterialList(String token , Integer pageIndex, Integer pageSize, Material m) {
    	DataWrapper<List<MaterialPojo>> dataWrappers = new DataWrapper<List<MaterialPojo>>();
    	DataWrapper<List<Material>> dataWrapper = new DataWrapper<List<Material>>();
    	List<MaterialPojo> mPojoList = new ArrayList<MaterialPojo>();
    	User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) 
        {
			dataWrapper=materialDao.getMaterialList(pageIndex,pageSize,m);
			if(dataWrapper.getData()!=null)
			{		
				if(dataWrapper.getData().size()>0)
				{
					for(int i=0;i<dataWrapper.getData().size();i++)
					{
						MaterialPojo mPojo =new MaterialPojo();
						mPojo.setMaterialName(dataWrapper.getData().get(i).getMaterialName());
						mPojo.setCreateDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getCreateDate()));
						mPojo.setId(dataWrapper.getData().get(i).getId());
						mPojo.setRemark(dataWrapper.getData().get(i).getRemark());
						mPojo.setSize(dataWrapper.getData().get(i).getSize());
						mPojo.setUnicode(dataWrapper.getData().get(i).getUnicode());
						mPojo.setUnit(dataWrapper.getData().get(i).getUnit());
						mPojo.setInNum(dataWrapper.getData().get(i).getInNum());
						mPojo.setOutNum(dataWrapper.getData().get(i).getOutNum());
						mPojo.setLeaveNum(dataWrapper.getData().get(i).getLeaveNum());
						mPojo.setMaterialType(materialTypeDao.getById(dataWrapper.getData().get(i).getMaterialType()).getName());
						if(dataWrapper.getData().get(i).getUserId()!=null){
							User user= new User();
							user=userDao.getById(dataWrapper.getData().get(i).getUserId());
							if(user!=null){
								mPojo.setUserName(user.getUserName());
							}
						}
						if(mPojo!=null){
							mPojoList.add(mPojo);
						}
					}
				}else{
					dataWrappers.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
				}
				if(mPojoList!=null && mPojoList.size()>0){
					dataWrappers.setData(mPojoList);
					dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
					dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
					dataWrappers.setTotalPage(dataWrapper.getTotalPage());
					dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
				}
			}
		}else{
			dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrappers;
    }

	
}
