package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Project;
import com.my.spring.model.ProjectPojo;
import com.my.spring.model.User;
import com.my.spring.service.FileService;
import com.my.spring.service.ProjectService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectDao projectDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileService fileService;
    private String filePath="/files";
    private Integer fileType=2;
    @Override
    public DataWrapper<Void> addProject(Project project,String token,MultipartFile modelfile,MultipartFile picfile,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(project!=null){
					if(modelfile!=null){
						String path=filePath+"/"+"projectmodels"+"/";
						Files newfile=fileService.uploadFile(path, modelfile,fileType,request);
						project.setModelId(newfile.getId());
					}
					if(picfile!=null){
						String path=filePath+"/"+"projectpics"+"/";
						Files newfile=fileService.uploadFile(path, picfile,fileType,request);
						project.setPicId(newfile.getId());
					}
					if(!projectDao.addProject(project))
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
    public DataWrapper<Void> deleteProject(Long id,String token,Long modelid,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(id!=null){
				if(!projectDao.deleteProject(id)) 
					{
					fileDao.deleteFiles(modelid);//删除文件表的信息
					Files files=fileDao.getById(id);
					fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		            }
				else
					return dataWrapper;
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateProject(Project project,String token,MultipartFile modelFile,MultipartFile picFile,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
					if(project!=null){
						if(modelFile!=null){
							String path=filePath+"/"+"projectmodels"+"/";
							Files newfile=fileService.uploadFile(path, modelFile,fileType,request);
							project.setModelId(newfile.getId());
						}
						if(picFile!=null){
							String path=filePath+"/"+"projectpics"+"/";
							Files newfile=fileService.uploadFile(path, picFile,fileType,request);
							project.setPicId(newfile.getId());
						}
						if(!projectDao.updateProject(project)) 
				            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						else
							return dataWrapper;
				        
					}
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Existed);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<ProjectPojo>> getProjectList(Integer pageIndex, Integer pageSize, Project project, String token) {
    	DataWrapper<List<ProjectPojo>> dataWrappers=new DataWrapper<List<ProjectPojo>>();
    	DataWrapper<List<Project>> dataWrapper=new DataWrapper<List<Project>>();
    	List<ProjectPojo> pojoproject=new ArrayList<ProjectPojo>();
    	User userInMemory =SessionManager.getSession(token);
    	if (userInMemory != null) {	
				dataWrapper=projectDao.getProjectList(pageSize, pageIndex,project);
				for(int i=0;i<dataWrapper.getData().size();i++){
					ProjectPojo projectpojo=new ProjectPojo();
					projectpojo.setId(dataWrapper.getData().get(i).getId());
					projectpojo.setBuildingUnit(dataWrapper.getData().get(i).getBuildingUnit());
					projectpojo.setConstructionUnit(dataWrapper.getData().get(i).getConstructionUnit());
					projectpojo.setDescription(dataWrapper.getData().get(i).getDescription());
					projectpojo.setDesignUnit(dataWrapper.getData().get(i).getDesignUnit());
					projectpojo.setLeader(dataWrapper.getData().get(i).getLeader());
					projectpojo.setName(dataWrapper.getData().get(i).getName());
					projectpojo.setNum(dataWrapper.getData().get(i).getNum());
					projectpojo.setPhase(dataWrapper.getData().get(i).getPhase());
					projectpojo.setPlace(dataWrapper.getData().get(i).getPlace());
					projectpojo.setStartDate(dataWrapper.getData().get(i).getStartDate());
					projectpojo.setVersion(dataWrapper.getData().get(i).getVersion());
					if(dataWrapper.getData().get(i).getModelId()!=null || dataWrapper.getData().get(i).getPicId()!=null){
						Files files=fileDao.getById(dataWrapper.getData().get(i).getModelId());
						Files filess=fileDao.getById(dataWrapper.getData().get(i).getPicId());
						projectpojo.setModelUrl(files.getUrl());
						projectpojo.setPicUrl(filess.getUrl());
					}
					pojoproject.add(i, projectpojo);;
				}
				dataWrappers.setData(pojoproject);
				dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
    			dataWrappers.setCallStatus(dataWrapper.getCallStatus());
    			dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
    			dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
    			dataWrappers.setTotalPage(dataWrapper.getTotalPage());
		} else {
			dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrappers;
    }

	@Override
	public DataWrapper<Project> getProjectDetailsByAdmin(Long projectId, String token) {
		DataWrapper<Project> dataWrapper = new DataWrapper<Project>();
		 User userInMemory = SessionManager.getSession(token);
	        if (userInMemory != null) {
					if(projectId!=null){
						Project project=projectDao.getById(projectId);
						if(project==null) 
				            dataWrapper.setErrorCode(ErrorCodeEnum.Project_Not_Existed);
						else
							dataWrapper.setData(project);
					}
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
			}
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<Project>> findProjectLike(Project project, String token) {
		DataWrapper<List<Project>> dataWrapper = new DataWrapper<List<Project>>();
		User adminInMemory = SessionManager.getSession(token);
		if(adminInMemory!=null){
			if(project!=null){
				dataWrapper=projectDao.findProjectLike(project);
				if(dataWrapper!=null && dataWrapper.getData().size()>0){
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
			
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
}
