package com.my.spring.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.my.spring.DAO.CheckListTypesDao;
import com.my.spring.DAO.CommentDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ManageLogDao;
import com.my.spring.DAO.MeasuredProblemDao;
import com.my.spring.DAO.PaperOfMeasuredDao;
import com.my.spring.DAO.PaperPointInfoDao;
import com.my.spring.DAO.PointDataInputLogDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.RelationDao;
import com.my.spring.DAO.ReplyDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.jpush.PushExample;
import com.my.spring.model.CheckListTypes;
import com.my.spring.model.Comment;
import com.my.spring.model.Files;
import com.my.spring.model.ManageLog;
import com.my.spring.model.MeasuredProblem;
import com.my.spring.model.MeasuredProblemEditPojo;
import com.my.spring.model.MeasuredProblemPojo;
import com.my.spring.model.MeasuredUserInfo;
import com.my.spring.model.PaperOfMeasured;
import com.my.spring.model.PaperPointInfo;
import com.my.spring.model.PointDataInputLog;
import com.my.spring.model.Project;
import com.my.spring.model.Relation;
import com.my.spring.model.Reply;
import com.my.spring.model.User;
import com.my.spring.model.pojo.MeasuredCheckData;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.MeasuredProblemService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


@Service("measuredProblemService")
public class MeasuredProblemServiceImpl implements MeasuredProblemService,Runnable {
    @Autowired
    MeasuredProblemDao mpDao;
    @Autowired
    PointDataInputLogDao dataDao;
    @Autowired
    CheckListTypesDao checkDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    PaperPointInfoDao paperPointInfoDao;
    @Autowired
    PaperOfMeasuredDao mopDao;
    @Autowired
    ReplyDao replyDao;
    @Autowired
	FileService fileService;
    @Autowired
    RelationDao relationDao;
    @Autowired
    CommentDao commentDao;
	@Autowired
	FileDao fileDao;
	@Autowired
	ManageLogDao manageLogDao;
	private static String filePath = "/files/measuredProblem";
    @Override
    public DataWrapper<Void> addMeasuredProblem(MeasuredProblem building,String token,MultipartFile[] files,MultipartFile[] vois,HttpServletRequest request,String fDate) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(building!=null){
				if(!fDate.equals("") && fDate!=null){
					try {
						building.setFinishedDate(Parameters.getSdfs().parse(fDate));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(files!=null){
					String filesStr="";
					for(int i=0;i<files.length;i++){
						Files file=fileService.uploadFile(filePath+"/pictures", files[i], 3, request);
						if(i==(files.length-1)){
							filesStr=filesStr+file.getId();
						}else{
							filesStr=filesStr+file.getId()+",";
						}
					}
					building.setFiles(filesStr);;
				}
				if(vois!=null){
					String filesStr="";
					for(int i=0;i<vois.length;i++){
						Files file=fileService.uploadFile(filePath+"/voices", vois[i], 3, request);
						if(i==(vois.length-1)){
							filesStr=filesStr+file.getId();
						}else{
							filesStr=filesStr+file.getId()+",";
						}
					}
					building.setVoices(filesStr);;
				}
				building.setCreateDate(new Date());
				building.setCheckUser(userInMemory.getId());
				building.setCreateUser(userInMemory.getId());
				if(!mpDao.addMeasuredProblem(building)){
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				} 
				else{
					return dataWrapper;
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
    public DataWrapper<Void> deleteMeasuredProblem(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(id!=null){
				if(!mpDao.deleteMeasuredProblem(id)) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else
					return dataWrapper;
		        
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

	@Override
	public DataWrapper<List<MeasuredProblemPojo>> getMeasuredProblemByProjectId(Long id,Long projectId,String token,Integer status,String bfmIds,String checkTypeIds,Long siteId) {
		DataWrapper<List<MeasuredProblemPojo>> dataWrapper = new DataWrapper<List<MeasuredProblemPojo>>();
		List<MeasuredProblem> results = new ArrayList<MeasuredProblem>();
		List<MeasuredProblemPojo> datas = new ArrayList<MeasuredProblemPojo>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	List<Long> bids = new ArrayList<>();
        	List<Long> cids = new ArrayList<>();
        	if(bfmIds!=null){
        		if(!bfmIds.equals("")){
        			String[] ids = bfmIds.split(",");
        			for(int i=0;i<ids.length;i++){
        				bids.add(Long.valueOf(ids[i]));
        			}
        		}
        	}
        	if(checkTypeIds!=null){
        		if(!checkTypeIds.equals("")){
        			String[] ctids = checkTypeIds.split(",");
        			for(int i=0;i<ctids.length;i++){
        				cids.add(Long.valueOf(ctids[i]));
        			}
        		}
        	}
        	results=mpDao.getMeasuredProblemByProjectId(id,projectId,userInMemory,status,bids,cids,siteId);
        	if(!results.isEmpty()){
        		for(int i=0;i<results.size();i++){
        			MeasuredProblemPojo pojo = new MeasuredProblemPojo();
        			pojo.setId(results.get(i).getId());
        			pojo.setCheckSite(results.get(i).getCheckSite());
        			pojo.setDetail(results.get(i).getDetail());
        			pojo.setStatus(results.get(i).getStatus());
        			pojo.setScore(results.get(i).getScore());
        			pojo.setProcess(results.get(i).getProcess());
        			CheckListTypes clt = new CheckListTypes();
        			Project project = new Project();
            		project = projectDao.getById(results.get(i).getProjectId());
            		pojo.setProjectName(project.getName());
        			if(results.get(i).getCheckListId()!=null){
        				clt = checkDao.getById(results.get(i).getCheckListId());
        				if(clt!=null){
        					pojo.setCheckLists(clt.getCheckName());
        				}
        			}
        			if(results.get(i).getPointId()!=null){
        				PaperPointInfo pointInfo = new PaperPointInfo();
        				pointInfo = paperPointInfoDao.getById(results.get(i).getPointId());
        				if(pointInfo!=null){
        					pojo.setAbscissa(pointInfo.getAbscissa());
        					pojo.setOrdinate(pointInfo.getOrdinate());
        					PaperOfMeasured pom = new PaperOfMeasured();
        					pom=mopDao.getById(pointInfo.getPaperOfMeasuredId());
        					if(pom!=null){
        						pojo.setPaperUrl(fileService.getById(pom.getFileId()).getUrl());
        					}
        				}
        				pojo.setPointId(results.get(i).getPointId());
        				PointDataInputLog dataLogs = dataDao.getPointDataInputLogListByOptions(results.get(i).getPointId(),results.get(i).getCheckListId(),results.get(i).getInputUserId());
        				if(dataLogs!=null){
        					String logString=results.get(i).getCheckSite()+" 实测实量-设备安装-";
    						String checkNames= clt.getCheckName();
    						logString=logString+checkNames;
    						pojo.setTitle(logString);
        						/////////
    						String content = "";
    						User users = userDao.getById(dataLogs.getCreateUser());
    						if(users!=null){
    							content=content+users.getRealName()+"-"+users.getWorkName();
    						}
    						content=content+Parameters.getSdf().format(dataLogs.getCreateDate());
    						pojo.setContent(content);
    						/////
    						String content2 = checkNames;
    						content2=content2+":【"+dataLogs.getInputData()+"】";
    						pojo.setContentDetail(content2);
        					
        				}
        			}
        		    
        			if(results.get(i).getCheckDate()!=null){
        				String checkDates = Parameters.getSdfs().format(results.get(i).getCheckDate());
        				pojo.setCheckDate(checkDates);
        			}
        			if(results.get(i).getFinishedDate()!=null){
        				String finishedDates = Parameters.getSdfs().format(results.get(i).getFinishedDate());
        				pojo.setFinishedDate(finishedDates);
        			}
        			String createDate = Parameters.getSdfs().format(results.get(i).getCreateDate());
    				pojo.setCreateDate(createDate);
    				if(results.get(i).getCreateUser()!=null){
        				User createUser = userDao.getById(results.get(i).getCreateUser());
        				if(createUser!=null){
        					pojo.setCreateUser(createUser.getRealName());
        				}
        			}
    				if(id!=null){
        				List<MeasuredUserInfo> users = mpDao.getAboutUserIcons(id);
        				pojo.setAboutIcons(users);
        				if(results.get(i).getCheckUser()!=null){
            				User checkUser = userDao.getById(results.get(i).getCheckUser());
            				if(checkUser!=null){
            					pojo.setCheckUser(checkUser.getRealName());
            				}
            			}
            			if(results.get(i).getFiles()!=null){
            				if(!results.get(i).getFiles().equals("")){
            					List<String> files = new ArrayList<String>();
                				String[] filesstr=results.get(i).getFiles().split(",");
                				for(int j=0;j<filesstr.length;j++)
                				{
                					Files file1 = fileService.getById(Long.valueOf(filesstr[j]));
                					if(file1!=null){
                						files.add(file1.getUrl());
                					}
                				}
                				pojo.setFiles(files);
            				}
            				
            			}
            			if(results.get(i).getVoices()!=null){
            				if(!results.get(i).getVoices().equals("")){
            					List<String> files = new ArrayList<String>();
                				String[] filesstr=results.get(i).getVoices().split(",");
                				for(int j=0;j<filesstr.length;j++)
                				{
                					Files file1 = fileService.getById(Long.valueOf(filesstr[j]));
                					if(file1!=null){
                						files.add(file1.getUrl());
                					}
                				}
                				pojo.setVoices(files);
            				}
            			}
        			}
        			
        			if(results.get(i).getRectifyUser()!=null){
        				pojo.setRectifyUser(results.get(i).getRectifyUser());
        				if(!results.get(i).getRectifyUser().equals("")){
        					List<Long> userids = new ArrayList<Long>();
        					for(int m=0;m<results.get(i).getRectifyUser().split(",").length;m++){
        						userids.add(Long.valueOf(results.get(i).getRectifyUser().split(",")[m]));
        					}
        					List<User> users = userDao.getUserIconListByIds(userids);
        					List<String> userNames = new ArrayList<String>();
        					for(User useritem:users){
        						userNames.add(useritem.getRealName());
        					}
        					pojo.setRectifyUserNames(userNames);
        				}
        			}
        			
        			datas.add(pojo);
        		}
        	}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        dataWrapper.setData(datas);
		return dataWrapper;
	}
	@Override
	public DataWrapper<Void> qualityRectificationCheckAgain(String token, Long measuredId, Integer score,
			Integer state) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			MeasuredProblem qm = mpDao.getById(measuredId);
			if(qm!=null){
				if(user.getId().equals(qm.getCheckUser()) || user.getUserType().equals(UserTypeEnum.Admin.getType())){
					qm.setScore(score);
					Comment comment = new Comment();
					ManageLog manageLog = new ManageLog();
					manageLog.setAboutId(measuredId);
					manageLog.setActionDate(new Date());
					manageLog.setOperateUser(user.getId());
					manageLog.setManageType(2);
					manageLog.setActionType(1);
					if(state==1){
						qm.setStatus(3);//已完成
					}
					if(state==0){
						qm.setStatus(1);//进行时
					}
					if(mpDao.updateMeasuredProblem(qm)){
						manageLogDao.addManageLog(manageLog);
						comment.setCommentUser(user.getId());;
						comment.setAboutId(measuredId);
						comment.setReplyType(2);
						comment.setType(2);
						comment.setCreateDate(new Date());
						comment.setCommentContent("验收通过");
						comment.setCommentContent("验收不通过");
						commentDao.addComment(comment);
					}
				}else{
					result.setErrorCode(ErrorCodeEnum.AUTH_Error);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
	@Override
	public DataWrapper<Void> qualityRectificationCheck(String token, Long measuredId, Integer schedule, String content,
			MultipartFile[] pics, MultipartFile[] vois, HttpServletRequest request) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			List<Relation> qms = relationDao.getRelationListsByIds(2, measuredId, user.getId());
			if(!qms.isEmpty() || user.getUserType()==UserTypeEnum.Admin.getType()){
				Reply reply = new Reply();
				reply.setAboutId(measuredId);
				reply.setReplyType(2);
				reply.setCreateDate(new Date());
				reply.setReplyContent(content);
				reply.setReplyUser(user.getId());
				reply.setSchedule(schedule);
				if(pics!=null){
					String picsStr="";
					for(int i=0;i<pics.length;i++){
						Files file=fileService.uploadFile(filePath+"/pictures", pics[i], 3, request);
						if(i==(pics.length-1)){
							picsStr=picsStr+file.getId();
						}else{
							picsStr=picsStr+file.getId()+",";
						}
					}
					reply.setPictures(picsStr);
				}
				if(vois!=null){
					String voisStr="";
					for(int i=0;i<vois.length;i++){
						Files file=fileService.uploadFile(filePath+"/voices", vois[i], 3, request);
						if(i==(vois.length-1)){
							voisStr=voisStr+file.getId();
						}else{
							voisStr=voisStr+file.getId()+",";
						}
					}
					reply.setVoices(voisStr);
				}
				if(replyDao.addReply(reply)){
					Comment comment = new Comment();
					comment.setCommentUser(reply.getReplyUser());;
					comment.setAboutId(measuredId);
					comment.setReplyType(2);
					comment.setType(1);
					comment.setCreateDate(new Date());
					comment.setCommentContent("工作已完成"+schedule+"%");
					if(schedule==100){
						comment.setCommentContent("工作已完成"+schedule+"%,请尽快验收！");
						MeasuredProblem qm = mpDao.getById(measuredId);
						qm.setStatus(2);//待验收
						mpDao.updateMeasuredProblem(qm);
					}
					commentDao.addComment(comment);
				}
			}else{
				result.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		}
		return result;
	}
	@Override
	public DataWrapper<Void> updateMeasuredProblem(MeasuredProblem building, String token, MultipartFile[] files,
			MultipartFile[] vois, HttpServletRequest request, String fDate) {
		 DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
	        User userInMemory = SessionManager.getSession(token);
	        if (userInMemory != null) {
				if(building.getId()!=null){
					MeasuredProblem mps = mpDao.getById(building.getId());
					if(mps!=null){
						if(!fDate.equals("") && fDate!=null){
							try {
								mps.setFinishedDate(Parameters.getSdfs().parse(fDate));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(files!=null){
							String filesStr="";
							for(int i=0;i<files.length;i++){
								Files file=fileService.uploadFile(filePath+"/pictures", files[i], 3, request);
								if(i==(files.length-1)){
									filesStr=filesStr+file.getId();
								}else{
									filesStr=filesStr+file.getId()+",";
								}
							}
							mps.setFiles(filesStr);;
						}
						if(vois!=null){
							String filesStr="";
							for(int i=0;i<vois.length;i++){
								Files file=fileService.uploadFile(filePath+"/voices", vois[i], 3, request);
								if(i==(vois.length-1)){
									filesStr=filesStr+file.getId();
								}else{
									filesStr=filesStr+file.getId()+",";
								}
							}
							mps.setVoices(filesStr);;
						}
						mps.setDetail(building.getDetail());
						mps.setStatus(1);
						mps.setRectifyUser(building.getRectifyUser());
						if(!mpDao.updateMeasuredProblem(mps)){
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						} 
						else{
							/*添加相关人员记录表*/
							List<Relation> relationList = new ArrayList<Relation>();
							if(building.getRectifyUser()!=null){
								String[] rectifyUsers = building.getRectifyUser().split(",");
								for(int n=0;n<rectifyUsers.length;n++){
									Relation relation2 = new Relation();
									relation2.setAboutId(building.getId());
									relation2.setProjectId(building.getProjectId());
									relation2.setRelationType(2);
									relation2.setUserId(Long.valueOf(rectifyUsers[n]));
									relationList.add(relation2);
								}
								relationDao.addRelationList(relationList);
							}
							return dataWrapper;
						}
					}else{
						dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
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
	public DataWrapper<Void> addMeasuredProblemList(List<MeasuredProblem> mpList) {
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		if(!mpList.isEmpty()){
			if(!mpDao.addMeasuredProblemList(mpList)){
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			} 
			else{
				/*添加相关人员记录表*/
				List<Relation> relationList = new ArrayList<Relation>();
				for(int i=0;i<mpList.size();i++){
					Relation relation = new Relation();
					relation.setAboutId(mpList.get(i).getId());
					relation.setProjectId(mpList.get(i).getProjectId());
					relation.setRelationType(2);
					relation.setUserId(mpList.get(i).getCreateUser());
					relationList.add(relation);
				}
				relationDao.addRelationList(relationList);
				return dataWrapper;
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
		}
        return dataWrapper;
	}
	@Override
	public DataWrapper<MeasuredProblemPojo> getMeasuredProblemByPointId(Long pointId, String token) {
		// TODO Auto-generated method stub
		return null;
	}
	public void test(){
		new Thread(new Runnable() { @Override public void run() { System.out.println("线程同步测试！！！！！！！！！！！！！！！！！！！！！！！！"); } }).start();
	}
	@Override
	public void run() {
		System.out.println("线程同步测试！！！！！！！！！！！！！！！！！！！！！！！！");
	}
	@Override
	public DataWrapper<Void> updateMeasuredProblemList(String token, String editString) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			List<MeasuredProblemEditPojo> gets = JSONObject.parseArray(editString, MeasuredProblemEditPojo.class);
			if(!gets.isEmpty()){
				List<MeasuredProblem> getList = mpDao.getMeasuredProblemByIds(gets);
				if(!getList.isEmpty()){
					for(int i=0;i<getList.size();i++){
						try {
							if(gets.get(i).getFinishedDate()!=null){
								getList.get(i).setFinishedDate(Parameters.getSdfs().parse(gets.get(i).getFinishedDate()));
							}
							if(gets.get(i).getRectifyUserId()!=null){
								getList.get(i).setRectifyUser(gets.get(i).getRectifyUserId());
							}
							if(gets.get(i).getFinishedDate()!=null && getList.get(i).getRectifyUser()!=null){
								getList.get(i).setStatus(1);
							}else if(gets.get(i).getRectifyUserId()!=null && getList.get(i).getFinishedDate()!=null){
								getList.get(i).setStatus(1);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					if(!mpDao.updateMeasuredProblemList(getList)){
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}else{
						List<Relation> relationList = new ArrayList<Relation>();
						for(int i=0;i<gets.size();i++){
							if(gets.get(i).getRectifyUserId()!=null){
								String[] rectifyUsers = gets.get(i).getRectifyUserId().split(",");
								for(int j=0;j<rectifyUsers.length;j++){
									Relation relation2 = new Relation();
									relation2.setAboutId(getList.get(j).getId());
									relation2.setProjectId(getList.get(j).getProjectId());
									relation2.setRelationType(2);
									relation2.setUserId(Long.valueOf(rectifyUsers[j]));
									relationList.add(relation2);
								}
							}
						}
						relationDao.addRelationList(relationList);
					}
				}
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		
		
		return dataWrapper;
	}
	@Override
	public DataWrapper<Void> measuredProblemSend(String aboutIds, String token,Long projectId) {
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			if(aboutIds!=null){
				HashMap<String,String> hq = new HashMap<String,String>();
				Project po = projectDao.getById(projectId);
				hq.put("projectName",po.getName());
				hq.put("createDate", Parameters.getSdfs().format(new Date()));
				String content="实测实量爆点整改情况需要你督办！";
				////推送人包括自己
				///0、质量   1、安全   2、施工任务单  3、 预付单  4、留言
				PushExample.testSendPushWithCustomConfig_ios(aboutIds.split(","), content,0,hq);
				PushExample.testSendPushWithCustomConfig_android(aboutIds.split(","), content,0,hq);
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
	}
	@Override
	public DataWrapper<List<String>> measuredProblemAddUser(String aboutIds, String token, Long measuredProblemId,Long projectId) {
		// TODO Auto-generated method stub
		DataWrapper<List<String>> result = new DataWrapper<List<String>>();
		List<String> urls = new ArrayList<String>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(measuredProblemId!=null){
				if(aboutIds!=null){
					if(!aboutIds.equals("")){
						List<Relation> relations = new ArrayList<Relation>();
						List<Long> userIds = new ArrayList<Long>();
						for(int i=0;i<aboutIds.split(",").length;i++){
							userIds.add(Long.valueOf(aboutIds.split(",")[i]));
							Relation relation = new Relation();
							relation.setAboutId(measuredProblemId);
							relation.setRelationType(2);
							relation.setUserId(Long.valueOf(aboutIds.split(",")[i]));
							relation.setProjectId(projectId);
							relation.setState(0);
							relations.add(relation);
						}
						if(relationDao.addRelationList(relations)){
							List<User> userList = new ArrayList<User>();
							userList = userDao.getUserIconListByIds(userIds);
							if(!userList.isEmpty()){
								for(int n=0;n<userList.size();n++){
									if(userList.get(n).getUserIconUrl()!=null){
										if(!userList.get(n).getUserIconUrl().equals("")){
											urls.add(userList.get(n).getUserIconUrl());
										}else if(userList.get(n).getUserIcon()!=null){
											Files file = fileService.getById(userList.get(n).getUserIcon());
											if(file!=null){
												urls.add(file.getUrl());
											}
										}
									}else{
										if(userList.get(n).getUserIcon()!=null){
											Files file = fileService.getById(userList.get(n).getUserIcon());
											if(file!=null){
												urls.add(file.getUrl());
											}
										}
									}
								}
								result.setData(urls);
							}
						}
					}
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
	@Override
	public DataWrapper<Void> measuredProblemListCheckAgain(String token, String checkString) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			List<MeasuredCheckData> getDatas = JSONObject.parseArray(checkString, MeasuredCheckData.class);
			if(getDatas!=null){
				for(int i=0;i<getDatas.size();i++){
					MeasuredProblem qm = mpDao.getById(getDatas.get(i).getMeasuredId());
					if(qm!=null){
						if(user.getId().equals(qm.getCreateUser()) || user.getUserType().equals(UserTypeEnum.Admin.getType())){
							qm.setScore(getDatas.get(i).getScore());
							Comment comment = new Comment();
							ManageLog manageLog = new ManageLog();
							manageLog.setAboutId(getDatas.get(i).getMeasuredId());
							manageLog.setActionDate(new Date());
							manageLog.setOperateUser(user.getId());
							manageLog.setManageType(2);
							manageLog.setActionType(1);
							if(getDatas.get(i).getState()==1){
								qm.setStatus(3);//已完成
							}
							if(getDatas.get(i).getState()==0){
								qm.setStatus(1);//进行时
							}
							if(mpDao.updateMeasuredProblem(qm)){
								manageLogDao.addManageLog(manageLog);
								comment.setCommentUser(user.getId());;
								comment.setAboutId(getDatas.get(i).getMeasuredId());
								comment.setReplyType(2);
								comment.setType(2);
								comment.setCreateDate(new Date());
								comment.setCommentContent("验收通过");
								comment.setCommentContent("验收不通过");
								commentDao.addComment(comment);
							}
						}else{
							result.setErrorCode(ErrorCodeEnum.AUTH_Error);
						}
					}else{
						result.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
					}
				}
			}
			
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}
}
