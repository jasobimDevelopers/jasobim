package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.MessageDao;
import com.my.spring.DAO.MessageFileDao;
import com.my.spring.DAO.QuestionDao;
import com.my.spring.DAO.QuestionFileDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Message;
import com.my.spring.model.MessageFile;
import com.my.spring.model.Question;
import com.my.spring.model.QuestionFile;
import com.my.spring.model.QuestionPojo;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.service.FileService;
import com.my.spring.service.QuestionService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("questionService")
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    QuestionFileDao questionFileDao;
    @Autowired
    UserDao userDao;
    @Autowired
    MessageDao messageDao;
    @Autowired
    MessageFileDao messageFileDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    FileService fileService;
    @Autowired
    UserLogService userLogService;
    private String filePath = "files";
    private Integer fileType=2;
    @Override
    public DataWrapper<Void> addQuestion(Question question,String token,MultipartFile[] fileList,HttpServletRequest request,MultipartFile fileCode,MultipartFile[] voices) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	question.setUserId(userInMemory.getId());
        	if(question.getQuestionDate()==null){
				question.setQuestionDate(new Date());
			}
        	if(question.getState()==null){
        		question.setState(0);
        	}
			if(question!=null){
				if(fileCode!=null){
					String path=filePath+"/"+"questions";
				 	Files newfile=fileService.uploadFile(path, fileCode,fileType,request);
				 	question.setCodeInformation(newfile.getUrl());
				}
				if(!questionDao.addQuestion(question)){ 
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					return dataWrapper;
				}else{ 
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
							questionFile.setOriginName(originName);
						 	questionFileDao.addQuestionFile(questionFile);
						}
							
					}
					if(voices.length>0){
						for(int s=0;s<voices.length;s++){
							
							String path=filePath+"/"+"questions";
						 	Files newfile=fileService.uploadFile(path, fileList[s],fileType,request);
						 	QuestionFile questionFiles=new QuestionFile();
						 	questionFiles.setQuestionId(question.getId());
						 	questionFiles.setFileId(newfile.getId());
						 	String originName = fileList[s].getOriginalFilename();
							if (originName.contains(".")) {
								originName = originName.substring(0, originName.lastIndexOf("."));
							}
							questionFiles.setOriginName(originName);
						 	questionFileDao.addQuestionFile(questionFiles);
						}
					}
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
								messageDao.deleteMessageByQuestionId(messageList.getData().get(j).getQuestionId());
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
				if(userInDB.getUserType()==UserTypeEnum.Admin.getType()){
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
								messageDao.deleteMessageByQuestionId(messageList.getData().get(j).getQuestionId());
							}
							
						}
						/////最后删除问题自身
						if(questionDao.getById(id)!=null){
							questionDao.deleteQuestion(id);
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
							questionOld.setQuestionType(question.getQuestionType());
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
				if(question.getId()!=null){
	        		UserLog userLog = new UserLog();
	        		userLog.setActionDate(new Date());
	        		userLog.setFileId(question.getId());
	        		userLog.setSystemType(userInMemory.getSystemId());
	        		userLog.setProjectId((long)-1);
	        		userLog.setProjectPart(5);
	        		userLog.setUserId(userInMemory.getId());
	        		userLog.setVersion("-1");
	        		userLogService.addUserLog(userLog, token);
	        	}
				//////问题类型搜索
				if("安全".contains(content))
				{
					question.setQuestionType(0);
				}
				if("质量".contains(content))
				{
					question.setQuestionType(1);
				}
				if("其他".contains(content))
				{
					question.setQuestionType(2);
				}
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
			if(userInMemory.getWorkName()!=null){
				if(userInMemory.getWorkName().equals("总经理"))
				{
					if(question.getPriority()==null)
						question.setPriority(-1);
				}else{
					if(content!=null){
						if("一般".contains(content))
							question.setPriority(0);
					}
				}
				/////当用户是投资方（甲方）的时候，问题搜索只能搜索一般问题，同时也只能看一般问题，不能重要和紧急问题
				if(userInMemory.getWorkName().equals("投资方"))
				{
					if(question.getPriority()==null)
						question.setPriority(0);
				}else{
					if(content!=null){
						if("重要".contains(content))
							question.setPriority(1);
						if("紧急".contains(content))
							question.setPriority(2);
					}
				}
			}
			String projectList=null;
			if(userInMemory.getUserType()==2 || userInMemory.getUserType()==3){
				projectList=userInMemory.getProjectList();
			}
			dataWrappers= questionDao.getQuestionList(content,projectId,pageIndex,pageSize,question,userIdList,projectList);
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
	        			String[] nameLists =new String[nameList.length] ;
	        			for(int k=0;k<nameList.length;k++){
	        			   nameLists[k]=userDao.getById(Long.valueOf(nameList[k])).getRealName();
	        			}
	        			questionpojo.setUserNameLists(nameLists);
		        	}else{
		        		questionpojo.setUserNameLists(null);
		        	}
	        	}
	        	String username=userDao.getById(dataWrappers.getData().get(i).getUserId()).getRealName();
	        	questionpojo.setUserId(username);
	        	questionpojo.setCodeInformation(dataWrappers.getData().get(i).getCodeInformation());
	        	questionpojo.setPriority(dataWrappers.getData().get(i).getPriority());
	        	questionpojo.setId(dataWrappers.getData().get(i).getId());
	        	questionpojo.setIntro(dataWrappers.getData().get(i).getIntro());
	        	questionpojo.setName(dataWrappers.getData().get(i).getName());
	        	questionpojo.setProjectId(projectId);
	        	questionpojo.setPosition(dataWrappers.getData().get(i).getPosition());
	        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        	questionpojo.setQuestionDate(sdf.format(dataWrappers.getData().get(i).getQuestionDate()));
	        	questionpojo.setQuestionType(dataWrappers.getData().get(i).getQuestionType());
	        	questionpojo.setState(dataWrappers.getData().get(i).getState());
	        	questionpojo.setTrades(dataWrappers.getData().get(i).getTrades());        	        	
	        	pojo.add(i, questionpojo);
	        }
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
	    		if(userInMemory.getWorkName()!=null){
	    			if(userInMemory.getWorkName().equals("总经理")){
						datawrapper.getData().get(0).setRoleFlag(1);
					}else if(userInMemory.getWorkName().equals("投资方")){
						datawrapper.getData().get(0).setRoleFlag(2);
					}else{
						datawrapper.getData().get(0).setRoleFlag(0);
					}
	    		}
			}
	    	if(datawrapper.getData()!=null || datawrapper.getData().size()>0){
	    		datawrapper.getData().get(0).setImportantPercent((int)importantPercent);
    	    	datawrapper.getData().get(0).setUrgentPercent(100-(int)sortPercent-(int)importantPercent);
	    		datawrapper.getData().get(0).setSortPercent((int)sortPercent);
	    	}
    	}else{
    		datawrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
    	return datawrapper;
    }

	@Override
	public DataWrapper<QuestionPojo> getQuestionDetailsByAdmin(Long questionId, String token) {
		DataWrapper<QuestionPojo> dataWrapper = new DataWrapper<QuestionPojo>();
		 User userInMemory = SessionManager.getSession(token);
	        if (userInMemory != null) {
				User userInDB = userDao.getById(userInMemory.getId());
				if (userInDB != null) {
					if(questionId!=null){
						Question question=questionDao.getById(questionId);
						if(question==null){
							 dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
						} 
						else{
							QuestionPojo questionPojo=new QuestionPojo();
							
							questionPojo.setId(question.getId());
							questionPojo.setCodeInformation(question.getCodeInformation());
							questionPojo.setIntro(question.getIntro());
							questionPojo.setName(question.getName());
							questionPojo.setPriority(question.getPriority());
							questionPojo.setProjectId(question.getProjectId());
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
							questionPojo.setQuestionDate(sdf.format(question.getQuestionDate()));
							questionPojo.setQuestionType(question.getQuestionType());
							questionPojo.setState(question.getState());
							questionPojo.setTrades(question.getTrades());
							questionPojo.setUserId(userDao.getById(question.getUserId()).getRealName());
							Long userIdis=userDao.getById(question.getUserId()).getId();
							if(userIdis==userInMemory.getId()){
								questionPojo.setUserid(1);
							}else{
								questionPojo.setUserid(0);
							}
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
			User userInDB = userDao.getById(userInMemory.getId());
			if (userInDB != null) {
				Question question=null;
				question=questionDao.getById(questionId);	
				if(question!=null){
					question.setState(state);
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
				}
				if(!questionDao.updateQuestion(question)){
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
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
}
