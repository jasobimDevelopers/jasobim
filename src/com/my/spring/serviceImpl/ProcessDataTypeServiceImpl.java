package com.my.spring.serviceImpl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.spring.DAO.ProcessDataTypeDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.ProcessDataType;
import com.my.spring.model.ProcessDataTypePojo;
import com.my.spring.model.User;
import com.my.spring.service.ProcessDataTypeService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("processDataTypeService")
public class ProcessDataTypeServiceImpl implements ProcessDataTypeService {
	@Autowired
	ProcessDataTypeDao processDataTypeDao;
	@Autowired
	UserDao userDao;
	@Override
	public DataWrapper<List<ProcessDataTypePojo>> getProcessDataTypeList(String token) {
		// TODO Auto-generated method stub
		List<ProcessDataType> dataWrapper = new ArrayList<ProcessDataType>();
		List<ProcessDataTypePojo> processDataTypePojo = new ArrayList<ProcessDataTypePojo>();
		DataWrapper<List<ProcessDataTypePojo>> dataWrapperpojo = new DataWrapper<List<ProcessDataTypePojo>>();
		User adminInMemory = SessionManager.getSession(token);
		if (adminInMemory != null) {
			User adminInDB = userDao.getById(adminInMemory.getId());
			if (adminInDB.getUserType() == UserTypeEnum.Admin.getType()) {
				dataWrapper=processDataTypeDao.getProcessDataTypeList();
				if(!dataWrapper.isEmpty()){
					for(int i=0;i<dataWrapper.size();i++){
						ProcessDataTypePojo pojos=new ProcessDataTypePojo();
						pojos.setId(dataWrapper.get(i).getId());
						pojos.setName(dataWrapper.get(i).getName());
						processDataTypePojo.add(pojos);
					}
					dataWrapperpojo.setData(processDataTypePojo);
				}
			} else {
				dataWrapperpojo.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrapperpojo.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapperpojo;
	}


	@Override
	public DataWrapper<Void> deleteProcessDataType(Long id, String token) {
		DataWrapper<Void> dataWrapper=new DataWrapper<Void>();
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			User adminInDB = userDao.getById(userInMemory.getId());
			if (adminInDB.getUserType() == UserTypeEnum.Admin.getType()) {
				if(processDataTypeDao.deleteProcessDataType(id)){
					dataWrapper.setErrorCode(ErrorCodeEnum.No_Error);
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> addProcessDataType(String name,String token) {
		DataWrapper<Void> dataWrapper = new DataWrapper<>();
		
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			ProcessDataType pdt=new ProcessDataType();
			pdt.setName(name);
			pdt.setCreateDate(new Date());
			pdt.setCreateUser(userInMemory.getId());
			if(!processDataTypeDao.addProcessDataType(pdt)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		return dataWrapper;
	}


	

}
