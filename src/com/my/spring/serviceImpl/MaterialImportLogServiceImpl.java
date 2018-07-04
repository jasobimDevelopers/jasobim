package com.my.spring.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.DAO.MaterialImportLogDao;
import com.my.spring.DAO.AttenceModelDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.RoleDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.MaterialImportLog;
import com.my.spring.model.MaterialImportLogPojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.MaterialImportLogService;
import com.my.spring.service.FileService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

@Service("materialImportLogService")
public class MaterialImportLogServiceImpl implements MaterialImportLogService{
    @Autowired
    UserDao userDao;
    @Autowired
    MaterialImportLogDao materialImportLogDao;
    @Autowired
    AttenceModelDao attenceModelDao;
    @Autowired
    FileService fileservice;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    RoleDao roleDao;
	@Override
	public DataWrapper<Void> addMaterialImportLog(MaterialImportLog am, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(am!=null){
				am.setUserId(user.getId());
				am.setImportDate(new Date());
				materialImportLogDao.addMaterialImportLog(am);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteMaterialImportLog(Long id, String token) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(!materialImportLogDao.deleteMaterialImportLog(id)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	
	@Override
	public DataWrapper<List<MaterialImportLogPojo>> getMaterialImportLogList(String token, MaterialImportLog duct, Integer pageSize,
			Integer pageIndex) {
		DataWrapper<List<MaterialImportLogPojo>> result = new DataWrapper<List<MaterialImportLogPojo>>();
		List<MaterialImportLogPojo> resultList = new ArrayList<MaterialImportLogPojo>();
		DataWrapper<List<MaterialImportLog>> results = new DataWrapper<List<MaterialImportLog>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			
			results = materialImportLogDao.getMaterialImportLogList(duct.getProjectId(), pageSize, pageIndex);
			if(results.getData()!=null){
				if(!results.getData().isEmpty()){
					for(MaterialImportLog mlp:results.getData()){
						MaterialImportLogPojo gets = new MaterialImportLogPojo();
						gets.setCodeUrl(mlp.getCodeUrl());
						gets.setImportDate(Parameters.getSdfs().format(mlp.getImportDate()));
						gets.setId(mlp.getId());
						resultList.add(gets);
					}
					result.setTotalNumber(results.getTotalNumber());
					result.setCurrentPage(results.getCurrentPage());
					result.setNumberPerPage(results.getNumberPerPage());
					result.setTotalPage(results.getTotalPage());
					result.setData(resultList);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	

}
