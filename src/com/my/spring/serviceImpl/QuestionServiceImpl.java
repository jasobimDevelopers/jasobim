package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.MessageDao;
import com.my.spring.DAO.MessageFileDao;
import com.my.spring.DAO.NoticeDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.QuestionDao;
import com.my.spring.DAO.QuestionFileDao;
import com.my.spring.DAO.RoleDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserProjectDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.jpush.PushExample;
import com.my.spring.model.Files;
import com.my.spring.model.Message;
import com.my.spring.model.MessageFile;
import com.my.spring.model.Notice;
import com.my.spring.model.PageInfo;
import com.my.spring.model.Project;
import com.my.spring.model.Question;
import com.my.spring.model.QuestionFile;
import com.my.spring.model.QuestionPojo;
import com.my.spring.model.User;
import com.my.spring.model.UserId;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.QuestionService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.DataWrappern;
import com.my.spring.utils.SessionManager;


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

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    QuestionFileDao questionFileDao;
    @Autowired
    UserDao userDao;
    @Autowired
    NoticeDao noticeDao;
    @Autowired
    MessageDao messageDao;
    @Autowired
    MessageFileDao messageFileDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    FileService fileService;
    @Autowired
    UserProjectDao upDao;
    private String filePath = "files";
    private Integer fileType=2;
    @Override
    public DataWrapper<Void> addQuestion(Question question,String token,MultipartFile[] fileList,HttpServletRequest request,MultipartFile fileCode,MultipartFile[] voices) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
    	String userLists="";
		String[] userList=null;
		List<String> aa = new ArrayList<String>();
		List<Notice> nl = new ArrayList<Notice>();
        if (userInMemory != null) {
        	question.setUserId(userInMemory.getId());
        	if(question!=null){
				if(question.getQuestionDate()==null){
					question.setQuestionDate(new Date());
				}
	        	if(question.getState()==null){
	        		question.setState(0);
	        	}
				if(fileCode!=null){
					String path=filePath+"/"+"questions";
				 	Files newfile=fileService.uploadFile(path, fileCode,fileType,request);
				 	question.setCodeInformation(newfile.getUrl());
				}
				if(!questionDao.addQuestion(question)){ 
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					return dataWrapper;
				}else{ 
					///////添加通知关系
				
					if(question.getUserList()!=null){
						userLists=question.getUserList();
						if(userInMemory.getId()!=question.getUserId()){
							userLists=question.getUserId()+","+userLists;
						}
						userList = userLists.split(",");
						if(userList!=null){
							for(String bb:userList){
								if(!bb.equals(userInMemory.getId())){
									aa.add(bb);
								}
							}
						}
					}
					List<UserId> userIdList = userDao.getAllUserIdList(question.getUserList());
					if(!userIdList.isEmpty()){
						for(UserId s:userIdList){
							Notice nl2 = new Notice();
							nl2.setAboutId(question.getId());
							nl2.setCreateDate(new Date());
							nl2.setUserId(s.getId());
							nl2.setNoticeType(1);
							nl2.setProjectId(question.getProjectId());
							nl2.setReadState(0);
							nl.add(nl2);
						}
						noticeDao.addNoticeList(nl);
					}
					/////////////
					HashMap<String,String> hq = new HashMap<String,String>();
					hq.put("title", question.getName());
					hq.put("content", question.getIntro());
					if(fileList.length>0)
					{
						for(int k=0;k<fileList.length;k++){
							String path=filePath+"/"+"questions";
						 	Files newfile=fileService.uploadFile(path, fileList[k],fileType,request);
						 	QuestionFile questionFile=new QuestionFile();
						 	questionFile.setQuestionId(question.getId());
						 	questionFile.setFileId(newfile.getId());
						 	String originName = fileList[k].getOriginalFilename();
							if (originName.contains(".")) {
								originName = originName.substring(0, originName.lastIndexOf("."));
							}
							hq.put("imagUrl", newfile.getUrl());
							questionFile.setOriginName(originName);
						 	questionFileDao.addQuestionFile(questionFile);
						}
							
					}
					if(voices.length>0){
						for(int s=0;s<voices.length;s++){
							
							String path=filePath+"/"+"questions";
						 	Files newfile=fileService.uploadFile(path, voices[s],fileType,request);
						 	QuestionFile questionFiles=new QuestionFile();
						 	questionFiles.setQuestionId(question.getId());
						 	questionFiles.setFileId(newfile.getId());
						 	String originName = voices[s].getOriginalFilename();
							if (originName.contains(".")) {
								originName = originName.substring(0, originName.lastIndexOf("."));
							}
							questionFiles.setOriginName(originName);
						 	questionFileDao.addQuestionFile(questionFiles);
						}
					}
					/////////////////////////
					///////////////////////////
					
					List<User> users=userDao.findUserLikeProjct(aa);
					String[] userids= new String[users.size()];
					for(int b =0;b<users.size();b++){
						userids[b]=users.get(b).getId().toString();
					}
					Project po = projectDao.getById(question.getProjectId());
					hq.put("createUserName", userInMemory.getRealName());
					if(userInMemory.getUserIconUrl()!=null){
						hq.put("userIconUrl", userInMemory.getUserIconUrl());
					}else{
						if(userInMemory.getUserIcon()!=null){
							Files files = fileService.getById(userInMemory.getUserIcon());
							hq.put("userIconUrl", files.getUrl());
						}
					}
					
					hq.put("projectName", "来自  "+po.getName());
					hq.put("aboutId", question.getId().toString());
					hq.put("createDate", Parameters.getSdfs().format(new Date()));
					String content="";
					if(po!=null){
						content=userInMemory.getRealName()+"在"+po.getName()+"项目里提交了一个标题为："+question.getName()+"的问题";
					}else{
						content=userInMemory.getRealName()+"提交了一个标题为："+question.getName()+"的问题";
					}
					///0、质量   1、安全   2、施工任务单  3、 预付单  4、留言
					PushExample.testSendPushWithCustomConfig_ios(userids, content,1,hq);
					PushExample.testSendPushWithCustomConfig_android(userids, content,1,hq);
				}
        	}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
   
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteQuestion(Long id,String token,HttpServletRequest request, Long projectId) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        DataWrapper<List<QuestionFile>> questionFileList=new DataWrapper<List<QuestionFile>>();
        DataWrapper<List<Message>> messageList=new DataWrapper<List<Message>>();
        DataWrapper<List<MessageFile>> messageListFile=new DataWrapper<List<MessageFile>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			User userInDB = userDao.getById(userInMemory.getId());
			if (userInDB != null) {
				if(userInDB.getUserType()==UserTypeEnum.Admin.getType()){
					if(id!=null){
						/////删除问题所对应的问题文件
						questionFileList=questionFileDao.getQuestionFileByQuestionId(id);
						if(questionFileList.getData()!=null && questionFileList.getData().size()>0){
							if(!questionFileDao.deleteQuestionFileByQuestionId(id)){
								dataWrapper.setErrorCode(ErrorCodeEnum.Error);
							}
							for(int i=0;i<questionFileList.getData().size();i++){
								Files files=new Files();
								files=fileDao.getById(questionFileList.getData().get(i).getFileId());
								if(files!=null){
									fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
									fileDao.deleteFiles(files.getId());
								}
							}
							
						}
						
						////////删除问题所对应的留言
						messageList=messageDao.getMessageListByQuestionId(id);
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
								messageDao.deleteMessageByQuestionId(messageList.getData().get(j).getAboutId());
							}
							
						}
						/////最后删除问题自身
						if(!questionDao.deleteQuestion(id,projectId)){
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
    public DataWrapper<Void> deleteQuestion(Long id,String token,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        DataWrapper<List<QuestionFile>> questionFileList=new DataWrapper<List<QuestionFile>>();
        DataWrapper<List<Message>> messageList=new DataWrapper<List<Message>>();
        DataWrapper<List<MessageFile>> messageListFile=new DataWrapper<List<MessageFile>>();
        if (userInMemory != null) {
			User userInDB = userDao.getById(userInMemory.getId());
			if (userInDB != null) {
					if(id!=null){
						/////删除问题所对应的问题文件
						questionFileList=questionFileDao.getQuestionFileByQuestionId(id);
						if(questionFileList.getData()!=null && questionFileList.getData().size()>0){
							questionFileDao.deleteQuestionFileByQuestionId(id);
							for(int i=0;i<questionFileList.getData().size();i++){
								Files files=new Files();
								files=fileDao.getById(questionFileList.getData().get(i).getFileId());
								if(files!=null){
									fileDao.deleteFiles(files.getId());
									fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
									
								}
							}
							
						}
						
						////////删除问题所对应的留言
						messageList=messageDao.getMessageListByQuestionId(id);
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
								messageDao.deleteMessageByQuestionId(messageList.getData().get(j).getAboutId());
							}
							
						}
						/////最后删除问题自身
						if(questionDao.getById(id)!=null){
							questionDao.deleteQuestion(id);
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
    public DataWrapper<Void> updateQuestion(QuestionPojo question,String token,MultipartFile[] file,HttpServletRequest request,MultipartFile[] voices) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			User userInDB = userDao.getById(userInMemory.getId());
			if (userInDB != null) {
				
					if(question!=null){
						Question questionOld=new Question();
						questionOld=questionDao.getById(question.getId());
						if(questionOld!=null) 
						{
							if(file.length>0){
								for(int i=0;i<file.length;i++){
									String path=filePath+"/"+"questions";
								 	Files newfile=fileService.uploadFile(path, file[i],fileType,request);
								 	QuestionFile questionFile=new QuestionFile();
								 	questionFile.setQuestionId(question.getId());
								 	questionFile.setFileId(newfile.getId());
								 	questionFileDao.addQuestionFile(questionFile);
								}
							}
							if(voices.length>0){
								for(int j=0;j<voices.length;j++){
									String path=filePath+"/"+"questions";
								 	Files newfile=fileService.uploadFile(path, file[j],fileType,request);
								 	QuestionFile questionFile=new QuestionFile();
								 	questionFile.setQuestionId(question.getId());
								 	questionFile.setFileId(newfile.getId());
								 	questionFileDao.addQuestionFile(questionFile);
								}
							}
							questionOld.setIntro(question.getIntro());
							questionOld.setName(question.getName());
							questionOld.setState(question.getState());
							questionOld.setTrades(question.getTrades());
							questionDao.updateQuestion(questionOld);
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
    public DataWrapper<List<QuestionPojo>> getQuestionList(String content,Long projectId,String token, Integer pageIndex, Integer pageSize, Question question) {
    	DataWrapper<List<QuestionPojo>> datawrapper=new DataWrapper<List<QuestionPojo>>();
    	List<QuestionPojo> pojo = new ArrayList<QuestionPojo>();
    	DataWrapper<List<Question>> dataWrappers = new DataWrapper<List<Question>>();
    	DataWrapper<List<QuestionFile>> dataWrapperFiles = new DataWrapper<List<QuestionFile>>();
    	
    	User userInMemory=SessionManager.getSession(token);
    	if(userInMemory!=null){
    		Long[] userIdList=null;
			if(content!=null){
				//////问题类型搜索
				
				/////问题状态搜索
				if("待解决".contains(content)){
					question.setState(0);
				}
				if("已解决".contains(content)){
					question.setState(1);
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
			/////问题等级搜索
			/////当用户是总经理级别的时候,问题搜索只能搜索重要和紧急，同时也只能看重要和紧急问题，不看一般问题 
			String projectList=null;
			if(question.getId()!=null){
			////////////更新消息为已读
				Notice notice = new Notice();
				notice=noticeDao.getByAdoutIdAndUserId(userInMemory.getId(),question.getId(),1);
				if(notice!=null){
					if(notice.getReadState()!=1){
						notice.setReadState(1);
						notice.setUpdateDate(new Date());
						noticeDao.updateNotice(notice);
					}
				}
			}
			/////////
			dataWrappers= questionDao.getQuestionList(content,projectId,pageIndex,pageSize,question,userIdList,projectList);
			if(dataWrappers.getData()!=null && dataWrappers.getData().size()>0){
				for(int i=0;i<dataWrappers.getData().size();i++)
		        {
		        	QuestionPojo questionpojo=new QuestionPojo();
	        		dataWrapperFiles=questionFileDao.getQuestionFileByQuestionId(dataWrappers.getData().get(i).getId());
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
		        			questionpojo.setFileList(fileLists);
		        			questionpojo.setImageUrlList(imageList);
		        			questionpojo.setVoiceUrlList(voiceList);
		        		}				
			        }
		        	if(dataWrappers.getData().get(i).getUserList()!=null && !dataWrappers.getData().get(i).getUserList().equals("")){
		        		String[] nameList = dataWrappers.getData().get(i).getUserList().split(",");
		        		if(nameList.length>0){
		        			List<String> nameLists =new ArrayList<String>();
		        			for(int k=0;k<nameList.length;k++){
		        				User users = userDao.getById(Long.valueOf(nameList[k]));
		        				if(users!=null){
		        					 nameLists.add(users.getRealName());
		        				}
		        			  
		        			}
		        			questionpojo.setUserNameLists(nameLists);
			        	}else{
			        		questionpojo.setUserNameLists(null);
			        	}
		        	}
		        	String username=null;
		        	if(userDao.getById(dataWrappers.getData().get(i).getUserId())!=null){
		        		username=userDao.getById(dataWrappers.getData().get(i).getUserId()).getRealName();
		        		
		        	}
		        	if(username!=null){
		        		questionpojo.setUserId(username);
		        	}
		        	questionpojo.setModelFlag(dataWrappers.getData().get(i).getModelFlag());
		        	questionpojo.setCodeInformation(dataWrappers.getData().get(i).getCodeInformation());
		        	questionpojo.setPriority(dataWrappers.getData().get(i).getPriority());
		        	questionpojo.setId(dataWrappers.getData().get(i).getId());
		        	questionpojo.setIntro(dataWrappers.getData().get(i).getIntro());
		        	questionpojo.setName(dataWrappers.getData().get(i).getName());
		        	questionpojo.setProjectId(dataWrappers.getData().get(i).getProjectId());
		        	questionpojo.setProjectName(projectDao.getById(dataWrappers.getData().get(i).getProjectId()).getName());
		        	questionpojo.setPosition(dataWrappers.getData().get(i).getPosition());
		        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		        	questionpojo.setQuestionDate(sdf.format(dataWrappers.getData().get(i).getQuestionDate()));
		        	questionpojo.setState(dataWrappers.getData().get(i).getState());
		        	questionpojo.setTrades(dataWrappers.getData().get(i).getTrades());    
		        	if(dataWrappers.getData().get(i).getUserId()!=null){
		        		User users = userDao.getById(dataWrappers.getData().get(i).getUserId());
		        		if(users!=null){
		        			if(users.getUserIconUrl()!=null){
		        				questionpojo.setCreateUserIcon(users.getUserIconUrl());
		        			}else{
		        				Files files = fileService.getById(users.getUserIcon());
		        				questionpojo.setCreateUserIcon(files.getUrl());
		        			}
		        			
		        		}
		        	}
		        	pojo.add(i, questionpojo);
		        }
			}
			
			if(pojo.size()>0){
				datawrapper.setData(pojo);
				datawrapper.setCurrentPage(dataWrappers.getCurrentPage());
				datawrapper.setCallStatus(dataWrappers.getCallStatus());
				datawrapper.setNumberPerPage(dataWrappers.getNumberPerPage());
				datawrapper.setTotalNumber(dataWrappers.getTotalNumber());
				datawrapper.setTotalPage(dataWrappers.getTotalPage());
				Long questionSort=questionDao.getQuestionListOfSort();
				Long questionImportant=questionDao.getQuestionListOfImportant();
				Long questionAll=questionDao.getQuestionList();
				NumberFormat nf = NumberFormat.getNumberInstance();   
		        nf.setMaximumFractionDigits(2);   
		    	double sortPercent=( (double)questionSort/(double)questionAll);
		    	sortPercent=sortPercent*100;
		    	if((sortPercent+0.5)>Math.ceil(sortPercent))
		    	{
		    		sortPercent=Math.ceil(sortPercent);
		    	}else{
		    		sortPercent=Math.floor(sortPercent);
		    	}
		    	
		    	double importantPercent=((double)questionImportant/(double)questionAll);
		    	importantPercent=importantPercent*100;
		    	if((importantPercent+0.5)>Math.ceil(importantPercent))
		    	{
		    		importantPercent=Math.ceil(importantPercent);
		    	}else{
		    		importantPercent=Math.floor(importantPercent);
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
	       	List<QuestionPojo> pas= new ArrayList<QuestionPojo>();
	       	datawrapper.setData(pas);
	    }
    	return datawrapper;
    }

	@Override
	public DataWrapper<QuestionPojo> getQuestionDetailsByAdmin(Long questionId, String token,String weixin) {
		DataWrapper<QuestionPojo> dataWrapper = new DataWrapper<QuestionPojo>();
		if(weixin!=null){
			if(weixin.equals("weixin")){
				 Question question=questionDao.getById(questionId);
					if(question!=null){
						QuestionPojo questionPojo=new QuestionPojo();
						questionPojo.setId(question.getId());
						questionPojo.setCodeInformation(question.getCodeInformation());
						questionPojo.setIntro(question.getIntro());
						questionPojo.setName(question.getName());
						questionPojo.setPriority(question.getPriority());
						questionPojo.setProjectId(question.getProjectId());
						Project test = new Project();
						test=projectDao.getById(question.getProjectId());
						if(test!=null){
							questionPojo.setProjectName(test.getName());
						}
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						questionPojo.setQuestionDate(sdf.format(question.getQuestionDate()));
						questionPojo.setState(question.getState());
						questionPojo.setModelFlag(question.getModelFlag());
						questionPojo.setTrades(question.getTrades());
						
						String userlist="";
						if(question.getUserList()!=null){
							for(int s=0;s<question.getUserList().split(",").length;s++){
								if(s==0){
									userlist=userDao.getById(Long.valueOf(question.getUserList().split(",")[s])).getRealName();
								}else{
									userlist=userlist+","+userDao.getById(Long.valueOf(question.getUserList().split(",")[s])).getRealName();
								}
							}
						}
						questionPojo.setUserList(userlist);
						questionPojo.setUserId(userDao.getById(question.getUserId()).getRealName());
						DataWrapper<List<QuestionFile>> file=new DataWrapper<List<QuestionFile>>();
						file=questionFileDao.getQuestionFileByQuestionId(question.getId());
						if(file.getData()!=null && file.getData().size()>0){
							String[] fileList=new String[file.getData().size()];
							for(int i=0;i<file.getData().size();i++){
								Files files=new Files();
								files=fileDao.getById(file.getData().get(i).getFileId());
								if(files!=null){
									fileList[i]=files.getUrl();
								}
							}
							questionPojo.setFileList(fileList);
						}
						
						dataWrapper.setData(questionPojo);
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
				if(questionId!=null){
					Question question=questionDao.getById(questionId);
					if(question==null){
						dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
					}
					if(question!=null){
						if(userInDB.getUserType()==1 || userInDB.getUserType()==0 || userInDB.getUserType()==3){
							QuestionPojo questionPojo=new QuestionPojo();
							questionPojo.setId(question.getId());
							questionPojo.setCodeInformation(question.getCodeInformation());
							questionPojo.setIntro(question.getIntro());
							questionPojo.setName(question.getName());
							questionPojo.setPriority(question.getPriority());
							questionPojo.setProjectId(question.getProjectId());
							if(question.getProjectId()!=null){
								Project projects= new Project();
								projects=projectDao.getById(question.getProjectId());
								if(projects!=null){
									questionPojo.setProjectName(projects.getName());
									if(projects.getPicId()!=null && !projects.getPicId().equals(""))
									{
										Files files=new Files();
										files=fileDao.getById(Long.valueOf(projects.getPicId()));
										if(files!=null){
											questionPojo.setProjectPicUrl(files.getUrl());
										}
									}
									
								}
							}
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
							questionPojo.setQuestionDate(sdf.format(question.getQuestionDate()));
							questionPojo.setState(question.getState());
							questionPojo.setTrades(question.getTrades());
							if(userInDB.getUserIconUrl()!=null){
								questionPojo.setCreateUserIcon(userInDB.getUserIconUrl());
							}else if(userInDB.getUserIcon()!=null){
								Files file = fileService.getById(userInDB.getUserIcon());
								if(file!=null){
									questionPojo.setCreateUserIcon(file.getUrl());
								}
							}
							questionPojo.setUserId(userDao.getById(question.getUserId()).getRealName());
							/*if(userIdis==userInMemory.getId()){
								questionPojo.setUserid(1);
							}else{
								questionPojo.setUserid(0);
							}*/
							DataWrapper<List<QuestionFile>> file=new DataWrapper<List<QuestionFile>>();
							file=questionFileDao.getQuestionFileByQuestionId(question.getId());
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
				        			questionPojo.setImageUrlList(imageList);
				        			questionPojo.setVoiceUrlList(voiceList);
				        		}			
								questionPojo.setFileList(fileList);
							}
							if(questionPojo!=null){
								Notice notice = new Notice();
								notice=noticeDao.getByAdoutIdAndUserId(userInDB.getId(),questionId,1);
								if(notice!=null){
									if(notice.getReadState()!=1){
										notice.setReadState(1);
										notice.setUpdateDate(new Date());
										noticeDao.updateNotice(notice);
									}
								}
							}
							dataWrapper.setData(questionPojo);
						}else{
							dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
						}
						
					}
				}
			} 
		} 
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<Question>> getQuestionsByLike(String content, String token) {
		DataWrapper<List<Question>> dataWrapper= new DataWrapper<List<Question>>();
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			dataWrapper=questionDao.getQuestionListByLike(content);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return null;
	}

	@Override
	public DataWrapper<Void> updateQuestionState(Long questionId, String token, Integer state) {
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				Question question=null;
				question=questionDao.getById(questionId);	
					if(question!=null){
						
						/////安全问题状态更新，消息提醒存储
						Notice notice = new Notice();
						notice.setAboutId(question.getId());
						notice.setNoticeType(1);
						notice.setCreateDate(new Date());
						notice.setRemark("finished");
						notice.setUserId(question.getUserId());
						notice.setProjectId(question.getProjectId());
						Integer statse=0;
						notice.setReadState(statse);
						noticeDao.addNotice(notice);
						if(question.getUserId().equals(userInMemory.getId())){
							question.setState(state);
							if(!questionDao.updateQuestion(question)){
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
	public DataWrapper<List<QuestionPojo>> getQuestionListByUserId(String token, Integer pageIndex, Integer pageSize) {
		DataWrapper<List<QuestionPojo>> datawrapper=new DataWrapper<List<QuestionPojo>>();
    	User userInMemory=SessionManager.getSession(token);
    	Question question=new Question();
    	if(userInMemory!=null){
    			question.setUserId(userInMemory.getId());
    			datawrapper= questionDao.getQuestionList(pageIndex,pageSize,question);
    			Long questionSort=questionDao.getQuestionListOfSort();
    			Long questionImportant=questionDao.getQuestionListOfImportant();
    			Long questionAll=questionDao.getQuestionList();
    			NumberFormat nf = NumberFormat.getNumberInstance();   
    	        nf.setMaximumFractionDigits(2);   
    	    	double sortPercent=( (double)questionSort/(double)questionAll);
    	    	sortPercent=sortPercent*100;
    	    	if((sortPercent+0.5)>Math.ceil(sortPercent))
    	    	{
    	    		sortPercent=Math.ceil(sortPercent);
    	    	}else{
    	    		sortPercent=Math.floor(sortPercent);
    	    	}
    	    	
    	    	double importantPercent=((double)questionImportant/(double)questionAll);
    	    	importantPercent=importantPercent*100;
    	    	if((importantPercent+0.5)>Math.ceil(importantPercent))
    	    	{
    	    		importantPercent=Math.ceil(importantPercent);
    	    	}else{
    	    		importantPercent=Math.floor(importantPercent);
    	    	}
    	    	if(datawrapper.getData()==null || datawrapper.getData().size()<=0){
    	    		List<QuestionPojo> questionPojolist=new ArrayList<QuestionPojo>();
    	    		QuestionPojo questionPojo=new QuestionPojo();
    	    		questionPojo.setSortPercent((int)sortPercent);
    	    		questionPojo.setImportantPercent((int)importantPercent);
    	    		questionPojo.setUrgentPercent(100-(int)sortPercent-(int)importantPercent);
    	    		questionPojolist.add(questionPojo);
    	    		datawrapper.setData(questionPojolist);
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
	public DataWrappern< PageInfo,List<QuestionPojo>,HashMap<String,String>> getQuestionHash(Integer searchType,String content, Long projectId, String token,
			Integer pageIndex, Integer pageSize, Question question) {
		DataWrappern<PageInfo,List<QuestionPojo>, HashMap<String, String>> datawrapper=new DataWrappern<PageInfo,List<QuestionPojo>,HashMap<String, String>>();
    	List<QuestionPojo> pojo = new ArrayList<QuestionPojo>();
		PageInfo pageInfos = new PageInfo(); 
    	DataWrapper<List<Question>> dataWrappers = new DataWrapper<List<Question>>();
    	DataWrapper<List<QuestionFile>> dataWrapperFiles = new DataWrapper<List<QuestionFile>>();
    	User userInMemory=SessionManager.getSession(token);
    	if(userInMemory!=null){
    		Long[] userIdList=null;
			
			/////问题等级搜索
			/////当用户是总经理级别的时候,问题搜索只能搜索重要和紧急，同时也只能看重要和紧急问题，不看一般问题 
    		if(searchType!=null){
    			if(searchType==0){
    				User users = userDao.getByUserRealName(content);
    				if(users!=null){
    					content=users.getId().toString();
    				}
    			}
    		}
    		if(question.getId()!=null){
				////////////更新消息为已读
				Notice notice = new Notice();
				notice=noticeDao.getByAdoutIdAndUserId(userInMemory.getId(),question.getId(),1);
				if(notice!=null){
					if(notice.getReadState()!=1){
						notice.setReadState(1);
						notice.setUpdateDate(new Date());
						noticeDao.updateNotice(notice);
					}
				}
			}
			dataWrappers= questionDao.getQuestionLists(searchType,content,projectId,pageIndex,pageSize,question,userIdList);
			
			for(int i=0;i<dataWrappers.getData().size();i++)
	        {
	        	QuestionPojo questionpojo=new QuestionPojo();
	        	
        		dataWrapperFiles=questionFileDao.getQuestionFileByQuestionId(dataWrappers.getData().get(i).getId());
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
	        			questionpojo.setFileList(fileLists);
	        		}				
		        }
	        	if(dataWrappers.getData().get(i).getUserList()!=null){
	        		String[] nameList = dataWrappers.getData().get(i).getUserList().split(",");
	        		if(nameList.length>0){
	        			List<String> nameLists =new ArrayList<String>();
	        			for(int k=0;k<nameList.length;k++){
	        				User users = userDao.getById(Long.valueOf(nameList[k]));
	        				if(users!=null){
	        					nameLists.add(users.getRealName());
	        				}
	        			}
	        			questionpojo.setUserNameLists(nameLists);
		        	}else{
		        		questionpojo.setUserNameLists(null);
		        	}
	        	}
	        	User users=userDao.getById(dataWrappers.getData().get(i).getUserId());
	        	if(users!=null){
	        		String username=users.getRealName();
	        		questionpojo.setUserId(username);
	        		if(users.getUserIcon()!=null){
	        			Files files = fileService.getById(users.getUserIcon());
	        			if(files!=null){
	        				questionpojo.setCreateUserIcon(files.getUrl());
	        			}
	        		}else{
	        			questionpojo.setCreateUserIcon(users.getUserIconUrl());
	        		}
	        	}
	        	questionpojo.setModelFlag(dataWrappers.getData().get(i).getModelFlag());
	        	questionpojo.setCodeInformation(dataWrappers.getData().get(i).getCodeInformation());
	        	questionpojo.setPriority(dataWrappers.getData().get(i).getPriority());
	        	questionpojo.setId(dataWrappers.getData().get(i).getId());
	        	questionpojo.setIntro(dataWrappers.getData().get(i).getIntro());
	        	questionpojo.setName(dataWrappers.getData().get(i).getName());
	        	questionpojo.setProjectId(dataWrappers.getData().get(i).getProjectId());
	        	questionpojo.setProjectName(projectDao.getById(dataWrappers.getData().get(i).getProjectId()).getName());
	        	questionpojo.setPosition(dataWrappers.getData().get(i).getPosition());
	        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        	questionpojo.setQuestionDate(sdf.format(dataWrappers.getData().get(i).getQuestionDate()));
	        	questionpojo.setState(dataWrappers.getData().get(i).getState());
	        	questionpojo.setTrades(dataWrappers.getData().get(i).getTrades());  
	        	questionpojo.setUserid(dataWrappers.getData().get(i).getUserId());
	        	int messageNum=0;
	        	List<Message> mes = messageDao.getMessageListByQuestionId(dataWrappers.getData().get(i).getId()).getData();
	        	if(mes!=null){
	        		messageNum = mes.size();
	        	}
	        	
	        	questionpojo.setMessageNum(messageNum);
	        	pojo.add(i, questionpojo);
	        }
			if(pojo.size()>0){
				 
				pageInfos.setCurrentPage(dataWrappers.getCurrentPage());
				pageInfos.setNumberPerPage(dataWrappers.getNumberPerPage());
				pageInfos.setTotalNumber(dataWrappers.getTotalNumber());
				pageInfos.setTotalPage(dataWrappers.getTotalPage());
		
				Long questionSort=questionDao.getQuestionListOfSort();
				Long questionImportant=questionDao.getQuestionListOfImportant();
				Long questionAll=questionDao.getQuestionList();
				NumberFormat nf = NumberFormat.getNumberInstance();   
		        nf.setMaximumFractionDigits(2);   
		    	double sortPercent=( (double)questionSort/(double)questionAll);
		    	sortPercent=sortPercent*100;
		    	if((sortPercent+0.5)>Math.ceil(sortPercent))
		    	{
		    		sortPercent=Math.ceil(sortPercent);
		    	}else{
		    		sortPercent=Math.floor(sortPercent);
		    	}
		    	
		    	double importantPercent=((double)questionImportant/(double)questionAll);
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
	
}
