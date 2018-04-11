package com.my.spring.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.DAO.MaterialTypeDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MaterialType;
import com.my.spring.model.MaterialTypePojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.MaterialTypeService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("materialTypeService")
public class MaterialTypeServiceImpl implements MaterialTypeService {
	
	@Autowired
	UserDao userDao;
	@Autowired
	MaterialTypeDao materialTypeDao;
	@Override
	public DataWrapper<Void> addMaterialType(MaterialType m, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(m!=null){
				m.setCreateDate(new Date());
				m.setUserId(userInMemory.getId());
				if(!materialTypeDao.addMaterialType(m)){
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
	public DataWrapper<Void> deleteMaterialType(Long id, String token) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(id!=null){
				if(!materialTypeDao.deleteMaterialType(id)){
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
	public DataWrapper<Void> updateMaterialType(MaterialType m, String token) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(m!=null){
				MaterialType mt = new MaterialType();
				mt=materialTypeDao.getById(m.getId());
				m.setUserId(mt.getUserId());
				m.setCreateDate(mt.getCreateDate());
				if(mt.getUserId()==userInMemory.getId()){
					m.setUpdateDate(new Date());
					if(!materialTypeDao.updateMaterialType(m)){
						result.setErrorCode(ErrorCodeEnum.Error);
					}
				}else{
					result.setErrorCode(ErrorCodeEnum.AUTH_Error);
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
	public DataWrapper<List<MaterialTypePojo>> getMaterialTypeList(String token, Integer pageIndex, Integer pageSize,
			MaterialType m) {
		DataWrapper<List<MaterialTypePojo>> result = new DataWrapper<List<MaterialTypePojo>>();
		List<MaterialTypePojo> mo = new ArrayList<MaterialTypePojo>();
		DataWrapper<List<MaterialType>> results = new DataWrapper<List<MaterialType>>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			results=materialTypeDao.getMaterialTypeList(pageIndex,pageSize,m);
			if(results.getData()!=null){
				if(results.getData().size()>0){
					for(MaterialType mts:results.getData()){
						MaterialTypePojo mpo = new MaterialTypePojo();
						mpo.setId(mts.getId());
						mpo.setName(mts.getName());
						mpo.setRemark(mts.getRemark());
						mpo.setUserName(userDao.getById(mts.getUserId()).getRealName());
						mpo.setCreateDate(Parameters.getSdf().format(mts.getCreateDate()));
						if(mts.getUpdateDate()!=null){
							mpo.setUpdateDate(Parameters.getSdf().format(mts.getUpdateDate()));
						}
						mo.add(mpo);
					}
					result.setData(mo);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

}
