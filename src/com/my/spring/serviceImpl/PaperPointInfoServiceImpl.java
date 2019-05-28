package com.my.spring.serviceImpl;

import com.my.spring.DAO.PaperPointInfoDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.PaperPointInfo;
import com.my.spring.model.User;
import com.my.spring.service.FileService;
import com.my.spring.service.PaperPointInfoService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


@Service("paperPointInfoService")
public class PaperPointInfoServiceImpl implements PaperPointInfoService {
    @Autowired
    PaperPointInfoDao paperPointInfoDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileService fileservice;
    @Override
    public DataWrapper<Void> addPaperPointInfo(PaperPointInfo info,MultipartFile paper,HttpServletRequest request,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(!paperPointInfoDao.addPaperPointInfo(info)) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else{
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
    public DataWrapper<Void> deletePaperPointInfo(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!paperPointInfoDao.deletePaperPointInfo(id)) 
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
    public DataWrapper<Void> updatePaperPointInfo(PaperPointInfo info,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
 				if(info!=null){
					if(!paperPointInfoDao.updatePaperPointInfo(info)) 
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
    public DataWrapper<List<PaperPointInfo>> getPaperPointInfoList() {
        return paperPointInfoDao.getPaperPointInfoList();
    }

	@Override
	public DataWrapper<PaperPointInfo> getPaperPointInfoByProjectId(Long projectId,String token) {
		// TODO Auto-generated method stub
		DataWrapper<PaperPointInfo> dataWrapper = new DataWrapper<PaperPointInfo>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			dataWrapper=paperPointInfoDao.getPaperPointInfoByProjectId(projectId);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
}
