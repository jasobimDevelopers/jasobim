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
import java.util.Date;
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
    public DataWrapper<ProjectPojo> addProject(Project project,String token,MultipartFile modelfile[],MultipartFile picfile[],HttpServletRequest request) {
        DataWrapper<ProjectPojo> dataWrapper = new DataWrapper<ProjectPojo>();
        DataWrapper<Project> dataWrappers = new DataWrapper<Project>();
        ///班组信息存储
        if(project.getTeamList()!=null){
        	String[] temp=project.getTeamList().split(",");
        	String test="";
        	for(int i=0;i<temp.length;i++){
        		if(i==0){
        			test=test+i;
        		}else{
        			test=test+","+i;
        		}
        	}
        	project.setTeamId(test);
        }
        ////////////
        User userInMemory = SessionManager.getSession(token);
        ProjectPojo pojo=new ProjectPojo();
        String[] urlList = new String[modelfile.length];
        
        String[] urlList2 = new String[picfile.length];
        
        Integer[] ioIos = new Integer[modelfile.length];
        Long[] modelidList=new Long[modelfile.length];
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(project!=null){
					if(modelfile.length>=0)
					{
						String temp="";
						String modelName="";
						for(int i=0;i<modelfile.length;i++)
						{
							String path=filePath1+"/"+"projectmodels/"+project.getId();
							Files newfile=fileService.uploadFile(path, modelfile[i],fileType,request);
							if(i!=0)
							{
								temp=temp+","+newfile.getId();//0.安卓	1.ios   2.pad模型
								String nameList=modelfile[i].getOriginalFilename().substring(0,modelfile[i].getOriginalFilename().lastIndexOf("."));
								String[] nameLists=nameList.split("_");   
								modelName=modelName+","+nameLists[0];
								ioIos[i]=Integer.valueOf(nameLists[1]);
							}else
							{
								temp=newfile.getId().toString();
								String nameList=modelfile[i].getOriginalFilename().substring(0,modelfile[i].getOriginalFilename().lastIndexOf("."));
								String[] nameLists=nameList.split("_");   
								modelName=nameLists[0];
								ioIos[i]=Integer.valueOf(nameLists[1]);
							}
							modelidList[i]=newfile.getId();
							urlList[i]=newfile.getUrl();
						}
						project.setModelId(temp);
						project.setModelPart(modelName);
					}
					if(picfile!=null){
						String temp="";
						String picName = "";
						for(int i=0;i<picfile.length;i++)
						{
							String path=filePath1+"/"+"projectpics/"+project.getId();
							Files newfile=fileService.uploadFile(path, picfile[i],fileType,request);
							if(i!=0){
								temp=temp+","+newfile.getId();
								picName=picName+","+picfile[i].getOriginalFilename();
							}else{
								temp=newfile.getId().toString();
								picName=picfile[i].getOriginalFilename();
							}
							urlList2[i]=newfile.getUrl();
							project.setPicId(newfile.getId().toString());
						}
						//project.setPicId();
						//project.set();
						if(urlList2.length>0)
						{
							pojo.setPicUrl(urlList2[0]);
						}
					}
					project.setUpdateDate(new Date(System.currentTimeMillis()));
					if(projectDao.addProject(project))
					{
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
						dataWrapper.setData(pojo);
					}else{
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
				}else
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
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
        String modelId[]=null;
        if(projects.getModelId()!=null && !projects.getModelId().equals("") ){
        	modelId=projects.getModelId().split(",");
        }
        String[] picId=null;
        if(projects.getPicId()!=null && !projects.getPicId().equals("")){
        	picId=projects.getPicId().split(",");
        }
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
					if(questionDao.getQuestionList(null,id, 10, 1, question,null,null).getTotalNumber()>0){
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
					if(modelId!=null){
						/////有模型文件时删除模型文件
						for(int i=0;i<modelId.length;i++){
							Files files=fileDao.getById(Long.valueOf(modelId[i]));
							if(fileDao.deleteFiles(Long.valueOf(modelId[i])))
							{
								fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
							}else{
								dataWrapper.setErrorCode(ErrorCodeEnum.Error);
							}
						}
						
					}
					if(picId!=null){
						///有图片时删除图片文件
						for(int j=0;j<picId.length;j++){
							Files files=fileDao.getById(Long.valueOf(picId[j]));
							if(fileDao.deleteFiles(Long.valueOf(picId[j])))
							{
								fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
								
							}else{
								dataWrapper.setErrorCode(ErrorCodeEnum.Error);
							}
						}
						
					}
					/////删除项目自身文件
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
    public DataWrapper<Void> updateProject(Project project,String token,MultipartFile modelFile[],MultipartFile picFile[],HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
      ///班组信息存储
        if(project.getTeamList()!=null){
        	String[] temp=project.getTeamList().split(",");
        	String test="";
        	for(int i=0;i<temp.length;i++){
        		if(i==0){
        			test=test+i;
        		}else{
        			test=test+","+i;
        		}
        	}
        	project.setTeamId(test);
        }
        if (userInMemory != null) {
				if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
					if(project!=null){
						String modelid="";
						String modelName="";
						String isIos="";
						if(modelFile.length>=0){
							for(int i=0;i<modelFile.length;i++){
								String path=filePath1+"/"+"projectmodels/"+project.getId();
								Files newfile=fileService.uploadFile(path, modelFile[i],fileType,request);
								if(i!=0){
									modelid=modelid+","+newfile.getId();
									isIos=isIos+","+newfile.getRealName().split("_")[1];
								}else{
									modelid=newfile.getId().toString();
									isIos=newfile.getRealName().split("_")[1];
								}
							}
							project.setModelId(modelid);
							project.setIsIos(isIos);
						}
						if(picFile.length>=0){
							String picid="";
							for(int i=0;i<picFile.length;i++){
								String path=filePath1+"/"+"projectpics/"+project.getId();
								Files newfile=fileService.uploadFile(path, picFile[i],fileType,request);
								if(i!=0){
									picid=picid+","+newfile.getId();
								}else{
									picid=newfile.getId().toString();
								}
							}
							project.setPicId(picid);
						}
						project.setUpdateDate(new Date(System.currentTimeMillis()));
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

    @SuppressWarnings("unused")
	@Override
    public DataWrapper<List<ProjectPojo>> getProjectList(Integer pageIndex, Integer pageSize, Project project, String token) {
    	DataWrapper<List<ProjectPojo>> dataWrappers=new DataWrapper<List<ProjectPojo>>();
    	DataWrapper<List<Project>> dataWrapper=new DataWrapper<List<Project>>();
    	List<ProjectPojo> pojoproject=new ArrayList<ProjectPojo>();
    	User userInMemory =SessionManager.getSession(token);
    	Integer isios=-1;
    	isios=userInMemory.getSystemId();
    	if (userInMemory != null) {
    		if(userInMemory.getUserType()==0 || userInMemory.getUserType()==1 
    				|| userInMemory.getWorkName().contains("总经理") || userInMemory.getWorkName().contains("科长") || userInMemory.getWorkName().contains("主管")){
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
				String[] tests=null;
				if(dataWrapper.getData().get(i).getModelPart()!=null && !dataWrapper.getData().get(i).getModelPart().equals("")){
					tests=dataWrapper.getData().get(i).getModelPart().split(",");
				}
				if(dataWrapper.getData().get(i).getModelId()!=null && !dataWrapper.getData().get(i).getModelId().equals("")){
					String[] temp=dataWrapper.getData().get(i).getModelId().split(",");
					String[] urlList=new String[temp.length];
					String[] nameList=new String[temp.length];
					Integer[] isIos = new Integer[temp.length];
					for(int k=0;k<temp.length;k++){
						String test="";
						Files files=fileDao.getById(Long.valueOf(temp[k]));
						urlList[k]=files.getUrl();
						if(files.getRealName()!=null){
							test=files.getRealName();
							String[] nameLists=test.split("_");   
							nameList[k]=nameLists[0];
							isIos[k]=Integer.valueOf(nameLists[1]);
						}
					}
					/////////
					for(int j=0;j<isIos.length;j++){
						if(isIos[j]==isios){
							projectpojo.setModelUrl(urlList[j]);
						}
					}
				}
				if(dataWrapper.getData().get(i).getPicId()!=null && !dataWrapper.getData().get(i).getPicId().equals("")){
					Files filess=fileDao.getById(Long.valueOf(dataWrapper.getData().get(i).getPicId()));
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
		}else{
			dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrappers;
    }

	@SuppressWarnings("unused")
	@Override
	public DataWrapper<ProjectPojo> getProjectDetailsByAdmin(Long projectId, String token) {
		DataWrapper<ProjectPojo> dataWrapper = new DataWrapper<ProjectPojo>();
		User userInMemory = SessionManager.getSession(token);
		Integer isios=-1;
		isios=userInMemory.getSystemId();
        if (userInMemory != null) {
			if(projectId!=null){
				Project project=projectDao.getById(projectId);
				ProjectPojo projectPojo=new ProjectPojo();
				
				if(project==null) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Project_Not_Existed);
				else{
					if(project.getPicId()!=null && !project.getPicId().equals(""))
					{
						Files files=new Files();
						files=fileDao.getById(Long.valueOf(project.getPicId()));
						if(files!=null){
							projectPojo.setPicUrl(files.getUrl());
						}
					}
					if(project.getModelId()!=null && !project.getModelId().equals("")){
						String[] urllist=project.getModelId().split(",");
						String[] urlList=new String[urllist.length];
						String[] nameList=new String[urllist.length];
						Integer[] isIos = new Integer[urllist.length];
						for(int j=0;j<urllist.length;j++){
							String test="";
							Files filess=new Files();
							filess=fileDao.getById(Long.valueOf(urllist[j]));
							urlList[j]=filess.getUrl();
							if(filess.getRealName()!=null && !filess.getRealName().equals("")){
								test=filess.getRealName();
								nameList[j]=test.split("_")[0];
								isIos[j]=Integer.valueOf(test.split("_")[1]);
							}
						}
						for(int j=0;j<isIos.length;j++)
						{
							if(isIos[j]==isios)
							{
								if(urlList!=null)
								{
									projectPojo.setModelUrl(urlList[j]);
								}
							}
						}
					}
					if(userInMemory.getUserType()!=3){
						if(project.getTeamList()!=null){
							String[] teamNames=project.getTeamList().split(",");
							String[] teamIds=project.getTeamId().split(",");
							projectPojo.setTeamList(teamNames);
							projectPojo.setTeamId(teamIds);
						}
					}else{
						if(userInMemory.getTeamId()!=null){
							if(project.getTeamList()!=null){
								String[] teamNames=project.getTeamList().split(",");
								String[] teamIds=project.getTeamId().split(",");
								if(userInMemory.getWorkName().equals("安装负责人")){
									projectPojo.setTeamList(teamNames);
									projectPojo.setTeamId(teamIds);
								}else{
									String[] teamname=userInMemory.getTeamInformation().split(",");								
									projectPojo.setTeamList(teamname);
									String[] teamId=new String[teamname.length];
									for(int j=0;j<teamname.length;j++){
										teamId[j]=String.valueOf(j);
									}
									projectPojo.setTeamId(teamId);
								}
								
							}
						}
					}
					projectPojo.setModelPart(project.getModelPart().split(","));
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
					dataWrapper.setData(projectPojo);
				}
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

}
