package com.my.spring.serviceImpl;

import com.my.spring.DAO.ConstructionTaskDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.NoticeDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserLogDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.jpush.PushExample;
import com.my.spring.model.AdvancedOrderCopy;
import com.my.spring.model.AdvancedOrderPojo;
import com.my.spring.model.ConstructionTask;
import com.my.spring.model.ConstructionTaskCopy;
import com.my.spring.model.ConstructionTaskPojo;
import com.my.spring.model.Files;
import com.my.spring.model.Notice;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.parameters.Parameters;
import com.my.spring.parameters.ProjectDatas;
import com.my.spring.service.ConstructionTaskService;
import com.my.spring.service.FileService;
import com.my.spring.service.UserLogService;
import com.my.spring.service.UserService;
import com.my.spring.utils.DataExportWordTest;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service("constructionTaskService")
public class ConstructionTaskServiceImpl implements ConstructionTaskService {
    @Autowired
    ConstructionTaskDao constructionTaskDao;
    @Autowired
    NoticeDao noticeDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    UserLogDao userLogDao;
    @Autowired
    FileService fileService;
    @Autowired
    UserLogService userLogService;
    
    @Autowired
    UserService userService;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    @Override
    public DataWrapper<Void> addConstructionTask(ConstructionTask constructionTask,String token,MultipartFile[] files,HttpServletRequest request) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        HashMap<String,String> hq = new HashMap<String,String>();
        String filesUrlList="";
        if (userInMemory != null) {
			if(constructionTask!=null){
				if(files!=null && files.length>0){
					for(int i=0;i<files.length;i++){
						Files filess=fileService.uploadFile("constructionFiles/"+constructionTask.getProjectId(), files[i], 5, request);
						if(filess!=null){
							hq.put("imagUrl", filess.getUrl());
							if(filesUrlList.equals("")){
								filesUrlList=filess.getId().toString();
							}else{
								filesUrlList+=","+filess.getId();
							}
							
						}
					}
					
				}
				if(!filesUrlList.equals("")){
					constructionTask.setFileIdList(filesUrlList);
				}
				constructionTask.setUserId(userInMemory.getId());
				constructionTask.setCreateDate(new Date(System.currentTimeMillis()));
				constructionTask.setTaskFlag(0);/////默认是未完成
				constructionTask.setNextApprovalPeopleType("班组组长");
				constructionTask.setCreateUserName(userInMemory.getRealName());
				constructionTask.setNextReceivePeopleId(userDao.getById(constructionTask.getReceiveUserId()).getRealName());
				if(!constructionTaskDao.addConstructionTask(constructionTask)) 
				{
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}else{
					if(userInMemory.getUserIconUrl()!=null){
						hq.put("userIconUrl", userInMemory.getUserIconUrl());
					}else{
						if(userInMemory.getUserIcon()!=null){
							Files fileput = fileService.getById(userInMemory.getUserIcon());
							hq.put("userIconUrl", fileput.getUrl());
						}
					}
					
					if(constructionTask.getReceiveUserId()!=null){
						Notice notice = new Notice();
						notice.setCreateDate(new Date());
						notice.setNoticeType(2);
						notice.setUserId(constructionTask.getReceiveUserId());
						notice.setReadState(0);
						notice.setAboutId(constructionTask.getId());
						noticeDao.addNotice(notice);
					}
					hq.put("createDate", Parameters.getSdfs().format(new Date()));
					hq.put("createUserName", userInMemory.getRealName());
					hq.put("aboutId", constructionTask.getId().toString());
					hq.put("content", constructionTask.getDetailContent());
					hq.put("projectName","来自  "+ constructionTask.getCompanyName());
					hq.put("title", "提交了一个施工任务单需要您审批");
					List<User> userList = new ArrayList<User>();
					int adminFlag=0;
					if(userInMemory.getUserType()==0 || userInMemory.getUserType()==1 || userInMemory.getUserType()==2){
	        			adminFlag=-1;
	        		}
					userList=userDao.findGetPushUsers(constructionTask.getNextReceivePeopleId(),adminFlag).getData();
					String content=userInMemory.getRealName()+"提交了一个施工任务单需要您审批";
					 String[] userids=new String[userList.size()];
					for(int b =0;b<userList.size();b++){
						userids[b]=userList.get(b).getId().toString();
					}
					PushExample.testSendPushWithCustomConfig_ios(userids, content,2,hq);
					PushExample.testSendPushWithCustomConfig_android(userids, content,2,hq);
				}
		///////////////////////////
				
				
					

				///////////////////////////////
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteConstructionTask(String id,String token ) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType() || userInMemory.getUserType()==1){
				if(id!=null){
					ConstructionTask constructionTask = new ConstructionTask();
					if(id!=null){
						String[] ids=id.split(",");
						for(int s=0;s<ids.length;s++){
							constructionTask=constructionTaskDao.getById(Long.valueOf(ids[s]));
							if(constructionTask!=null){
								if(constructionTask!=null){
									String fileIdList=constructionTask.getFileIdList();
									if(fileIdList!=null){
										for(int i=0;i<fileIdList.split(",").length;i++){
											fileDao.deleteFiles(Long.valueOf(fileIdList.split(",")[i]));
										}
									}
								}
								if(!constructionTaskDao.deleteConstructionTask(Long.valueOf(ids[s]))){
									dataWrapper.setErrorCode(ErrorCodeEnum.Error);
								}
							}
						}
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
    public DataWrapper<List<ConstructionTaskPojo>> getConstructionTaskList(String token , Integer pageIndex, Integer pageSize, ConstructionTask ConstructionTask,Integer state) {
    	DataWrapper<List<ConstructionTaskPojo>> dataWrappers = new DataWrapper<List<ConstructionTaskPojo>>();
    	DataWrapper<List<ConstructionTask>> dataWrapper = new DataWrapper<List<ConstructionTask>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	if(userInMemory.getSystemId()==0 || userInMemory.getSystemId()==1){
    			UserLog userLog = new UserLog();
    			userLog.setProjectPart(ProjectDatas.ConstructionTask_area.getCode());
    			userLog.setActionDate(new Date());
    			userLog.setUserId(userInMemory.getId());
    			userLog.setSystemType(userInMemory.getSystemId());
    			//userLog.setVersion("3.0");
    			if(ConstructionTask.getProjectId()!=null){
    				userLog.setProjectId(ConstructionTask.getProjectId());
    			}
    			if(ConstructionTask.getId()!=null){
    				userLog.setFileId(ConstructionTask.getId());
    			}
    			userLogDao.addUserLog(userLog);
    		}
        	String NextReceivePeopleId=null;
    		if(userInMemory.getUserType()!=3){
    			ConstructionTask.setUserProjectIdList("-1");
    		}
    		if(ConstructionTask.getApprovalPeopleName()==null && userInMemory.getUserType()==3){
    			ConstructionTask.setApprovalPeopleName(userInMemory.getRealName());
    		}
    		if(ConstructionTask.getNextReceivePeopleId()==null && userInMemory.getUserType()==3){
    			NextReceivePeopleId=userInMemory.getRealName(); 
    		}
    		if(ConstructionTask.getCreateUserName()==null && userInMemory.getUserType()==3){
    			ConstructionTask.setCreateUserName(userInMemory.getRealName());
    		}
			dataWrapper=constructionTaskDao.getConstructionTasksList(pageIndex,pageSize,ConstructionTask,state,userInMemory.getRealName(),NextReceivePeopleId);
			if(dataWrapper.getData()!=null && dataWrapper.getData().size()>0){
				if(ConstructionTask.getId()!=null){
					Notice notice = noticeDao.getByAdoutIdAndUserId(userInMemory.getId(), ConstructionTask.getId(), 2);
					if(notice!=null){
						notice.setUpdateDate(new Date());
						notice.setReadState(1);
						noticeDao.updateNotice(notice);
					}
				}
				List<ConstructionTaskPojo> constructionTaskPojoList = new ArrayList<ConstructionTaskPojo>();
				for(int i=0;i<dataWrapper.getData().size();i++){
					ConstructionTaskPojo constructionTaskPojo =new ConstructionTaskPojo();
					constructionTaskPojo.setCreateDate(sdf.format(dataWrapper.getData().get(i).getCreateDate()));
					constructionTaskPojo.setDetailContent(dataWrapper.getData().get(i).getDetailContent());
					constructionTaskPojo.setFinishedDate(dataWrapper.getData().get(i).getFinishedDate());
					constructionTaskPojo.setId(dataWrapper.getData().get(i).getId());
					constructionTaskPojo.setRewards(dataWrapper.getData().get(i).getRewards());
					constructionTaskPojo.setTaskContent(dataWrapper.getData().get(i).getTaskContent());
					constructionTaskPojo.setTeamName(dataWrapper.getData().get(i).getTeamName());
					constructionTaskPojo.setCompanyName(dataWrapper.getData().get(i).getCompanyName());
					constructionTaskPojo.setNextReceivePeopleName(dataWrapper.getData().get(i).getNextReceivePeopleId());
					constructionTaskPojo.setTaskFlag(dataWrapper.getData().get(i).getTaskFlag());
					constructionTaskPojo.setNextApprovalPeopleType(dataWrapper.getData().get(i).getNextApprovalPeopleType());
					if(dataWrapper.getData().get(i).getFileIdList()!=null){
						String[] filesUrl=dataWrapper.getData().get(i).getFileIdList().split(",");
						String[] filesUrlList = new String[filesUrl.length];
						for(int j=0;j<filesUrl.length;j++){
							filesUrlList[j]=fileDao.getById(Long.valueOf(filesUrl[j])).getUrl();
						}
						constructionTaskPojo.setFileUrlList(filesUrlList);
					}
					if(dataWrapper.getData().get(i).getWorkPeopleNameList()!=null){
						String[] nameList = dataWrapper.getData().get(i).getWorkPeopleNameList().split(",");
						String workPeopleName="";
						for(int k=0;k<nameList.length;k++){
							String names=userDao.getById(Long.valueOf(nameList[k])).getRealName();
							if(k==0){
								workPeopleName=workPeopleName+names;
							}else{
								workPeopleName=workPeopleName+"、"+names;
							}
							
						}
						constructionTaskPojo.setWorkPeopleNameList(workPeopleName);
					}
					if(dataWrapper.getData().get(i).getUserId()!=null){
						User user =userDao.getById(dataWrapper.getData().get(i).getUserId());
						if(user.getUserIconUrl()==null){
							Files file=fileService.getById(user.getUserIcon());
							constructionTaskPojo.setCreateUserIcon(file.getUrl());
						}else{
							constructionTaskPojo.setCreateUserIcon(user.getUserIconUrl());
						}
						constructionTaskPojo.setCreateUserName(user.getRealName());
					}
					if(dataWrapper.getData().get(i).getReceiveUserId()!=null){
						constructionTaskPojo.setReceiveUserName(userDao.getById(dataWrapper.getData().get(i).getReceiveUserId()).getRealName());
					}
					
					try {
						long day=0;
						Date endDate=sdf.parse(sdf.format(new Date(System.currentTimeMillis())));
						Date beginDate=sdf.parse(sdf.format(dataWrapper.getData().get(i).getCreateDate()));
						day=(endDate.getTime()-beginDate.getTime())/(60*60*1000); 
						constructionTaskPojo.setLastDate(String.valueOf(day));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(dataWrapper.getData().get(i).getFileIdList()!=null){
						String[] fileIdList = dataWrapper.getData().get(i).getFileIdList().split(",");
						String[] fileUrlList = new String[fileIdList.length];
						for(int j=0;j<fileIdList.length;j++){
							fileUrlList[j]=fileDao.getById(Long.valueOf(fileIdList[j])).getUrl();
						}
						constructionTaskPojo.setFileUrlList(fileUrlList);
					}
					if(ConstructionTask.getId()!=null){
						if(dataWrapper.getData().get(i).getApprovalPeopleTypeList()!=null){
							String [] approvalPeopleTypeList = dataWrapper.getData().get(i).getApprovalPeopleTypeList().split("&");
							constructionTaskPojo.setApprovalPeopleTypeList(approvalPeopleTypeList);
						}
						if(dataWrapper.getData().get(i).getApprovalPeopleNoteList()!=null){
							String [] approvalPeopleNoteList = dataWrapper.getData().get(i).getApprovalPeopleNoteList().split("&");
							constructionTaskPojo.setApprovalPeopleNoteList(approvalPeopleNoteList);
						}
						if(dataWrapper.getData().get(i).getApprovalPeopleIdeaList()!=null){
							String [] approvalPeopleIdeaList = dataWrapper.getData().get(i).getApprovalPeopleIdeaList().split("&");
							constructionTaskPojo.setApprovalPeopleIdeaList(approvalPeopleIdeaList);
						}
						if(dataWrapper.getData().get(i).getApprovalDateList()!=null){
							String [] approvalDateList = dataWrapper.getData().get(i).getApprovalDateList().split("&");
							constructionTaskPojo.setApprovalDateList(approvalDateList);
						}
						if(dataWrapper.getData().get(i).getApprovalPeopleName()!=null){
							String [] approvalPeopleNameList = dataWrapper.getData().get(i).getApprovalPeopleName().split("&");
							constructionTaskPojo.setApprovalPeopleNameList(approvalPeopleNameList);
						}
					}
					if(dataWrapper.getData().get(i).getApprovalPeopleName()!=null){
						String [] approvalPeopleNameList = dataWrapper.getData().get(i).getApprovalPeopleName().split("&");
						constructionTaskPojo.setCurrentUserName(approvalPeopleNameList[approvalPeopleNameList.length-1]);
					}
					
					if(constructionTaskPojo!=null){
						constructionTaskPojoList.add(constructionTaskPojo);
					}
				}
				if(constructionTaskPojoList!=null && constructionTaskPojoList.size()>0){
					dataWrappers.setData(constructionTaskPojoList);
					dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
					dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
					dataWrappers.setTotalPage(dataWrapper.getTotalPage());
					dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
				}else{
					dataWrappers.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        if(dataWrappers.getCallStatus()==CallStatusEnum.SUCCEED && dataWrappers.getData()==null){
        	List<ConstructionTaskPojo> pas= new ArrayList<ConstructionTaskPojo>();
        	dataWrappers.setData(pas);
        }
        return dataWrappers;
    }

	@Override
	public DataWrapper<List<ConstructionTask>> getConstructionTaskListByUserId(Long userId,String token) {
		DataWrapper<List<ConstructionTask>> dataWrapper = new DataWrapper<List<ConstructionTask>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userId!=null){
				if(constructionTaskDao.getConstructionTasksListByUserId(userId)==null) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else
					return constructionTaskDao.getConstructionTasksListByUserId(userId);
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
	}

	@Override
	public DataWrapper<Void> updateConstructionTask(ConstructionTask constructionTask, String token) {
		/////需要更新的施工任务单的任务状态默认流程都是没有走完的，走完的不能更新
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		ConstructionTask ct = new ConstructionTask();
		String currentUserName = userInMemory.getRealName();
		if(constructionTask.getId()!=null){
			ct = constructionTaskDao.getById(constructionTask.getId());
			if(ct.getTaskFlag()==1){
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}else{
			////不同意的任务单返回给创建人，重新填写
				String approvalPeopleName = ct.getNextReceivePeopleId();
				if(approvalPeopleName.equals(currentUserName) || userInMemory.getUserType()==0){
					if(ct.getNextReceivePeopleId().equals("填写人")){
						if(constructionTask.getCompanyName()!=null){
							ct.setCompanyName(constructionTask.getCompanyName());
						}
						if(constructionTask.getDetailContent()!=null){
							ct.setDetailContent(constructionTask.getDetailContent());
						}
						if(constructionTask.getFinishedDate()!=null){
							
							ct.setFinishedDate(constructionTask.getFinishedDate());
						}
						if(constructionTask.getRewards()!=null){
							ct.setRewards(constructionTask.getRewards());
						}
						if(constructionTask.getTaskContent()!=null){
							ct.setTaskContent(constructionTask.getTaskContent());
						}
						if(constructionTask.getTeamName()!=null){
							ct.setTeamName(constructionTask.getTeamName());
						}
						if(constructionTask.getReceiveUserId()!=null){
							ct.setReceiveUserId(constructionTask.getUserId());
						}
						if(constructionTask.getWorkPeopleNameList()!=null){
							ct.setWorkPeopleNameList(constructionTask.getWorkPeopleNameList());
						}
					}
					///审批人姓名
					if(ct.getApprovalPeopleName()!=null){
						ct.setApprovalPeopleName(ct.getApprovalPeopleName()+"&"+currentUserName);
					}else{
						ct.setApprovalPeopleName(currentUserName);
					}
					if(constructionTask.getApprovalPeopleIdeaList()!=null){
						if(constructionTask.getApprovalPeopleIdeaList().equals("不同意")){
							ct.setNextReceivePeopleId(ct.getCreateUserName());
						}else{
							ct.setNextReceivePeopleId(constructionTask.getNextReceivePeopleId());
						}
					}
					
					
					////审批时间
					String updateDateList=ct.getApprovalDateList();
					if(updateDateList!=null && !updateDateList.equals("")){
						ct.setApprovalDateList(updateDateList+"&"+sdf.format(new Date(System.currentTimeMillis())));
					}else{
						ct.setApprovalDateList(sdf.format(new Date(System.currentTimeMillis())));
					}
					
					////审批意见
					
					String approvalPeopleIdeaLists=ct.getApprovalPeopleIdeaList();
					if(approvalPeopleIdeaLists!=null && !approvalPeopleIdeaLists.equals("")){
						ct.setApprovalPeopleIdeaList(approvalPeopleIdeaLists+"&"+constructionTask.getApprovalPeopleIdeaList());
					}else{
						ct.setApprovalPeopleIdeaList(constructionTask.getApprovalPeopleIdeaList());
					}
					
					////审批批注
					String approvalPeopleNoteList=ct.getApprovalPeopleNoteList();
					if(approvalPeopleNoteList!=null && !approvalPeopleNoteList.equals("")){
						ct.setApprovalPeopleNoteList(approvalPeopleNoteList+"&"+constructionTask.getApprovalPeopleNoteList());
					}else{
						ct.setApprovalPeopleNoteList(constructionTask.getApprovalPeopleNoteList());
					}
					
					/////审批岗位
					if(ct.getApprovalPeopleTypeList()!=null && !ct.getApprovalPeopleTypeList().equals("")){
						ct.setApprovalPeopleTypeList(ct.getApprovalPeopleTypeList()+"&"+ct.getNextApprovalPeopleType());
					}else{
						ct.setApprovalPeopleTypeList(ct.getNextApprovalPeopleType());
					}
					
					if(ct.getNextApprovalPeopleType().equals("经理") && constructionTask.getApprovalPeopleIdeaList().equals("同意")){
						ct.setTaskFlag(1);
					}else{
						ct.setNextApprovalPeopleType(constructionTask.getNextApprovalPeopleType());
					}
					if(!constructionTaskDao.updateConstructionTask(ct)){
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
				}else{
					dataWrapper.setCallStatus(CallStatusEnum.FAILED);
					dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
				}
			}
			
		}else{
			dataWrapper.setCallStatus(CallStatusEnum.FAILED);
			dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed); 
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<ConstructionTaskPojo> getConstructionTaskById(Long id, String token, String weixin) {
		DataWrapper<ConstructionTaskPojo> dataWrapper = new DataWrapper<ConstructionTaskPojo>();
		ConstructionTask constructionTask = new ConstructionTask();
		if(id!=null){
			if(weixin.equals("weixin")){
				constructionTask=constructionTaskDao.getById(id);
				if(constructionTask!=null){
					ConstructionTaskPojo constructionTaskPojo =new ConstructionTaskPojo();
					constructionTaskPojo.setCreateDate(sdf.format(constructionTask.getCreateDate()));
					constructionTaskPojo.setDetailContent(constructionTask.getDetailContent());
					constructionTaskPojo.setFinishedDate(constructionTask.getFinishedDate());
					constructionTaskPojo.setId(constructionTask.getId());
					constructionTaskPojo.setRewards(constructionTask.getRewards());
					constructionTaskPojo.setTaskContent(constructionTask.getTaskContent());
					constructionTaskPojo.setTeamName(constructionTask.getTeamName());
					constructionTaskPojo.setCompanyName(constructionTask.getCompanyName());
					constructionTaskPojo.setNextReceivePeopleName(constructionTask.getNextReceivePeopleId());
					constructionTaskPojo.setTaskFlag(constructionTask.getTaskFlag());
					constructionTaskPojo.setNextApprovalPeopleType(constructionTask.getNextApprovalPeopleType());
					if(constructionTask.getFileIdList()!=null){
						String[] filesUrl=constructionTask.getFileIdList().split(",");
						String[] filesUrlList = new String[filesUrl.length];
						for(int j=0;j<filesUrl.length;j++){
							filesUrlList[j]=fileDao.getById(Long.valueOf(filesUrl[j])).getUrl();
						}
						constructionTaskPojo.setFileUrlList(filesUrlList);
					}
					String[] nameList = constructionTask.getWorkPeopleNameList().split(",");
					String workPeopleName="";
					for(int k=0;k<nameList.length;k++){
						String names=userDao.getById(Long.valueOf(nameList[k])).getRealName();
						if(k==0){
							workPeopleName=workPeopleName+names;
						}else{
							workPeopleName=workPeopleName+"、"+names;
						}
						
					}
					constructionTaskPojo.setWorkPeopleNameList(workPeopleName);
					if(constructionTask.getUserId()!=null){
						constructionTaskPojo.setCreateUserName(userDao.getById(constructionTask.getUserId()).getRealName());
					}
					if(constructionTask.getReceiveUserId()!=null){
						constructionTaskPojo.setReceiveUserName(userDao.getById(constructionTask.getReceiveUserId()).getRealName());
					}
					
					try {
						long day=0;
						Date endDate=sdf.parse(sdf.format(new Date(System.currentTimeMillis())));
						Date beginDate=sdf.parse(sdf.format(constructionTask.getCreateDate()));
						day=(endDate.getTime()-beginDate.getTime())/(60*60*1000); 
						constructionTaskPojo.setLastDate(String.valueOf(day));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(constructionTask.getFileIdList()!=null){
						String[] fileIdList = constructionTask.getFileIdList().split(",");
						String[] fileUrlList = new String[fileIdList.length];
						for(int j=0;j<fileIdList.length;j++){
							fileUrlList[j]=fileDao.getById(Long.valueOf(fileIdList[j])).getUrl();
						}
						constructionTaskPojo.setFileUrlList(fileUrlList);
					}
					if(constructionTask.getApprovalPeopleTypeList()!=null){
						String [] approvalPeopleTypeList = constructionTask.getApprovalPeopleTypeList().split("&");
						constructionTaskPojo.setApprovalPeopleTypeList(approvalPeopleTypeList);
					}
					if(constructionTask.getApprovalPeopleNoteList()!=null){
						String [] approvalPeopleNoteList = constructionTask.getApprovalPeopleNoteList().split("&");
						constructionTaskPojo.setApprovalPeopleNoteList(approvalPeopleNoteList);
					}
					if(constructionTask.getApprovalPeopleIdeaList()!=null){
						String [] approvalPeopleIdeaList = constructionTask.getApprovalPeopleIdeaList().split("&");
						constructionTaskPojo.setApprovalPeopleIdeaList(approvalPeopleIdeaList);
					}
					if(constructionTask.getApprovalDateList()!=null){
						String [] approvalDateList = constructionTask.getApprovalDateList().split("&");
						constructionTaskPojo.setApprovalDateList(approvalDateList);
					}
					if(constructionTask.getApprovalPeopleName()!=null){
						String [] approvalPeopleNameList = constructionTask.getApprovalPeopleName().split("&");
						constructionTaskPojo.setCurrentUserName(approvalPeopleNameList[approvalPeopleNameList.length-1]);
						constructionTaskPojo.setApprovalPeopleNameList(approvalPeopleNameList);
					}
					
					if(constructionTaskPojo!=null){
						dataWrapper.setData(constructionTaskPojo);
					}
				}
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
		}
        
        return dataWrapper;
	}

	@Override
	public DataWrapper<String> exportConstructionTaskById(Long id, String token) {
		// TODO Auto-generated method stub
		DataWrapper<String> dataWrapper = new DataWrapper<String>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(id!=null){
				ConstructionTask constructionTask = new ConstructionTask();
				constructionTask = constructionTaskDao.getById(id);
				if(constructionTask!=null){
					ConstructionTaskPojo constructionTaskPojo =new ConstructionTaskPojo();
					constructionTaskPojo.setCreateDate(sdf.format(constructionTask.getCreateDate()));
					constructionTaskPojo.setDetailContent(constructionTask.getDetailContent());
					constructionTaskPojo.setFinishedDate(constructionTask.getFinishedDate());
					constructionTaskPojo.setId(constructionTask.getId());
					constructionTaskPojo.setRewards(constructionTask.getRewards());
					constructionTaskPojo.setTaskContent(constructionTask.getTaskContent());
					constructionTaskPojo.setTeamName(constructionTask.getTeamName());
					constructionTaskPojo.setCompanyName(constructionTask.getCompanyName());
					constructionTaskPojo.setNextReceivePeopleName(constructionTask.getNextReceivePeopleId());
					constructionTaskPojo.setTaskFlag(constructionTask.getTaskFlag());
					constructionTaskPojo.setNextApprovalPeopleType(constructionTask.getNextApprovalPeopleType());
					if(constructionTask.getWorkPeopleNameList()!=null){
						String[] nameList = constructionTask.getWorkPeopleNameList().split(",");
						String workPeopleName="";
						for(int k=0;k<nameList.length;k++){
							String names=userDao.getById(Long.valueOf(nameList[k])).getRealName();
							if(k==0){
								workPeopleName=workPeopleName+names;
							}else{
								workPeopleName=workPeopleName+"、"+names;
							}
							
						}
						constructionTaskPojo.setWorkPeopleNameList(workPeopleName);
					}
					if(constructionTask.getUserId()!=null){
						constructionTaskPojo.setCreateUserName(userDao.getById(constructionTask.getUserId()).getRealName());
					}
					if(constructionTask.getReceiveUserId()!=null){
						constructionTaskPojo.setReceiveUserName(userDao.getById(constructionTask.getReceiveUserId()).getRealName());
					}
					
					if(constructionTask.getApprovalPeopleTypeList()!=null){
						String [] approvalPeopleTypeList = constructionTask.getApprovalPeopleTypeList().split("&");
						constructionTaskPojo.setApprovalPeopleTypeList(approvalPeopleTypeList);
					}
					if(constructionTask.getApprovalPeopleNoteList()!=null){
						String [] approvalPeopleNoteList = constructionTask.getApprovalPeopleNoteList().split("&");
						constructionTaskPojo.setApprovalPeopleNoteList(approvalPeopleNoteList);
					}
					if(constructionTask.getApprovalPeopleIdeaList()!=null){
						String [] approvalPeopleIdeaList = constructionTask.getApprovalPeopleIdeaList().split("&");
						constructionTaskPojo.setApprovalPeopleIdeaList(approvalPeopleIdeaList);
					}
					if(constructionTask.getApprovalDateList()!=null){
						String [] approvalDateList = constructionTask.getApprovalDateList().split("&");
						constructionTaskPojo.setApprovalDateList(approvalDateList);
					}
					if(constructionTask.getApprovalPeopleName()!=null){
						String [] approvalPeopleNameList = constructionTask.getApprovalPeopleName().split("&");
						constructionTaskPojo.setCurrentUserName(approvalPeopleNameList[approvalPeopleNameList.length-1]);
						constructionTaskPojo.setApprovalPeopleNameList(approvalPeopleNameList);
					}
					String str=null;
					DataExportWordTest de = new DataExportWordTest();
					str = de.exportConstructionToWord(constructionTaskPojo);
					dataWrapper.setData(str);
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		}else{
			dataWrapper.setCallStatus(CallStatusEnum.FAILED);
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<ConstructionTaskCopy>> getConstructionTaskListNotRead(String token, Integer pageSize,
			Integer pageIndex) {
		DataWrapper<List<ConstructionTaskCopy>> resultList = new DataWrapper<List<ConstructionTaskCopy>>();
		List<ConstructionTaskCopy> result = new ArrayList<ConstructionTaskCopy>();
		User user = SessionManager.getSession(token);
		if(user!=null){
			result = constructionTaskDao.getAdvancedOrdersListNotRead(user.getId(),pageSize,pageIndex);
			if(result.size()>=0 && result!=null){
				resultList.setTotalNumber(result.size());
			}else{
				resultList.setTotalNumber(0);
			}
			
			resultList.setData(result);
		}else{
			resultList.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return resultList;
	}


	

	
}
