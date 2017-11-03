package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ProjectStudyDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.ProjectStudy;
import com.my.spring.model.ProjectStudyPojo;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.service.FileService;
import com.my.spring.service.ProjectStudyService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("projectStudyService")
public class ProjectStudyServiceImpl implements ProjectStudyService {
    @Autowired
    ProjectStudyDao projectStudyDao;
    /*@Autowired
    ProjectStudyFileDao ProjectStudyFileDao;*/
    @Autowired
    FileDao fileDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileService fileService;
    @Autowired
    UserLogService userLogSerivce;
    /*private String filePath = "/files";
    private Integer fileType=3;*/
    @Override
    public DataWrapper<Void> addProjectStudy(ProjectStudy projectStudy,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(projectStudy!=null){
				projectStudy.setSubmitUserId(userInMemory.getId());
				projectStudy.setSubmitDate(new Date(System.currentTimeMillis()));
				if(!projectStudyDao.addProjectStudy(projectStudy)) 
				{
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
    public DataWrapper<Void> deleteProjectStudy(Long id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!projectStudyDao.deleteProjectStudy(id)){
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
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
    public DataWrapper<List<ProjectStudyPojo>> getProjectStudyList(String token , Integer pageIndex, Integer pageSize, ProjectStudy ProjectStudy) {
    	DataWrapper<List<ProjectStudyPojo>> dataWrappers = new DataWrapper<List<ProjectStudyPojo>>();
    	DataWrapper<List<ProjectStudy>> dataWrapper = new DataWrapper<List<ProjectStudy>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	if(ProjectStudy.getId()!=null){
        		UserLog userLog = new UserLog();
        		userLog.setActionDate(new Date());
        		//userLog.setFileId(ProjectStudy.getId());
        		userLog.setProjectPart(6);
        		userLog.setUserId(userInMemory.getId());
        		userLog.setVersion("-1");
        		userLogSerivce.addUserLog(userLog, token);
        	}
				dataWrapper=projectStudyDao.getProjectStudysList(pageIndex,pageSize,ProjectStudy);
				if(dataWrapper.getData()!=null){
					List<ProjectStudyPojo> ProjectStudyPojoList = new ArrayList<ProjectStudyPojo>();
					for(int i=0;i<dataWrapper.getData().size();i++){
						ProjectStudyPojo ProjectStudyPojo =new ProjectStudyPojo();
						ProjectStudyPojo.setContent(dataWrapper.getData().get(i).getContent());
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						ProjectStudyPojo.setSubmitDate(sdf.format(dataWrapper.getData().get(i).getSubmitDate()));
						ProjectStudyPojo.setId(dataWrapper.getData().get(i).getId());
						ProjectStudyPojo.setTitle(dataWrapper.getData().get(i).getTitle());
						if(dataWrapper.getData().get(i).getSubmitUserId()!=null){
							ProjectStudyPojo.setSubmitUserName(userDao.getById(dataWrapper.getData().get(i).getSubmitUserId()).getUserName());
						}
						if(ProjectStudyPojo!=null){
							ProjectStudyPojoList.add(ProjectStudyPojo);
						}
					}
					if(ProjectStudyPojoList!=null && ProjectStudyPojoList.size()>0){
						dataWrappers.setData(ProjectStudyPojoList);
						dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
						dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
						dataWrappers.setTotalPage(dataWrapper.getTotalPage());
						dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
					}else{
						dataWrappers.setErrorCode(ErrorCodeEnum.Error);
					}
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
        return dataWrappers;
    }

	@Override
	public DataWrapper<List<ProjectStudy>> getProjectStudyListByUserId(Long userId,String token) {
		DataWrapper<List<ProjectStudy>> dataWrapper = new DataWrapper<List<ProjectStudy>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userId!=null){
				if(projectStudyDao.getProjectStudysListByUserId(userId)==null) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else
					return projectStudyDao.getProjectStudysListByUserId(userId);
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
	}

	

	
}
