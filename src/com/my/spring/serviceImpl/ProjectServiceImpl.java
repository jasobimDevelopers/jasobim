package com.my.spring.serviceImpl;

import com.my.spring.DAO.BuildingDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ItemDao;
import com.my.spring.DAO.PaperDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QuantityDao;
import com.my.spring.DAO.QuestionDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.VideoDao;
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
    VideoDao videoDao;
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
    private String filePath1="files";
    private Integer fileType=2;
    @Override
    public DataWrapper<ProjectPojo> addProject(Project project,String token,MultipartFile modelfile,MultipartFile picfile,HttpServletRequest request) {
        DataWrapper<ProjectPojo> dataWrapper = new DataWrapper<ProjectPojo>();
        DataWrapper<Project> dataWrappers = new DataWrapper<Project>();
        User userInMemory = SessionManager.getSession(token);
        ProjectPojo pojo=new ProjectPojo();
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(project!=null){
					if(modelfile!=null){
						String path=filePath1+"/"+"projectmodels";
						Files newfile=fileService.uploadFile(path, modelfile,fileType,request);
						project.setModelId(newfile.getId());
						pojo.setModelUrl(newfile.getUrl());
					}
					if(picfile!=null){
						String path=filePath1+"/"+"projectpics";
						Files newfile=fileService.uploadFile(path, picfile,fileType,request);
						project.setPicId(newfile.getId());
						pojo.setPicUrl(newfile.getUrl());
					}
					if(projectDao.addProject(project)){
						dataWrappers=projectDao.findProjectLike(project);
						pojo.setBuildingUnit(dataWrappers.getData().getBuildingUnit());
						pojo.setConstructionUnit(dataWrappers.getData().getConstructionUnit());
						pojo.setDescription(dataWrappers.getData().getDescription());
						pojo.setDesignUnit(dataWrappers.getData().getDesignUnit());
						pojo.setId(dataWrappers.getData().getId());
						pojo.setLeader(dataWrappers.getData().getLeader());
						pojo.setName(dataWrappers.getData().getName());
						pojo.setPlace(dataWrappers.getData().getPlace());
						pojo.setNum(dataWrappers.getData().getNum());
						pojo.setPhase(dataWrappers.getData().getPhase());
						pojo.setStartDate(dataWrappers.getData().getStartDate());
						pojo.setVersion(dataWrappers.getData().getVersion());
						pojo.setIsIos(dataWrappers.getData().getIsIos());
						dataWrapper.setData(pojo);
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
					 /////判断项目下的图纸信息是否存在，存在删除，不存在不做处理
					Paper paper=new Paper();
					if(paperDao.getPaperList(id, 10, 1, paper,null).getTotalNumber()>0)
					{
						paperDao.deletePaperByProjectId(id);/////删除项目对应的图纸
					}
					
					/////判断项目下的构件信息是否存在，存在删除，不存在不做处理
					Item item=new Item();
					if(itemDao.getItemList(id, 10, 1, item).getTotalNumber()>0)
					{
						itemDao.deleteItemByPorjectId(id);//////删除项目对应的构件
					}
				   
					/////判断项目下的问题信息是否存在，存在删除，不存在不做处理
					Question question=new Question();
					if(questionDao.getQuestionList(null,id, 10, 1, question,null).getTotalNumber()>0){
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
					//////判断项目下的交底视频是否存在，存在删除，不存在不作处理
					if(videoDao.getByProjectId(id)==true){
						videoDao.deleteVideoByProjectId(id);
					}
					/////删除项目自身文件
					if(!projectDao.deleteProject(id))
					{
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
					if(modelId!=null){
						/////有模型文件时删除模型文件
						Files files=fileDao.getById(modelId);
						if(fileDao.deleteFiles(modelId))
						{
							fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
						}else{
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}
					}
					if(picId!=null){
						///有图片时删除图片文件
						Files files=fileDao.getById(picId);
						if(fileDao.deleteFiles(picId))
						{
							fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
							
						}else{
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}
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
							String path=filePath1+"/"+"projectmodels";
							Files newfile=fileService.uploadFile(path, modelFile,fileType,request);
							project.setModelId(newfile.getId());
						}
						if(picFile!=null){
							String path=filePath1+"/"+"projectpics";
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
    		if(userInMemory.getUserType()==0 || userInMemory.getUserType()==1 
    				|| userInMemory.getWorkName().contains("经理") || userInMemory.getWorkName().contains("科长")){
    			dataWrapper=projectDao.getProjectList(pageSize, pageIndex,project,"-1");
    		}else{
    			dataWrapper=projectDao.getProjectList(pageSize, pageIndex,project,userInMemory.getProjectList());
    		}
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
					projectpojo.setState(dataWrapper.getData().get(i).getState());
					projectpojo.setIsIos(dataWrapper.getData().get(i).getIsIos());
					if(dataWrapper.getData().get(i).getModelPart()!=null && dataWrapper.getData().get(i).getModelPart()!=""){
						projectpojo.setModelPart(dataWrapper.getData().get(i).getModelPart());
					}
					if(dataWrapper.getData().get(i).getModelId()!=null){
						Files files=fileDao.getById(dataWrapper.getData().get(i).getModelId());
						projectpojo.setModelUrl(files.getUrl());
						projectpojo.setModelName(files.getName());
					}
					if(dataWrapper.getData().get(i).getPicId()!=null){
						Files filess=fileDao.getById(dataWrapper.getData().get(i).getPicId());
						projectpojo.setPicUrl(filess.getUrl());
						projectpojo.setPicName(filess.getName());
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
	public DataWrapper<ProjectPojo> getProjectDetailsByAdmin(Long projectId, String token) {
		DataWrapper<ProjectPojo> dataWrapper = new DataWrapper<ProjectPojo>();
		User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				if(projectId!=null){
					Project project=projectDao.getById(projectId);
					ProjectPojo projectPojo=new ProjectPojo();
					
					if(project==null) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Project_Not_Existed);
					else{
						if(project.getPicId()!=null)
						{
							Files files=new Files();
							files=fileDao.getById(project.getPicId());
							if(files!=null){
								projectPojo.setPicUrl(files.getUrl());
							}
						}
						if(project.getModelId()!=null){
							Files filess=new Files();
							filess=fileDao.getById(project.getModelId());
							if(filess!=null){
								projectPojo.setModelUrl(filess.getUrl());
							}
						}
						projectPojo.setBuildingUnit(project.getBuildingUnit());
						projectPojo.setConstructionUnit(project.getConstructionUnit());
						projectPojo.setDescription(project.getDescription());
						projectPojo.setDesignUnit(project.getDesignUnit());
						projectPojo.setId(project.getId());
						projectPojo.setLeader(project.getLeader());
						projectPojo.setName(project.getName());
						projectPojo.setNum(project.getNum());
						projectPojo.setPhase(project.getPhase());
						projectPojo.setPlace(project.getPlace());
						projectPojo.setStartDate(project.getStartDate());
						projectPojo.setVersion(project.getVersion());
						projectPojo.setState(project.getState());
						projectPojo.setIsIos(project.getIsIos());
						projectPojo.setModelPart(project.getModelPart());
						dataWrapper.setData(projectPojo);
					}
						
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
