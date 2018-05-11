package com.my.spring.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.my.spring.DAO.BuildingDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ItemDao;
import com.my.spring.DAO.PaperDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QuantityDao;
import com.my.spring.DAO.QuestionDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserProjectDao;
import com.my.spring.DAO.VideoDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Item;
import com.my.spring.model.Paper;
import com.my.spring.model.Project;
import com.my.spring.model.ProjectPojo;
import com.my.spring.model.Projectvs;
import com.my.spring.model.Quantity;
import com.my.spring.model.Question;
import com.my.spring.model.User;
import com.my.spring.model.UserProject;
import com.my.spring.parameters.Parameters;
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
    UserProjectDao userProjectDao;
    @Autowired
    FileService fileService;
    private String filePath1="files";
    private Integer fileType=2;
    @SuppressWarnings("unused")
	@Override
    public DataWrapper<ProjectPojo> addProject(Project project,String token,MultipartFile modelfile[],MultipartFile picfile[],HttpServletRequest request) {
        DataWrapper<ProjectPojo> dataWrapper = new DataWrapper<ProjectPojo>();
        DataWrapper<Project> dataWrappers = new DataWrapper<Project>();
        project.setWorkHour(10);
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
        	if(userInMemory.getUserType()==UserTypeEnum.Visitor.getType()){
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
					project.setCreateDate(new Date(System.currentTimeMillis()));
					if(projectDao.addProject(project))
					{
						/////添加关联关系表记录
						UserProject userProject = new UserProject();
						userProject.setProjectId(project.getId());
						userProject.setUserId(userInMemory.getId());
						if(!userProjectDao.addUserProject(userProject)){
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}
						////////////
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
        Project projects = new Project();
        if (userInMemory != null) {
				if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
					if(project!=null){
						projects=projectDao.getById(project.getId());
						String modelid="";
						String isIos="";
						if(modelFile.length>0){
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
							projects.setModelId(modelid);
							projects.setIsIos(isIos);
						}
						if(picFile.length>0){
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
							projects.setPicId(picid);
						}
						if(project.getBuildingUnit()!=null){
							projects.setBuildingUnit(project.getBuildingUnit());
						}
						if(project.getConstructionUnit()!=null){
							projects.setConstructionUnit(project.getConstructionUnit());
						}
						if(project.getDescription()!=null){
							projects.setDescription(project.getDescription());
						}
						if(project.getDesignUnit()!=null){
							projects.setDesignUnit(project.getDesignUnit());
						}
						if(project.getBuildingUnitUser()!=null){
							projects.setBuildingUnitUser(project.getBuildingUnitUser());
						}
						if(project.getPrice()!=null){
							projects.setPrice(project.getPrice());
						}
						if(project.getConstructionControlUser()!=null){
							projects.setConstructionControlUser(project.getConstructionControlUser());
						}
						if(project.getConstructionControlUnit()!=null){
							projects.setConstructionControlUnit(project.getConstructionControlUnit());
						}
						if(project.getDesignUnitUser()!=null){
							projects.setDesignUnitUser(project.getDesignUnitUser());
						}
						if(project.getConstructionUnitUser()!=null){
							projects.setConstructionUnitUser(project.getConstructionUnitUser());
						}
						if(project.getLeader()!=null){
							projects.setLeader(project.getLeader());
						}
						if(project.getModelPart()!=null){
							projects.setModelPart(project.getModelPart());
						}
						if(project.getName()!=null){
							projects.setName(project.getName());
						}
						if(project.getPhase()!=null){
							projects.setPlace(project.getPhase());
						}
						if(project.getStartDate()!=null){
							projects.setStartDate(project.getStartDate());
						}
						if(project.getState()!=null){
							projects.setState(project.getState());
						}
						if(project.getTeamList()!=null){
							projects.setTeamId(project.getTeamId());
							projects.setTeamList(project.getTeamList());
						}
						if(project.getVersion()!=null){
							projects.setVersion(project.getVersion());
						}
						if(project.getPlace()!=null){
							projects.setPlace(project.getPlace());
						}
						projects.setUpdateDate(new Date(System.currentTimeMillis()));
						if(!projectDao.updateProject(projects)) 
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
    public DataWrapper<List<ProjectPojo>> getProjectList(Integer pageIndex, Integer pageSize, Project project, String token,String content) {
    	DataWrapper<List<ProjectPojo>> dataWrappers=new DataWrapper<List<ProjectPojo>>();
    	DataWrapper<List<Project>> dataWrapper=new DataWrapper<List<Project>>();
    	DataWrapper<List<Projectvs>> dataWrappervs=new DataWrapper<List<Projectvs>>();
    	List<ProjectPojo> pojoproject=new ArrayList<ProjectPojo>();
    	User userInMemory =SessionManager.getSession(token);
    	Integer isios=-1;
    	isios=userInMemory.getSystemId();
    	if (userInMemory != null) {
    		if(userInMemory.getUserType()==UserTypeEnum.Admin.getType() || userInMemory.getUserType()==UserTypeEnum.User.getType()){
    			dataWrapper=projectDao.getProjectList(pageSize, pageIndex, project,content, isios);
    			if(dataWrapper.getData()!=null){
    				for(int i=0;i<dataWrapper.getData().size();i++){
        				ProjectPojo projectpojo=new ProjectPojo();
        				projectpojo.setId(dataWrapper.getData().get(i).getId());
        				projectpojo.setBuildingUnit(dataWrapper.getData().get(i).getBuildingUnit());
        				projectpojo.setConstructionUnit(dataWrapper.getData().get(i).getConstructionUnit());
        				projectpojo.setDescription(dataWrapper.getData().get(i).getDescription());
        				projectpojo.setDesignUnit(dataWrapper.getData().get(i).getDesignUnit());
        				projectpojo.setLeader(dataWrapper.getData().get(i).getLeader());
        				projectpojo.setBuildingUnitUser(dataWrapper.getData().get(i).getBuildingUnitUser());
        				projectpojo.setConstructionControlUser(dataWrapper.getData().get(i).getConstructionControlUser());
        				projectpojo.setConstructionControlUnit(dataWrapper.getData().get(i).getConstructionControlUnit());
        				projectpojo.setConstructionUnitUser(dataWrapper.getData().get(i).getConstructionUnitUser());
        				projectpojo.setDesignUnitUser(dataWrapper.getData().get(i).getDesignUnitUser());
        				projectpojo.setName(dataWrapper.getData().get(i).getName());
        				projectpojo.setNum(dataWrapper.getData().get(i).getNum());
        				projectpojo.setPhase(dataWrapper.getData().get(i).getPhase());
        				projectpojo.setPlace(dataWrapper.getData().get(i).getPlace());
        				projectpojo.setShortName(dataWrapper.getData().get(i).getShortName());
        				projectpojo.setStartDate(dataWrapper.getData().get(i).getStartDate());
        				projectpojo.setVersion(dataWrapper.getData().get(i).getVersion());
        				projectpojo.setState(dataWrapper.getData().get(i).getState());
        				projectpojo.setFinishedDate(dataWrapper.getData().get(i).getFinishedDate());
        				projectpojo.setModelPart(dataWrapper.getData().get(i).getModelPart().split(","));
        				String[] tests=null;
        				if(dataWrapper.getData().get(i).getModelPart()!=null){
        					if(!dataWrapper.getData().get(i).getModelPart().equals("")){
        						tests=dataWrapper.getData().get(i).getModelPart().split(",");
        					}
        				}
        				if(dataWrapper.getData().get(i).getModelId()!=null){
        					if(!dataWrapper.getData().get(i).getModelId().equals("")){
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
        				}
        				if(userInMemory.getUserType()!=3){
        					if(project.getTeamList()!=null){
        						String[] teamNames=project.getTeamList().split(",");
        						String[] teamIds=project.getTeamId().split(",");
        						projectpojo.setTeamList(teamNames);
        						projectpojo.setTeamId(teamIds);
        					}
        				}else{
        					if(userInMemory.getTeamId()!=null){
        						if(project.getTeamList()!=null){
        							String[] teamNames=project.getTeamList().split(",");
        							String[] teamIds=project.getTeamId().split(",");
        						}
        					}
        				}
        				if(dataWrapper.getData().get(i).getPicId()!=null){
        					if(!dataWrapper.getData().get(i).getPicId().equals("")){
        						Files filess=fileDao.getById(Long.valueOf(dataWrapper.getData().get(i).getPicId()));
            					if(filess!=null){
            						projectpojo.setPicUrl(filess.getUrl());
            						projectpojo.setPicName(filess.getName());
            					}
        					}
        				}
        				projectpojo.setCreateDate(Parameters.getSdf().format(dataWrapper.getData().get(i).getCreateDate()));
        				pojoproject.add(i, projectpojo);
        			}
    			}
    		}else{
    			dataWrappervs=projectDao.getProjectList(pageSize, pageIndex,project,userInMemory);
    			if(dataWrappervs.getData()!=null){
    				for(int i=0;i<dataWrappervs.getData().size();i++){
        				ProjectPojo projectpojo=new ProjectPojo();
        				projectpojo.setId(dataWrappervs.getData().get(i).getId());
        				projectpojo.setBuildingUnit(dataWrappervs.getData().get(i).getBuilding_unit());
        				projectpojo.setConstructionUnit(dataWrappervs.getData().get(i).getConstruction_unit());
        				projectpojo.setDescription(dataWrappervs.getData().get(i).getDescription());
        				projectpojo.setDesignUnit(dataWrappervs.getData().get(i).getDesign_unit());
        				projectpojo.setLeader(dataWrappervs.getData().get(i).getLeader());
        				projectpojo.setBuildingUnitUser(dataWrappervs.getData().get(i).getBuilding_unit_user());
        				projectpojo.setConstructionControlUser(dataWrappervs.getData().get(i).getConstruction_control_user());
        				projectpojo.setConstructionControlUnit(dataWrappervs.getData().get(i).getConstruction_control_unit());
        				projectpojo.setConstructionUnitUser(dataWrappervs.getData().get(i).getConstruction_unit_user());
        				projectpojo.setDesignUnitUser(dataWrappervs.getData().get(i).getDesign_unit_user());
        				projectpojo.setName(dataWrappervs.getData().get(i).getName());
        				projectpojo.setNum(dataWrappervs.getData().get(i).getNum());
        				projectpojo.setPhase(dataWrappervs.getData().get(i).getPhase());
        				projectpojo.setPlace(dataWrappervs.getData().get(i).getPlace());
        				projectpojo.setShortName(dataWrappervs.getData().get(i).getShort_name());
        				projectpojo.setStartDate(dataWrappervs.getData().get(i).getStart_date());
        				projectpojo.setVersion(dataWrappervs.getData().get(i).getVersion());
        				projectpojo.setState(dataWrappervs.getData().get(i).getState());
        				projectpojo.setFinishedDate(dataWrappervs.getData().get(i).getFinished_date());
        				projectpojo.setModelPart(dataWrappervs.getData().get(i).getModel_part().split(","));
        				String[] tests=null;
        				if(dataWrappervs.getData().get(i).getModel_part()!=null){
        					if(!dataWrappervs.getData().get(i).getModel_part().equals("")){
        						tests=dataWrappervs.getData().get(i).getModel_part().split(",");
        					}
        				}
        				if(dataWrappervs.getData().get(i).getModel_id()!=null){
        					if(!dataWrappervs.getData().get(i).getModel_id().equals("")){
        						String[] temp=dataWrappervs.getData().get(i).getModel_id().split(",");
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
        				}
        				if(userInMemory.getUserType()!=3){
        					if(project.getTeamList()!=null){
        						String[] teamNames=project.getTeamList().split(",");
        						String[] teamIds=project.getTeamId().split(",");
        						projectpojo.setTeamList(teamNames);
        						projectpojo.setTeamId(teamIds);
        					}
        				}else{
        					if(userInMemory.getTeamId()!=null){
        						if(project.getTeamList()!=null){
        							String[] teamNames=project.getTeamList().split(",");
        							String[] teamIds=project.getTeamId().split(",");
        						}
        					}
        				}
        				if(dataWrappervs.getData().get(i).getPic_id()!=null){
        					if(!dataWrappervs.getData().get(i).getPic_id().equals("")){
        						Files filess=fileDao.getById(Long.valueOf(dataWrappervs.getData().get(i).getPic_id()));
            					if(filess!=null){
            						projectpojo.setPicUrl(filess.getUrl());
            						projectpojo.setPicName(filess.getName());
            					}
        					}
        				}
        				projectpojo.setCreateDate(Parameters.getSdf().format(dataWrappervs.getData().get(i).getCreate_date()));
        				pojoproject.add(i, projectpojo);
        			}
    			}
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
							}
						}
					}
					projectPojo.setModelPart(project.getModelPart().split(","));
					projectPojo.setWorkHour(project.getWorkHour());
					projectPojo.setBuildingUnit(project.getBuildingUnit());
					projectPojo.setBuildingUnitUser(project.getBuildingUnitUser());
					projectPojo.setConstructionUnit(project.getConstructionUnit());
					projectPojo.setConstructionControlUser(project.getConstructionControlUser());
					projectPojo.setConstructionControlUnit(project.getConstructionControlUnit());
					projectPojo.setConstructionUnitUser(project.getConstructionUnitUser());
					projectPojo.setDesignUnitUser(project.getDesignUnitUser());
					projectPojo.setDescription(project.getDescription());
					projectPojo.setDesignUnit(project.getDesignUnit());
					projectPojo.setId(project.getId());
					projectPojo.setLeader(project.getLeader());
					projectPojo.setName(project.getName());
					projectPojo.setNum(project.getNum());
					projectPojo.setPrice(project.getPrice());
					projectPojo.setPhase(project.getPhase());
					projectPojo.setPlace(project.getPlace());
					projectPojo.setStartDate(project.getStartDate());
					projectPojo.setFinishedDate(project.getFinishedDate());
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

	@Override
	public DataWrapper<Void> updateWorkHour(Long projectId, String token, Integer workHour) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			Project project = projectDao.getById(projectId);
			if(project!=null){
				project.setWorkHour(workHour);
				if(!projectDao.updateProject(project)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<String>   getProjectHour(Long projectId, String token) {
		User userInMemory = SessionManager.getSession(token);
		DataWrapper<String> result =new  DataWrapper<String>();
        if (userInMemory != null) {
			if(projectId!=null){
				Project project=projectDao.getById(projectId);
			    result.setData(project.getWorkHour().toString());
			}
		} else {
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return result;
	}

}
