package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.MessageDao;
import com.my.spring.DAO.MessageFileDao;
import com.my.spring.DAO.NoticeDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QualityQuestionDao;
import com.my.spring.DAO.QuestionFileDao;
import com.my.spring.DAO.RoleDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserLogDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.jpush.PushExample;
import com.my.spring.jpush.PushExamples;
import com.my.spring.model.Files;
import com.my.spring.model.Message;
import com.my.spring.model.MessageFile;
import com.my.spring.model.Notice;
import com.my.spring.model.PageInfo;
import com.my.spring.model.Project;
import com.my.spring.model.QualityFinePojo;
import com.my.spring.model.QualityQuestion;
import com.my.spring.model.QuestionFile;
import com.my.spring.model.Role;
import com.my.spring.model.QualityQuestionPojo;
import com.my.spring.model.QuestionCopy;
import com.my.spring.model.User;
import com.my.spring.model.UserId;
import com.my.spring.model.UserLog;
import com.my.spring.parameters.Parameters;
import com.my.spring.parameters.ProjectDatas;
import com.my.spring.service.FileService;
import com.my.spring.service.ProjectService;
import com.my.spring.service.QualityQuestionService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.DataWrappern;
import com.my.spring.utils.SessionManager;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service("QualityQuestionService")
public class QualityQuestionServiceImpl implements QualityQuestionService {
    @Autowired
    QualityQuestionDao QualityQuestionDao;
    @Autowired
    QuestionFileDao QuestionFileDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserLogDao userLogDao;
    @Autowired
    MessageDao messageDao;
    @Autowired
    MessageFileDao messageFileDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    NoticeDao noticeDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    FileService fileService;
    @Autowired
    UserLogService userLogService;
    private String filePath = "files";
    private Integer fileType=2;
    @Override
    public DataWrapper<Void> addQualityQuestion(QualityQuestion QualityQuestion,String token,MultipartFile[] fileList,HttpServletRequest request,MultipartFile fileCode,MultipartFile[] voices) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	if(userInMemory.getSystemId()!=null){
				if(QualityQuestion.getProjectId()!=null){
					UserLog userLog = new UserLog();
	    			userLog.setProjectPart(ProjectDatas.Quality_area.getCode());
	    			userLog.setActionDate(new Date());
	    			userLog.setActionType(1);
	    			userLog.setProjectId(QualityQuestion.getProjectId());
	    			userLog.setUserId(userInMemory.getId());
	    			userLog.setSystemType(userInMemory.getSystemId());
	    			userLog.setVersion("3.0");
	    			userLogService.addUserLog(userLog, token);
				}
			}
        	QualityQuestion.setUserId(userInMemory.getId());
			if(QualityQuestion!=null){
				if(QualityQuestion.getQuestionDate()==null){
					QualityQuestion.setQuestionDate(new Date());
				}
	        	if(QualityQuestion.getState()==null){
	        		QualityQuestion.setState(0);
	        	}
				if(fileCode!=null){
					String path=filePath+"/"+"QualityQuestions";
				 	Files newfile=fileService.uploadFile(path, fileCode,fileType,request);
				 	QualityQuestion.setCodeInformation(newfile.getUrl());
				}
				if(!QualityQuestionDao.addQualityQuestion(QualityQuestion)){ 
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					return dataWrapper;
				}else{ 
					HashMap<String,String> hq = new HashMap<String,String>();
					if(fileList.length>0)
					{
						for(int k=0;k<fileList.length;k++){
							String path=filePath+"/"+"QualityQuestions";
						 	Files newfile=fileService.uploadFile(path, fileList[k],fileType,request);
						 	QuestionFile QualityQuestionFile=new QuestionFile();
						 	QualityQuestionFile.setQualityId(QualityQuestion.getId());
						 	QualityQuestionFile.setFileId(newfile.getId());
						 	String originName = fileList[k].getOriginalFilename();
						 	hq.put("imagUrl", newfile.getUrl());
							if (originName.contains(".")) {
								originName = originName.substring(0, originName.lastIndexOf("."));
							}
							QualityQuestionFile.setOriginName(originName);
						 	QuestionFileDao.addQuestionFile(QualityQuestionFile);
						}
							
					}
					if(voices.length>0){
						for(int s=0;s<voices.length;s++){
							String path=filePath+"/"+"QualityQuestions";
						 	Files newfile=fileService.uploadFile(path, voices[s],fileType,request);
						 	QuestionFile QualityQuestionFiles=new QuestionFile();
						 	QualityQuestionFiles.setQualityId(QualityQuestion.getId());
						 	QualityQuestionFiles.setFileId(newfile.getId());
						 	String originName = voices[s].getOriginalFilename();
							if (originName.contains(".")) {
								originName = originName.substring(0, originName.lastIndexOf("."));
							}
							QualityQuestionFiles.setOriginName(originName);
						 	QuestionFileDao.addQuestionFile(QualityQuestionFiles);
						}
					}
					/////////////////////////
				///////////////////////////
				List<Notice> nl = new ArrayList<Notice>();
				List<UserId> userIdList = userDao.getAllUserIdList(QualityQuestion.getUserList());
				if(!userIdList.isEmpty()){
					for(UserId s:userIdList){
						Notice nl2 = new Notice();
						nl2.setAboutId(QualityQuestion.getId());
						nl2.setCreateDate(new Date());
						nl2.setUserId(s.getId());
						nl2.setNoticeType(0);
						nl2.setProjectId(QualityQuestion.getProjectId());
						nl2.setReadState(0);
						nl.add(nl2);
					}
					
				}
				noticeDao.addNoticeList(nl);
				/////////////
				
				hq.put("title", QualityQuestion.getName());
				hq.put("content", QualityQuestion.getIntro());
				hq.put("createUserName", userInMemory.getRealName());
				if(userInMemory.getUserIconUrl()!=null){
					hq.put("userIconUrl", userInMemory.getUserIconUrl());
				}else{
					if(userInMemory.getUserIcon()!=null){
						Files files = fileService.getById(userInMemory.getUserIcon());
						hq.put("userIconUrl", files.getUrl());
					}
				}
				
				
				List<User> userList = new ArrayList<User>();
				int adminFlag=0;
				if(userInMemory.getUserType()==0 || userInMemory.getUserType()==1 || userInMemory.getUserType()==2){
        			adminFlag=-1;
        		}
				
				if(QualityQuestion.getUserList()!=null){
					List<String> aa = new ArrayList<String>();
					for(String s:QualityQuestion.getUserList().split(",")){
						aa.add(s);
					}
					userList=userDao.findUserLikeProjct(aa);
				}
				
				Project po = projectDao.getById(QualityQuestion.getProjectId());
				hq.put("projectName",po.getName());
				hq.put("aboutId", QualityQuestion.getId().toString());
				hq.put("createDate", Parameters.getSdfs().format(new Date()));
				String content="";
				if(po!=null){
					content=userInMemory.getRealName()+"在"+po.getName()+"项目里提交了一个标题为："+QualityQuestion.getName()+"的问题";
				}else{
					content=userInMemory.getRealName()+"提交了一个标题为："+QualityQuestion.getName()+"的问题";
				}
				
				////推送人包括自己
				String[] userids=new String[userList.size()+1];
				for(int b =0;b<userList.size();b++){
					userids[b]=userList.get(b).getId().toString();
				}
				userids[userids.length-1]=userInMemory.getId().toString();
				///0、质量   1、安全   2、施工任务单  3、 预付单  4、留言
				PushExample.testSendPushWithCustomConfig_ios(userids, content,0,hq);
				PushExample.testSendPushWithCustomConfig_android(userids, content,0,hq);
				}

			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
   
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteQualityQuestion(Long id,String token,HttpServletRequest request, Long projectId) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        DataWrapper<List<QuestionFile>> QuestionFileList=new DataWrapper<List<QuestionFile>>();
        DataWrapper<List<Message>> messageList=new DataWrapper<List<Message>>();
        DataWrapper<List<MessageFile>> messageListFile=new DataWrapper<List<MessageFile>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			User userInDB = userDao.getById(userInMemory.getId());
			if (userInDB != null) {
				if(userInDB.getUserType()==UserTypeEnum.Admin.getType()){
					if(id!=null){
						/////删除问题所对应的问题文件
						QuestionFileList=QuestionFileDao.getQuestionFileByQualityId(id);
						if(QuestionFileList.getData()!=null && QuestionFileList.getData().size()>0){
							if(!QuestionFileDao.deleteQuestionFileByQualityId(id)){
								dataWrapper.setErrorCode(ErrorCodeEnum.Error);
							}
							for(int i=0;i<QuestionFileList.getData().size();i++){
								Files files=new Files();
								files=fileDao.getById(QuestionFileList.getData().get(i).getFileId());
								if(files!=null){
									fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
									fileDao.deleteFiles(files.getId());
								}
							}
							
						}
						
						////////删除问题所对应的留言
						messageList=messageDao.getMessageListByQualityId(id);
						if(messageList.getData()!=null && messageList.getData().size()>0){
							for(int j=0;j<messageList.getData().size();j++){
								/////删除留言对应的文件
								messageListFile=messageFileDao.getMessageFileListByMessageId(messageList.getData().get(j).getId());
								////////无区别删除问题下的所有留言
								
								if(messageListFile.getData()!=null && messageListFile.getData().size()>0){
									
									for(int k=0;k<messageListFile.getData().size();k++){
										if(!messageFileDao.deleteMessageFileByMessageId(messageList.getData().get(j).getId())){
											dataWrapper.setErrorCode(ErrorCodeEnum.Error);
										}
										Files filess=new Files();
										filess=fileDao.getById(messageListFile.getData().get(k).getFileId());
										if(filess!=null){
											fileService.deleteFileByPath(filess.getUrl(),request);///删除实际文件
											fileDao.deleteFiles(filess.getId());
										}
									}
								}
								messageDao.deleteMessageByQualityId(messageList.getData().get(j).getAboutId());
							}
							
						}
						/////最后删除问题自身
						if(!QualityQuestionDao.deleteQualityQuestion(id,projectId)){
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}
					}else{
						dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
					}
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
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
    public DataWrapper<Void> deleteQualityQuestion(Long id,String token,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        DataWrapper<List<QuestionFile>> QualityQuestionFileList=new DataWrapper<List<QuestionFile>>();
        DataWrapper<List<Message>> messageList=new DataWrapper<List<Message>>();
        DataWrapper<List<MessageFile>> messageListFile=new DataWrapper<List<MessageFile>>();
        if (userInMemory != null) {
			User userInDB = userDao.getById(userInMemory.getId());
			if (userInDB != null) {
					if(id!=null){
						/////删除问题所对应的问题文件
						QualityQuestionFileList=QuestionFileDao.getQuestionFileByQualityId(id);
						if(QualityQuestionFileList.getData()!=null && QualityQuestionFileList.getData().size()>0){
							QuestionFileDao.deleteQuestionFileByQualityId(id);
							for(int i=0;i<QualityQuestionFileList.getData().size();i++){
								Files files=new Files();
								files=fileDao.getById(QualityQuestionFileList.getData().get(i).getFileId());
								if(files!=null){
									fileDao.deleteFiles(files.getId());
									fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
									
								}
							}
							
						}
						
						////////删除问题所对应的留言
						messageList=messageDao.getMessageListByQualityId(id);
						if(messageList.getData()!=null && messageList.getData().size()>0){
							for(int j=0;j<messageList.getData().size();j++){
								/////删除留言对应的文件
								messageListFile=messageFileDao.getMessageFileListByMessageId(messageList.getData().get(j).getId());
								if(messageListFile.getData()!=null && messageListFile.getData().size()>0){
									
									for(int k=0;k<messageListFile.getData().size();k++){
										if(!messageFileDao.deleteMessageFile(messageListFile.getData().get(k).getId())){
											dataWrapper.setErrorCode(ErrorCodeEnum.Error);
										}
										Files filess=new Files();
										filess=fileDao.getById(messageListFile.getData().get(k).getFileId());
										if(filess!=null){
											fileDao.deleteFiles(filess.getId());
											fileService.deleteFileByPath(filess.getUrl(),request);///删除实际文件
											
										}
									}
								}
								////////无区别删除该问题对应的所有留言
								messageDao.deleteMessageByQualityId(messageList.getData().get(j).getAboutId());
							}
							
						}
						/////最后删除问题自身
						if(QualityQuestionDao.getById(id)!=null){
							QualityQuestionDao.deleteQualityQuestion(id);
						}
					}else{
						dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
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
    public DataWrapper<Void> updateQualityQuestion(QualityQuestionPojo QualityQuestion,String token,MultipartFile[] file,HttpServletRequest request,MultipartFile[] voices) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			User userInDB = userDao.getById(userInMemory.getId());
			if (userInDB != null) {
					if(QualityQuestion!=null){
						QualityQuestion QualityQuestionOld=new QualityQuestion();
						QualityQuestionOld=QualityQuestionDao.getById(QualityQuestion.getId());
						if(QualityQuestionOld!=null) 
						{
							if(file.length>0){
								for(int i=0;i<file.length;i++){
									String path=filePath+"/"+"QualityQuestions";
								 	Files newfile=fileService.uploadFile(path, file[i],fileType,request);
								 	QuestionFile QualityQuestionFile=new QuestionFile();
								 	QualityQuestionFile.setQualityId(QualityQuestion.getId());
								 	QualityQuestionFile.setFileId(newfile.getId());
								 	QuestionFileDao.addQuestionFile(QualityQuestionFile);
								}
							}
							if(voices.length>0){
								for(int j=0;j<voices.length;j++){
									String path=filePath+"/"+"QualityQuestions";
								 	Files newfile=fileService.uploadFile(path, file[j],fileType,request);
								 	QuestionFile QualityQuestionFile=new QuestionFile();
								 	QualityQuestionFile.setQualityId(QualityQuestion.getId());
								 	QualityQuestionFile.setFileId(newfile.getId());
								 	QuestionFileDao.addQuestionFile(QualityQuestionFile);
								}
							}
							QualityQuestionOld.setIntro(QualityQuestion.getIntro());
							QualityQuestionOld.setName(QualityQuestion.getName());
							QualityQuestionOld.setState(QualityQuestion.getState());
							QualityQuestionOld.setTrades(QualityQuestion.getTrades());
							QualityQuestionDao.updateQualityQuestion(QualityQuestionOld);
						}
						else
						{
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
							return dataWrapper;
						}
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
    public DataWrapper<List<QualityQuestionPojo>> getQualityQuestionList(String content,Long projectId,String token, Integer pageIndex, Integer pageSize, QualityQuestion QualityQuestion) {
    	DataWrapper<List<QualityQuestionPojo>> datawrapper=new DataWrapper<List<QualityQuestionPojo>>();
    	List<QualityQuestionPojo> pojo = new ArrayList<QualityQuestionPojo>();
    	DataWrapper<List<QualityQuestion>> dataWrappers = new DataWrapper<List<QualityQuestion>>();
    	DataWrapper<List<QuestionFile>> dataWrapperFiles = new DataWrapper<List<QuestionFile>>();
    	User userInMemory=SessionManager.getSession(token);
    	if(userInMemory!=null){
    		Long[] userIdList=null;
    		if(userInMemory.getSystemId()!=null){
				if(QualityQuestion.getProjectId()!=null){
					UserLog userLog = new UserLog();
	    			userLog.setProjectPart(ProjectDatas.Quality_area.getCode());
	    			userLog.setActionDate(new Date());
	    			userLog.setActionType(0);
	    			if(QualityQuestion.getId()!=null){
	    				userLog.setFileId(QualityQuestion.getId());
	    			}
	    			userLog.setProjectId(QualityQuestion.getProjectId());
	    			userLog.setUserId(userInMemory.getId());
	    			userLog.setSystemType(userInMemory.getSystemId());
	    			userLog.setVersion("3.0");
	    			userLogService.addUserLog(userLog, token);
				}
			}
			if(content!=null){
				
				/////问题状态搜索
				if("待解决".contains(content)){
					QualityQuestion.setState(0);
				}
				if("已解决".contains(content)){
					QualityQuestion.setState(1);
				}
				List<User> users=new ArrayList<User>();
				users=userDao.findUserLikeRealName(content).getData();
    			if(users!=null)
    			{
					if(users.size()>0)
					{
						userIdList=new Long[users.size()];
						for(int i=0;i<users.size();i++)
						{
							userIdList[i]=users.get(i).getId();
						}
					}
				}
			}
			String projectList=null;
			if(QualityQuestion.getId()!=null){
				////////////更新消息为已读
				Notice notice = new Notice();
				notice=noticeDao.getByAdoutIdAndUserId(userInMemory.getId(),QualityQuestion.getId(),0);
				if(notice!=null){
					if(notice.getReadState()!=1){
						notice.setReadState(1);
						notice.setUpdateDate(new Date());
						noticeDao.updateNotice(notice);
					}
				}
			}
			dataWrappers= QualityQuestionDao.getQualityQuestionList(content,projectId,pageIndex,pageSize,QualityQuestion,userIdList,projectList);
			if(dataWrappers.getData()!=null && dataWrappers.getData().size()>0){
				for(int i=0;i<dataWrappers.getData().size();i++)
		        {
		        	QualityQuestionPojo QualityQuestionpojo=new QualityQuestionPojo();
		        	
	        		dataWrapperFiles=QuestionFileDao.getQuestionFileByQualityId(dataWrappers.getData().get(i).getId());
		        	if(dataWrapperFiles.getData()!=null)
		        	{
		        		String[] fileLists=new String[dataWrapperFiles.getData().size()];
		            	String[] fileNameLists=new String[dataWrapperFiles.getData().size()];
		            	int flag=0;
		        		for(int j=0;j<dataWrapperFiles.getData().size();j++)
		        		{
		        			Long fileId=dataWrapperFiles.getData().get(j).getFileId();
		        			Files file=fileDao.getById(fileId);
		        			if(file!=null)
		        			{
		        				String fileItem=file.getUrl();
		        				fileLists[flag]=fileItem;
		        				String nameList=dataWrapperFiles.getData().get(j).getOriginName();
		            			if(nameList!=null)
		            				fileNameLists[flag]=nameList;
		        				flag++;
		        			}
			        	}
		        		if(fileLists!=null)
		        		{
		        			List<String> imageList = new ArrayList<String>();
		        			List<String> voiceList = new ArrayList<String>();
		        			for(String items:fileLists){
		        				if(Parameters.getFileType(items).equals("mp3") || Parameters.getFileType(items).equals("wav")){
		        					voiceList.add(items);
		        				}else if(!Parameters.getFileType(items).equals("dat")){
		        					imageList.add(items);
		        				}
		        			}
		        			QualityQuestionpojo.setImageUrlList(imageList);
		        			QualityQuestionpojo.setVoiceUrlList(voiceList);
		        			QualityQuestionpojo.setFileList(fileLists);
		        		}				
			        }
		        	if(dataWrappers.getData().get(i).getUserList()!=null && !dataWrappers.getData().get(i).getUserList().equals("")){
		        		String[] nameList = dataWrappers.getData().get(i).getUserList().split(",");
		        		if(nameList.length>0){
		        			List<String> nameLists =new ArrayList<String>();
		        			for(int k=0;k<nameList.length;k++){
		        				User user =userDao.getById(Long.valueOf(nameList[k]));
		        				if(user!=null){
		        					nameLists.add(user.getRealName());
		        				}
		        			}
		        			QualityQuestionpojo.setUserNameLists(nameLists);
			        	}else{
			        		QualityQuestionpojo.setUserNameLists(null);
			        	}
		        	}
		        	User user = userDao.getById(dataWrappers.getData().get(i).getUserId());
		        	if(user!=null){
		        		String username=userDao.getById(dataWrappers.getData().get(i).getUserId()).getRealName();
			        	QualityQuestionpojo.setUserId(username);
		        	}
		        	QualityQuestionpojo.setModelFlag(dataWrappers.getData().get(i).getModelFlag());
		        	QualityQuestionpojo.setCodeInformation(dataWrappers.getData().get(i).getCodeInformation());
		        	QualityQuestionpojo.setPriority(dataWrappers.getData().get(i).getPriority());
		        	QualityQuestionpojo.setId(dataWrappers.getData().get(i).getId());
		        	QualityQuestionpojo.setIntro(dataWrappers.getData().get(i).getIntro());
		        	QualityQuestionpojo.setName(dataWrappers.getData().get(i).getName());
		        	QualityQuestionpojo.setProjectId(dataWrappers.getData().get(i).getProjectId());
		        	QualityQuestionpojo.setProjectName(projectDao.getById(dataWrappers.getData().get(i).getProjectId()).getName());
		        	QualityQuestionpojo.setPosition(dataWrappers.getData().get(i).getPosition());
		        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		        	QualityQuestionpojo.setQuestionDate(sdf.format(dataWrappers.getData().get(i).getQuestionDate()));
		        	QualityQuestionpojo.setState(dataWrappers.getData().get(i).getState());
		        	QualityQuestionpojo.setTrades(dataWrappers.getData().get(i).getTrades());    
		        	if(dataWrappers.getData().get(i).getUserId()!=null){
		        		User users = userDao.getById(dataWrappers.getData().get(i).getUserId());
		        		if(users!=null){
		        			if(users.getUserIconUrl()!=null){
		        				QualityQuestionpojo.setCreateUserIcon(users.getUserIconUrl());
		        			}else{
		        				Files files = fileService.getById(users.getUserIcon());
		        				QualityQuestionpojo.setCreateUserIcon(files.getUrl());
		        			}
		        			
		        		}
		        	}
		        	pojo.add(i, QualityQuestionpojo);
		        }
			}
			
			if(pojo.size()>0){
				datawrapper.setData(pojo);
				datawrapper.setCurrentPage(dataWrappers.getCurrentPage());
				datawrapper.setCallStatus(dataWrappers.getCallStatus());
				datawrapper.setNumberPerPage(dataWrappers.getNumberPerPage());
				datawrapper.setTotalNumber(dataWrappers.getTotalNumber());
				datawrapper.setTotalPage(dataWrappers.getTotalPage());
				Long QualityQuestionSort=QualityQuestionDao.getQualityQuestionListOfSort();
				Long QualityQuestionImportant=QualityQuestionDao.getQualityQuestionListOfImportant();
				Long QualityQuestionAll=QualityQuestionDao.getQualityQuestionList();
				NumberFormat nf = NumberFormat.getNumberInstance();   
		        nf.setMaximumFractionDigits(2);   
		    	double sortPercent=( (double)QualityQuestionSort/(double)QualityQuestionAll);
		    	sortPercent=sortPercent*100;
		    	if((sortPercent+0.5)>Math.ceil(sortPercent))
		    	{
		    		sortPercent=Math.ceil(sortPercent);
		    	}else{
		    		sortPercent=Math.floor(sortPercent);
		    	}
		    	
		    	double importantPercent=((double)QualityQuestionImportant/(double)QualityQuestionAll);
		    	importantPercent=importantPercent*100;
		    	if((importantPercent+0.5)>Math.ceil(importantPercent))
		    	{
		    		importantPercent=Math.ceil(importantPercent);
		    	}else{
		    		importantPercent=Math.floor(importantPercent);
		    	}
		    	if(datawrapper.getData()!=null && datawrapper.getData().size()>0){
				}
		    	if(datawrapper.getData()!=null && datawrapper.getData().size()>0){
		    		datawrapper.getData().get(0).setImportantPercent((int)importantPercent);
	    	    	datawrapper.getData().get(0).setUrgentPercent(100-(int)sortPercent-(int)importantPercent);
		    		datawrapper.getData().get(0).setSortPercent((int)sortPercent);
		    	}
			}
    	}else{
    		datawrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
    	if(datawrapper.getCallStatus()==CallStatusEnum.SUCCEED && datawrapper.getData()==null){
	       	List<QualityQuestionPojo> pas= new ArrayList<QualityQuestionPojo>();
	       	datawrapper.setData(pas);
	    }
    	return datawrapper;
    }

	@Override
	public DataWrapper<QualityQuestionPojo> getQualityQuestionDetailsByAdmin(Long QualityQuestionId, String token,String weixin) {
		DataWrapper<QualityQuestionPojo> dataWrapper = new DataWrapper<QualityQuestionPojo>();
		if(weixin!=null){
			if(weixin.equals("weixin")){
				 QualityQuestion QualityQuestion=QualityQuestionDao.getById(QualityQuestionId);
					if(QualityQuestion!=null){
						QualityQuestionPojo QualityQuestionPojo=new QualityQuestionPojo();
						QualityQuestionPojo.setId(QualityQuestion.getId());
						QualityQuestionPojo.setCodeInformation(QualityQuestion.getCodeInformation());
						QualityQuestionPojo.setIntro(QualityQuestion.getIntro());
						QualityQuestionPojo.setName(QualityQuestion.getName());
						QualityQuestionPojo.setPriority(QualityQuestion.getPriority());
						QualityQuestionPojo.setProjectId(QualityQuestion.getProjectId());
						Project test = new Project();
						test=projectDao.getById(QualityQuestion.getProjectId());
						if(test!=null){
							QualityQuestionPojo.setProjectName(test.getName());
						}
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						QualityQuestionPojo.setQuestionDate(sdf.format(QualityQuestion.getQuestionDate()));
						QualityQuestionPojo.setState(QualityQuestion.getState());
						QualityQuestionPojo.setModelFlag(QualityQuestion.getModelFlag());
						QualityQuestionPojo.setTrades(QualityQuestion.getTrades());
						String userlist="";
						if(QualityQuestion.getUserList()!=null){
							for(int s=0;s<QualityQuestion.getUserList().split(",").length;s++){
								if(s==0){
									userlist=userDao.getById(Long.valueOf(QualityQuestion.getUserList().split(",")[s])).getRealName();
								}else{
									userlist=userlist+","+userDao.getById(Long.valueOf(QualityQuestion.getUserList().split(",")[s])).getRealName();
								}
							}
						}
						QualityQuestionPojo.setUserList(userlist);
						QualityQuestionPojo.setUserId(userDao.getById(QualityQuestion.getUserId()).getRealName());
						DataWrapper<List<QuestionFile>> file=new DataWrapper<List<QuestionFile>>();
						file=QuestionFileDao.getQuestionFileByQualityId(QualityQuestion.getId());
						if(file.getData()!=null && file.getData().size()>0){
							String[] fileList=new String[file.getData().size()];
							for(int i=0;i<file.getData().size();i++){
								Files files=new Files();
								files=fileDao.getById(file.getData().get(i).getFileId());
								if(files!=null){
									fileList[i]=files.getUrl();
								}
							}
							QualityQuestionPojo.setFileList(fileList);
						}
						
						dataWrapper.setData(QualityQuestionPojo);
					}
					return dataWrapper;
			 }
		} 
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory==null ){
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
			return dataWrapper;
		}
        if (userInMemory != null) {
			User userInDB = userDao.getById(userInMemory.getId());
			if (userInDB != null) {
				if(QualityQuestionId!=null){
					QualityQuestion QualityQuestion=QualityQuestionDao.getById(QualityQuestionId);
					if(QualityQuestion==null){
						dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
					}
					if(QualityQuestion!=null){
						if(userInDB.getUserType()==1 || userInDB.getUserType()==0 || userInDB.getUserType()==3){
							QualityQuestionPojo QualityQuestionPojo=new QualityQuestionPojo();
							QualityQuestionPojo.setId(QualityQuestion.getId());
							QualityQuestionPojo.setCodeInformation(QualityQuestion.getCodeInformation());
							QualityQuestionPojo.setIntro(QualityQuestion.getIntro());
							QualityQuestionPojo.setName(QualityQuestion.getName());
							QualityQuestionPojo.setPriority(QualityQuestion.getPriority());
							QualityQuestionPojo.setProjectId(QualityQuestion.getProjectId());
							if(QualityQuestion.getProjectId()!=null){
								Project projects= new Project();
								projects=projectDao.getById(QualityQuestion.getProjectId());
								if(projects!=null){
									QualityQuestionPojo.setProjectName(projects.getName());
									if(projects.getPicId()!=null && !projects.getPicId().equals(""))
									{
										Files files=new Files();
										files=fileDao.getById(Long.valueOf(projects.getPicId()));
										if(files!=null){
											QualityQuestionPojo.setProjectPicUrl(files.getUrl());
										}
									}
									
								}
							}
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
							QualityQuestionPojo.setQuestionDate(sdf.format(QualityQuestion.getQuestionDate()));
							QualityQuestionPojo.setState(QualityQuestion.getState());
							QualityQuestionPojo.setTrades(QualityQuestion.getTrades());
							QualityQuestionPojo.setUserId(userDao.getById(QualityQuestion.getUserId()).getRealName());
							Long userIdis=userDao.getById(QualityQuestion.getUserId()).getId();
							DataWrapper<List<QuestionFile>> file=new DataWrapper<List<QuestionFile>>();
							file=QuestionFileDao.getQuestionFileByQualityId(QualityQuestion.getId());
							if(file.getData()!=null && file.getData().size()>0){
								String[] fileList=new String[file.getData().size()];
								for(int i=0;i<file.getData().size();i++){
									Files files=new Files();
									files=fileDao.getById(file.getData().get(i).getFileId());
									if(files!=null){
										fileList[i]=files.getUrl();
									}
								}
								if(fileList!=null)
				        		{
				        			List<String> imageList = new ArrayList<String>();
				        			List<String> voiceList = new ArrayList<String>();
				        			for(String items:fileList){
				        				if(Parameters.getFileType(items).equals("mp3") || Parameters.getFileType(items).equals("wav")){
				        					voiceList.add(items);
				        				}else if(!Parameters.getFileType(items).equals("dat")){
				        					imageList.add(items);
				        				}
				        			}
				        			QualityQuestionPojo.setImageUrlList(imageList);
				        			QualityQuestionPojo.setVoiceUrlList(voiceList);
				        		}			
								QualityQuestionPojo.setFileList(fileList);
							}
							////////////更新消息为已读
							Notice notice = new Notice();
							notice=noticeDao.getByAdoutIdAndUserId(userInDB.getId(),QualityQuestion.getId(),0);
							if(notice!=null){
								if(notice.getReadState()!=1){
									notice.setReadState(1);
									notice.setUpdateDate(new Date());
									noticeDao.updateNotice(notice);
								}
							}
							//////添加打点记录
							UserLog userLog = new UserLog();
							userLog.setActionDate(new Date());
							userLog.setFileId(QualityQuestion.getId());
							userLog.setProjectId(QualityQuestion.getProjectId());
							userLog.setProjectPart(ProjectDatas.Quality_area.getCode());
							userLog.setUserId(userInDB.getId());
							userLogDao.addUserLog(userLog);
							dataWrapper.setData(QualityQuestionPojo);
						}else{
							dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
						}
						
					}
				}
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
			}
		} 
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<QualityQuestion>> getQualityQuestionsByLike(String content, String token) {
		DataWrapper<List<QualityQuestion>> dataWrapper= new DataWrapper<List<QualityQuestion>>();
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			dataWrapper=QualityQuestionDao.getQualityQuestionListByLike(content);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return null;
	}

	@Override
	public DataWrapper<Void> updateQualityQuestionState(Long QualityQuestionId, String token, Integer state) {
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				QualityQuestion QualityQuestion=null;
				QualityQuestion=QualityQuestionDao.getById(QualityQuestionId);	
				if(userInMemory.getSystemId()!=null){
					if(QualityQuestion.getProjectId()!=null){
						UserLog userLog = new UserLog();
		    			userLog.setProjectPart(ProjectDatas.Quality_area.getCode());
		    			userLog.setActionDate(new Date());
		    			userLog.setActionType(3);
		    			userLog.setProjectId(QualityQuestion.getProjectId());
		    			userLog.setUserId(userInMemory.getId());
		    			userLog.setSystemType(userInMemory.getSystemId());
		    			userLog.setVersion("3.0");
		    			userLogService.addUserLog(userLog, token);
					}
				}
				if(QualityQuestion!=null){
				/////安全问题状态更新，消息提醒存储
					Notice notice = new Notice();
					notice.setAboutId(QualityQuestion.getId());
					notice.setNoticeType(1);
					notice.setCreateDate(new Date());
					notice.setRemark("finished");
					notice.setUserId(QualityQuestion.getUserId());
					notice.setProjectId(QualityQuestion.getProjectId());
					Integer statse=0;
					notice.setReadState(statse);
					noticeDao.addNotice(notice);
					if(QualityQuestion.getUserId().equals(userInMemory.getId())){
						QualityQuestion.setState(state);
						if(!QualityQuestionDao.updateQualityQuestion(QualityQuestion)){
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}
					}else{
						dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
					}
					
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
				}
				
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
	}

	@Override
	public DataWrapper<List<QualityQuestionPojo>> getQualityQuestionListByUserId(String token, Integer pageIndex, Integer pageSize) {
		DataWrapper<List<QualityQuestionPojo>> datawrapper=new DataWrapper<List<QualityQuestionPojo>>();
    	User userInMemory=SessionManager.getSession(token);
    	QualityQuestion QualityQuestion=new QualityQuestion();
    	if(userInMemory!=null){
    			QualityQuestion.setUserId(userInMemory.getId());
    			datawrapper= QualityQuestionDao.getQualityQuestionList(pageIndex,pageSize,QualityQuestion);
    			Long QualityQuestionSort=QualityQuestionDao.getQualityQuestionListOfSort();
    			Long QualityQuestionImportant=QualityQuestionDao.getQualityQuestionListOfImportant();
    			Long QualityQuestionAll=QualityQuestionDao.getQualityQuestionList();
    			NumberFormat nf = NumberFormat.getNumberInstance();   
    	        nf.setMaximumFractionDigits(2);   
    	    	double sortPercent=( (double)QualityQuestionSort/(double)QualityQuestionAll);
    	    	sortPercent=sortPercent*100;
    	    	if((sortPercent+0.5)>Math.ceil(sortPercent))
    	    	{
    	    		sortPercent=Math.ceil(sortPercent);
    	    	}else{
    	    		sortPercent=Math.floor(sortPercent);
    	    	}
    	    	
    	    	double importantPercent=((double)QualityQuestionImportant/(double)QualityQuestionAll);
    	    	importantPercent=importantPercent*100;
    	    	if((importantPercent+0.5)>Math.ceil(importantPercent))
    	    	{
    	    		importantPercent=Math.ceil(importantPercent);
    	    	}else{
    	    		importantPercent=Math.floor(importantPercent);
    	    	}
    	    	if(datawrapper.getData()==null || datawrapper.getData().size()<=0){
    	    		List<QualityQuestionPojo> QualityQuestionPojolist=new ArrayList<QualityQuestionPojo>();
    	    		QualityQuestionPojo QualityQuestionPojo=new QualityQuestionPojo();
    	    		QualityQuestionPojo.setSortPercent((int)sortPercent);
    	    		QualityQuestionPojo.setImportantPercent((int)importantPercent);
    	    		QualityQuestionPojo.setUrgentPercent(100-(int)sortPercent-(int)importantPercent);
    	    		QualityQuestionPojolist.add(QualityQuestionPojo);
    	    		datawrapper.setData(QualityQuestionPojolist);
    	    	}else{
    	    		datawrapper.getData().get(0).setImportantPercent((int)importantPercent);
        	    	datawrapper.getData().get(0).setUrgentPercent(100-(int)sortPercent-(int)importantPercent);
    	    		datawrapper.getData().get(0).setSortPercent((int)sortPercent);
    	    	}
    	    	
    	}else{
    		datawrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
    	return datawrapper;
	}
	public DataWrappern< PageInfo,List<QualityQuestionPojo>,HashMap<String,String>> getQualityQuestionHash(Integer searchType,String content, Long projectId, String token,
			Integer pageIndex, Integer pageSize, QualityQuestion QualityQuestion) {
		DataWrappern<PageInfo,List<QualityQuestionPojo>, HashMap<String, String>> datawrapper=new DataWrappern<PageInfo,List<QualityQuestionPojo>,HashMap<String, String>>();
    	List<QualityQuestionPojo> pojo = new ArrayList<QualityQuestionPojo>();
		PageInfo pageInfos = new PageInfo(); 
    	DataWrapper<List<QualityQuestion>> dataWrappers = new DataWrapper<List<QualityQuestion>>();
    	DataWrapper<List<QuestionFile>> dataWrapperFiles = new DataWrapper<List<QuestionFile>>();
    	User userInMemory=SessionManager.getSession(token);
    	if(userInMemory!=null){
    		Long[] userIdList=null;
    		if(userInMemory.getSystemId()==0 || userInMemory.getSystemId()==1 || userInMemory.getSystemId()==-1){
    			UserLog userLog = new UserLog();
    			userLog.setProjectPart(ProjectDatas.Quality_area.getCode());
    			userLog.setActionDate(new Date());
    			userLog.setUserId(userInMemory.getId());
    			userLog.setSystemType(userInMemory.getSystemId());
    			//userLog.setVersion("3.0");
    			if(QualityQuestion.getProjectId()!=null){
    				userLog.setProjectId(QualityQuestion.getProjectId());
    			}
    			if(QualityQuestion.getId()!=null){
    				userLog.setFileId(QualityQuestion.getId());
    			}
    			userLogDao.addUserLog(userLog);
    		}
    		if(searchType!=null){
    			if(searchType==0){
    				User users = userDao.getByUserRealName(content);
    				if(users!=null){
    					content=users.getId().toString();
    				}
    			}
    		}
    		if(QualityQuestion.getId()!=null){
				////////////更新消息为已读
				Notice notice = new Notice();
				notice=noticeDao.getByAdoutIdAndUserId(userInMemory.getId(),QualityQuestion.getId(),0);
				if(notice!=null){
					if(notice.getReadState()!=1){
						notice.setReadState(1);
						notice.setUpdateDate(new Date());
						noticeDao.updateNotice(notice);
					}
				}
			}
			/////问题等级搜索
			/////当用户是总经理级别的时候,问题搜索只能搜索重要和紧急，同时也只能看重要和紧急问题，不看一般问题 
			dataWrappers= QualityQuestionDao.getQualityQuestionLists(searchType,content,projectId,pageIndex,pageSize,QualityQuestion,userIdList);
			for(int i=0;i<dataWrappers.getData().size();i++)
	        {
	        	QualityQuestionPojo QualityQuestionpojo=new QualityQuestionPojo();
	        	
        		dataWrapperFiles=QuestionFileDao.getQuestionFileByQualityId(dataWrappers.getData().get(i).getId());
	        	if(dataWrapperFiles.getData()!=null)
	        	{
	        		String[] fileLists=new String[dataWrapperFiles.getData().size()];
	            	String[] fileNameLists=new String[dataWrapperFiles.getData().size()];
	            	int flag=0;
	        		for(int j=0;j<dataWrapperFiles.getData().size();j++)
	        		{
	        			Long fileId=dataWrapperFiles.getData().get(j).getFileId();
	        			Files file=fileDao.getById(fileId);
	        			if(file!=null)
	        			{
	        				String fileItem=file.getUrl();
	        				fileLists[flag]=fileItem;
	        				String nameList=dataWrapperFiles.getData().get(j).getOriginName();
	            			if(nameList!=null)
	            				fileNameLists[flag]=nameList;
	        				flag++;
	        			}
		        	}
	        		if(fileLists!=null)
	        		{
	        			QualityQuestionpojo.setFileList(fileLists);
	        		}				
		        }
	        	if(dataWrappers.getData().get(i).getUserList()!=null){
	        		String[] nameList = dataWrappers.getData().get(i).getUserList().split(",");
	        		if(nameList.length>0){
	        			List<String> nameLists =new ArrayList<String>() ;
	        			for(int k=0;k<nameList.length;k++){
	        				User users =userDao.getById(Long.valueOf(nameList[k]));
	        				if(users!=null){
	        					nameLists.add(users.getRealName());
	        				}
	        			  
	        			}
	        			QualityQuestionpojo.setUserNameLists(nameLists);
		        	}else{
		        		QualityQuestionpojo.setUserNameLists(null);
		        	}
	        	}
	        	User users=userDao.getById(dataWrappers.getData().get(i).getUserId());
	        	if(users!=null){
	        		String username=users.getRealName();
	        		QualityQuestionpojo.setUserId(username);
	        		if(users.getUserIcon()!=null){
	        			Files files = fileService.getById(users.getUserIcon());
	        			if(files!=null){
	        				QualityQuestionpojo.setCreateUserIcon(files.getUrl());
	        			}
	        		}else{
	        			QualityQuestionpojo.setCreateUserIcon(users.getUserIconUrl());
	        		}
	        	}
	        	QualityQuestionpojo.setModelFlag(dataWrappers.getData().get(i).getModelFlag());
	        	QualityQuestionpojo.setCodeInformation(dataWrappers.getData().get(i).getCodeInformation());
	        	QualityQuestionpojo.setPriority(dataWrappers.getData().get(i).getPriority());
	        	QualityQuestionpojo.setId(dataWrappers.getData().get(i).getId());
	        	QualityQuestionpojo.setIntro(dataWrappers.getData().get(i).getIntro());
	        	QualityQuestionpojo.setName(dataWrappers.getData().get(i).getName());
	        	QualityQuestionpojo.setProjectId(dataWrappers.getData().get(i).getProjectId());
	        	QualityQuestionpojo.setProjectName(projectDao.getById(dataWrappers.getData().get(i).getProjectId()).getName());
	        	QualityQuestionpojo.setPosition(dataWrappers.getData().get(i).getPosition());
	        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        	QualityQuestionpojo.setQuestionDate(sdf.format(dataWrappers.getData().get(i).getQuestionDate()));
	        	QualityQuestionpojo.setState(dataWrappers.getData().get(i).getState());
	        	QualityQuestionpojo.setTrades(dataWrappers.getData().get(i).getTrades()); 
	        	QualityQuestionpojo.setUserid(dataWrappers.getData().get(i).getUserId());
	        	int messageNum=0;
	        	List<Message> mes = messageDao.getMessageListByQualityId(dataWrappers.getData().get(i).getId()).getData();
	        	if(mes!=null){
	        		messageNum = mes.size();
	        	}
	        	QualityQuestionpojo.setMessageNum(messageNum);
	        	pojo.add(i, QualityQuestionpojo);
	        }
			if(pojo.size()>0){
				pageInfos.setCurrentPage(dataWrappers.getCurrentPage());
				pageInfos.setNumberPerPage(dataWrappers.getNumberPerPage());
				pageInfos.setTotalNumber(dataWrappers.getTotalNumber());
				pageInfos.setTotalPage(dataWrappers.getTotalPage());
		
				Long QualityQuestionSort=QualityQuestionDao.getQualityQuestionListOfSort();
				Long QualityQuestionImportant=QualityQuestionDao.getQualityQuestionListOfImportant();
				Long QualityQuestionAll=QualityQuestionDao.getQualityQuestionList();
				NumberFormat nf = NumberFormat.getNumberInstance();   
		        nf.setMaximumFractionDigits(2);   
		    	double sortPercent=( (double)QualityQuestionSort/(double)QualityQuestionAll);
		    	sortPercent=sortPercent*100;
		    	if((sortPercent+0.5)>Math.ceil(sortPercent))
		    	{
		    		sortPercent=Math.ceil(sortPercent);
		    	}else{
		    		sortPercent=Math.floor(sortPercent);
		    	}
		    	double importantPercent=((double)QualityQuestionImportant/(double)QualityQuestionAll);
		    	importantPercent=importantPercent*100;
		    	if((importantPercent+0.5)>Math.ceil(importantPercent))
		    	{
		    		importantPercent=Math.ceil(importantPercent);
		    	}else{
		    		importantPercent=Math.floor(importantPercent);
		    	}
		    	datawrapper.setPage(pageInfos);
		    	if(pojo!=null && pojo.size()>0){
		    		HashMap<String,String> sm = new HashMap<String,String>();
		    		String ip=importantPercent+"";
		    		String up=(100-importantPercent-sortPercent)+"";
		    		String sp=sortPercent+"";
		    		sm.put("importantPercent", ip);
		    		sm.put("urgentPercent", up);
		    		sm.put("sortPercent", sp);
		    		datawrapper.setData(pojo);
		    		datawrapper.setOthers(sm);
		    	}
			}
			
    	}else{
    		datawrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
    	return datawrapper;
	}

	@Override
	public DataWrapper<List<QuestionCopy>> getQuestionListOfNotRead(String token, Integer pageIndex, Integer pageSize) {
		DataWrapper<List<QuestionCopy>> resultList = new DataWrapper<List<QuestionCopy>>();
		List<QuestionCopy> questionCopys = new ArrayList<QuestionCopy>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType() || userInMemory.getUserType()==UserTypeEnum.User.getType()){
				questionCopys=QualityQuestionDao.getQuestionListByAdmin(userInMemory.getId(),pageIndex,pageSize);
				resultList.setTotalNumber(questionCopys.size());
			}else if(userInMemory.getUserType()==UserTypeEnum.Leader.getType()){
				Role role = new Role();
				role = roleDao.getById(userInMemory.getRoleId());
				////////////
				/////////////当前用户是该项目的项目负责人能看所有问题
				if(role!=null){
					if(role.getName()!=null){
						if(role.getName().equals("项目负责人")){
							/////获取当前角色所管项目的所有可读问题(已读)
							questionCopys=QualityQuestionDao.getQuestionListByLeader(userInMemory.getId(),pageIndex,pageSize);
							resultList.setTotalNumber(questionCopys.size());
						}else{
							questionCopys=QualityQuestionDao.getQuestionListByNorUser(userInMemory.getId(),pageIndex,pageSize);
							resultList.setTotalNumber(questionCopys.size());
						}
					}
				}
			}
			resultList.setData(questionCopys);
		}else{
			resultList.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return resultList;
	}
}
