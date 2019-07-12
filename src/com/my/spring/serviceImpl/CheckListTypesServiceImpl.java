package com.my.spring.serviceImpl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.spring.DAO.CheckListTypesDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.CheckListTypes;
import com.my.spring.model.User;
import com.my.spring.service.CheckListTypesService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
@Service("checkListTypesService")
public class CheckListTypesServiceImpl implements CheckListTypesService {

	@Autowired
	CheckListTypesDao checkListTypesDao;
	@Autowired
	UserDao userDao;
	@Override
	public DataWrapper<Void> deleteCheckListTypesById(String token, Long id) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(id!=null){
				if(!checkListTypesDao.deleteCheckListTypes(id)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<CheckListTypes> addCheckListTypes(String token, CheckListTypes role) {
		DataWrapper<CheckListTypes> result = new DataWrapper<CheckListTypes>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role!=null){
				role.setCreateUser(user.getId());
				role.setCreateDate(new Date());
				if(!checkListTypesDao.addCheckListTypes(role)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
	@Override
	public DataWrapper<Void> updateCheckListTypes(String token, CheckListTypes role) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(role.getCheckId()!=null){
				CheckListTypes newOne = checkListTypesDao.getById(role.getCheckId());
				if(role.getCheckName()!=null){
					newOne.setCheckName(role.getCheckName());
				}
				if(!checkListTypesDao.updateCheckListTypes(newOne)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<CheckListTypes>> getCheckListTypesList(Integer pageIndex, Integer pageSize,
		CheckListTypes CheckLists, String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<CheckListTypes>> result = new DataWrapper<List<CheckListTypes>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			result=checkListTypesDao.getCheckListTypesListByType(pageIndex, pageSize, CheckLists.getcheckType());
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

}
