package com.my.spring.serviceImpl;

import com.my.spring.DAO.QualityFineDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.QualityFine;
import com.my.spring.model.QualityFinePojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.QualityFineService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("qualityFineService")
public class QualityFineServiceImpl implements QualityFineService {
    @Autowired
    QualityFineDao fineDao;
    @Autowired
    UserDao userDao;
    @Override
    public DataWrapper<Void> addQualityFine(QualityFine building,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(building!=null){
					building.setCreateDate(new Date());
					building.setUserId(userInMemory.getId());
					if(!fineDao.addQualityFine(building)) 
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
    
    
    
    public DataWrapper<Void> deleteQualityFine(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!fineDao.deleteQualityFine(id)) 
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
    public DataWrapper<Void> updateQualityFine(QualityFine building,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
 				if(building!=null){
					if(!fineDao.updateQualityFine(building)) 
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
	public DataWrapper<QualityFine> getQualityFineByProjectId(Long projectId,String token) {
		DataWrapper<QualityFine> dataWrapper = new DataWrapper<QualityFine>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			
				dataWrapper=fineDao.getQualityFineByProjectId(projectId);
			
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}


	@Override
	public DataWrapper<List<QualityFinePojo>> getQualityFineList(Integer pageIndex, Integer pageSize, QualityFine qf,
			String token) {
		DataWrapper<List<QualityFinePojo>> result = new DataWrapper<List<QualityFinePojo>>();
		List<QualityFinePojo> results = new ArrayList<QualityFinePojo>();
		DataWrapper<List<QualityFine>> gets = new DataWrapper<List<QualityFine>>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			gets = fineDao.getQualityFineList(pageIndex, pageSize, qf);
			if(gets.getData()!=null){
				if(gets.getData().size()>0){
					for(QualityFine qfs:gets.getData()){
						QualityFinePojo qfp = new QualityFinePojo();
						qfp.setId(qfs.getId());
						qfp.setForfeit(qfs.getForfeit());
						qfp.setCreateDate(Parameters.getSdf().format(qfs.getCreateDate()));
						qfp.setDetail(qfs.getDetail());
						qfp.setLevel(qfs.getLevel());
						qfp.setProjectId(qfs.getProjectId());
						qfp.setCheckDate(qfs.getCheckDate());
						if(qfs.getUserId()!=null){
							User users = userDao.getById(qfs.getUserId());
							if(users!=null){
								qfp.setUserName(users.getRealName());
							}
						}
						results.add(qfp);
					}
					result.setData(results);
					result.setNumberPerPage(gets.getNumberPerPage());
					result.setTotalNumber(gets.getTotalNumber());
					result.setTotalPage(gets.getTotalPage());
					result.setCurrentPage(gets.getCurrentPage());
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
}
