package com.my.spring.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.DAO.AttenceModelDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserLogDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.AttenceModel;
import com.my.spring.model.AttenceModelPojo;
import com.my.spring.model.Project;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.AttenceModelService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("attenceModelService")
public class AttenceModelServiceImpl implements AttenceModelService{
    @Autowired
    UserDao userDao;
    @Autowired
    AttenceModelDao attenceModelDao;
    @Autowired
    UserLogDao userLogDao;
    @Autowired
    ProjectDao projectDao;

	@Override
	public DataWrapper<Void> addAttenceModel(AttenceModel am, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(am!=null){
				am.setCreateDate(new Date());
				am.setUserId(user.getId());
			}
			if(!attenceModelDao.addAttenceModel(am)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteAttenceModel(Long id, String token) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!attenceModelDao.deleteAttenceModel(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> updateAttenceModel(AttenceModel am, String token) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!attenceModelDao.updateAttenceModel(am)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<List<AttenceModelPojo>> getAttenceModelList(String token, AttenceModel duct, Integer pageSize,
			Integer pageIndex) {
		DataWrapper<List<AttenceModelPojo>> result = new DataWrapper<List<AttenceModelPojo>>();
		List<AttenceModelPojo> results = new ArrayList<AttenceModelPojo>();
		DataWrapper<List<AttenceModel>> amList = new DataWrapper<List<AttenceModel>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			amList = attenceModelDao.getAttenceModelList(pageIndex, pageSize, duct);
			if(amList.getData()!=null)
			{
				if(amList.getData().size()>0){
					for(AttenceModel am : amList.getData()){
						AttenceModelPojo attenceModelPojo = new AttenceModelPojo();
						attenceModelPojo.setAttenceDay(am.getAttenceDay());
						attenceModelPojo.setAttenceRange(am.getAttenceRange());
						attenceModelPojo.setPlace(am.getPlace());
						attenceModelPojo.setStartTime(am.getStartTime());
						attenceModelPojo.setEndTime(am.getEndTime());
						attenceModelPojo.setId(am.getId());
						attenceModelPojo.setCreateDate(Parameters.getSdf().format(am.getCreateDate()));
						if(am.getUserId()!=null){
							User users = userDao.getById(am.getUserId());
							if(users!=null){
								attenceModelPojo.setUserName(users.getRealName());
							}
						}
						if(am.getProjectId()!=null){
							Project project = projectDao.getById(am.getProjectId());
							if(project!=null){
								attenceModelPojo.setProjectName(project.getName());
							}
						}
						results.add(attenceModelPojo);
					}
					result.setData(results);
					result.setCallStatus(amList.getCallStatus());
					result.setCurrentPage(amList.getCurrentPage());
					result.setNumberPerPage(amList.getNumberPerPage());
					result.setTotalNumber(amList.getTotalNumber());
					result.setTotalPage(amList.getTotalPage());
					result.setErrorCode(amList.getErrorCode());
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
