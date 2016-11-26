package com.my.spring.serviceImpl;

import com.my.spring.DAO.BuildingDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ItemDao;
import com.my.spring.DAO.PaperDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QuantityDao;
import com.my.spring.DAO.QuestionDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Item;
import com.my.spring.model.Paper;
import com.my.spring.model.Project;
import com.my.spring.model.ProjectPojo;
import com.my.spring.model.Quantity;
import com.my.spring.model.Question;
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
    BuildingDao buidlingDao;
    @Autowired
    ItemDao itemDao;
    @Autowired
    PaperDao paperDao;
    @Autowired
    QuantityDao quantityDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileService fileService;
    private String filePath="/files";
    private Integer fileType=2;
    @Override
    public DataWrapper<Project> addProject(Project project,String token,MultipartFile modelfile,MultipartFile picfile,HttpServletRequest request) {
        DataWrapper<Project> dataWrapper = new DataWrapper<Project>();
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
					if(projectDao.addProject(project)){
						dataWrapper=projectDao.findProjectLike(project);
					}
					else{
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						return dataWrapper;
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
    public DataWrapper<Void> deleteProject(Long id,String token,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        Project projects=projectDao.getById(id);
        Long modelId=projects.getModelId();
        Long picId=projects.getPicId();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	if(userInMemory.getUserType()==UserTypeEnum.Admin.getType())
        	{
				if(id!=null)
				{
					/////判断项目下的构件信息是否存在，存在删除，不存在不做处理
					Item item=new Item();
					if(itemDao.getItemList(id, 10, 1, item).getTotalNumber()>0)
					{
						itemDao.deleteItemByPorjectId(id);//////删除项目对应的构件
					}
				    /////判断项目下的图纸信息是否存在，存在删除，不存在不做处理
					Paper paper=new Paper();
					if(paperDao.getPaperList(id, 10, 1, paper).getTotalNumber()>0)
					{
						paperDao.deletePaperByProjectId(id);/////删除项目对应的图纸
					}
					/////判断项目下的问题信息是否存在，存在删除，不存在不做处理
					Question question=new Question();
					if(questionDao.getQuestionList(id, 10, 1, question).getTotalNumber()>0){
						questionDao.deleteQuestionByProjectId(id);
					}
					//////判断项目下的工程量信息是否存在，存在删除，不存在不做处理
					Quantity quantity=new Quantity();
					if(quantityDao.getQuantityList(id, 10, 1, quantity).getTotalNumber()>0){
						quantityDao.deleteQuantityByProjectId(id);
					}
					//////判断项目下的楼栋信息是否存在，存在删除，不存在不做处理
					if(buidlingDao.getBuildingByProjectId(id).getData()!=null){
						buidlingDao.deleteBuildingByProjectId(id);
					}
					if(modelId!=null){
						/////有模型文件时删除模型文件
						if(fileDao.deleteFiles(modelId))
						{
							Files files=fileDao.getById(id);
							fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
						}else{
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}
					}
					if(picId!=null){
						///有图片时删除图片文件
						if(fileDao.deleteFiles(picId))
						{
							Files files=fileDao.getById(id);
							fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
							
						}else{
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}
					}
					if(!projectDao.deleteProject(id))
					{
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
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

	/*@Override
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
	}*/
}
