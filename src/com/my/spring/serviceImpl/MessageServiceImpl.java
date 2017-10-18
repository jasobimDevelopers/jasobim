package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.MessageDao;
import com.my.spring.DAO.MessageFileDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Message;
import com.my.spring.model.MessageFile;
import com.my.spring.model.MessagePojo;
import com.my.spring.model.User;
import com.my.spring.service.FileService;
import com.my.spring.service.MessageService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDao messageDao;
    @Autowired
    MessageFileDao messageFileDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileService fileService;
    private String filePath = "/files";
    private Integer fileType=3;
    @Override
    public DataWrapper<Void> addMessage(Message message,String token,MultipartFile[] file,HttpServletRequest request) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				if(message!=null){
					message.setUserId(userInMemory.getId());
					if(message.getMessageDate()==null){
						message.setMessageDate(new Date(System.currentTimeMillis()));
					}
					if(messageDao.addMessage(message)) 
					{
						if(file.length>0){
							for(int i=0;i<file.length;i++){
								String path=filePath+"/"+"messages";
								Files newfile=fileService.uploadFile(path, file[i],fileType,request);
								MessageFile messageFile=new MessageFile ();
								messageFile.setFileId(newfile.getId());
								String originName = file[i].getOriginalFilename();
								if (originName.contains(".")) {
									originName = originName.substring(0, originName.lastIndexOf("."));
								}
								messageFile.setOriginName(originName);
								messageFile.setMessageId(message.getId());
								messageFileDao.addMessageFile(messageFile);
							}
							
						}
			           
					}
					else
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);;
			        
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteMessage(Long id,String token ,HttpServletRequest request) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					Message message=messageDao.getById(id);
					if(messageFileDao.getMessageFileListByMessageId(message.getId()).getData()!=null 
							&& messageFileDao.getMessageFileListByMessageId(message.getId()).getData().size()>0){
						
						if(messageFileDao.deleteMessageFileByMessageIds(message.getId())){
							messageDao.deleteMessage(id);
						}
						
						/*for(MessageFile e:dataWrappers.getData()){
							fileDao.deleteFiles(e.getFileId());//删除文件表的信息
							Files files=fileDao.getById(e.getFileId());
							fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
						}*/
					}
					else{
						messageDao.deleteMessage(id);
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
    public DataWrapper<Void> updateMessage(Message message,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				message.setUserId(userInMemory.getId());
				if(!messageDao.updateMessage(message)) {
				    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
				else{
					return dataWrapper;
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
    public DataWrapper<List<MessagePojo>> getMessageList(String token , Integer pageIndex, Integer pageSize, Message message) {
    	DataWrapper<List<MessagePojo>> dataWrappers = new DataWrapper<List<MessagePojo>>();
    	DataWrapper<List<Message>> dataWrapper = new DataWrapper<List<Message>>();
    	DataWrapper<List<MessageFile>> dataWrapperfile=new DataWrapper<List<MessageFile>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				dataWrapper=messageDao.getMessageList(pageIndex,pageSize,message);
				if(dataWrapper.getData()!=null){
					
					List<MessagePojo> messagePojoList = new ArrayList<MessagePojo>();
					for(int i=0;i<dataWrapper.getData().size();i++){
						MessagePojo messagePojo =new MessagePojo();
						messagePojo.setContent(dataWrapper.getData().get(i).getContent());
						messagePojo.setMessageDate(dataWrapper.getData().get(i).getMessageDate());
						messagePojo.setQuestionId(dataWrapper.getData().get(i).getQuestionId());
						messagePojo.setId(dataWrapper.getData().get(i).getId());
						if(dataWrapper.getData().get(i).getUserId()!=null){
							messagePojo.setUserId(dataWrapper.getData().get(i).getUserId());
							User user= new User();
							user=userDao.getById(dataWrapper.getData().get(i).getUserId());
							if(user.getUserIcon()!=null){
								Files files=fileDao.getById(user.getUserIcon());
								if(files.getUrl()!=null){
									messagePojo.setUserIconUrl(files.getUrl());
								}
							}
							if(user!=null){
								messagePojo.setUserName(user.getUserName());
								messagePojo.setRealName(user.getRealName());
							}
							
						}
						dataWrapperfile=messageFileDao.getMessageFileListByMessageId(dataWrapper.getData().get(i).getId());
						if(dataWrapperfile.getData()!=null && dataWrapperfile.getData().size()>0){
							int length=dataWrapperfile.getData().size();
							String[] filenameList =new String[length];
							for(int j=0;j<dataWrapperfile.getData().size();j++){
								String filename=dataWrapperfile.getData().get(j).getOriginName();
								filenameList[j]=filename;
							}
							messagePojo.setFileNameList(filenameList);
						}
						if(messagePojo!=null){
							messagePojoList.add(messagePojo);
						}
					}
					if(messagePojoList!=null && messagePojoList.size()>0){
						dataWrappers.setData(messagePojoList);
						dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
						dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
						dataWrappers.setTotalPage(dataWrapper.getTotalPage());
						dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
					}else{
						dataWrappers.setErrorCode(ErrorCodeEnum.Error);
					}
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
        return dataWrappers;
    }

	@Override
	public DataWrapper<List<Message>> getMessageListByUserId(Long userId,String token) {
		DataWrapper<List<Message>> dataWrapper = new DataWrapper<List<Message>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userId!=null){
				if(messageDao.getMessageListByUserId(userId)==null) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else
					return messageDao.getMessageListByUserId(userId);
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
	}
	@Override
	public DataWrapper<List<MessagePojo>> getMessageListByQuestionId(Long questionId,String token) {
		DataWrapper<List<MessagePojo>> dataWrapper = new DataWrapper<List<MessagePojo>>();
		DataWrapper<List<Message>> dataWrappers = new DataWrapper<List<Message>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(questionId!=null)
			{
				dataWrappers=messageDao.getMessageListByQuestionId(questionId);
				if(dataWrappers.getData()==null || dataWrappers.getData().size()<=0) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else{
					List<MessagePojo> messagePojoList = new ArrayList<MessagePojo>();
					for(int i=0;i<dataWrappers.getData().size();i++)
					{
						MessagePojo messagePojo =new MessagePojo();
						messagePojo.setContent(dataWrappers.getData().get(i).getContent());
						messagePojo.setMessageDate(dataWrappers.getData().get(i).getMessageDate());
						messagePojo.setQuestionId(dataWrappers.getData().get(i).getQuestionId());
						messagePojo.setRealName(userDao.getById(dataWrappers.getData().get(i).getUserId()).getRealName());
						messagePojo.setId(dataWrappers.getData().get(i).getId());
						if(dataWrappers.getData().get(i).getUserId()!=null)
						{
							messagePojo.setUserId(dataWrappers.getData().get(i).getUserId());
							User user= new User();
							user=userDao.getById(dataWrappers.getData().get(i).getUserId());
							if(user.getUserIcon()!=null)
							{
								Files files=fileDao.getById(user.getUserIcon());
								if(files.getUrl()!=null)
								{
									messagePojo.setUserIconUrl(files.getUrl());
								}
							}
							if(user!=null)
							{
								messagePojo.setUserName(user.getUserName());
							}
							
						}
						if(messagePojo!=null)
						{
							messagePojoList.add(messagePojo);
						}
						if(messagePojoList!=null && messagePojoList.size()>0)
						{
							dataWrapper.setData(messagePojoList);
							dataWrapper.setTotalNumber(dataWrappers.getTotalNumber());
							dataWrapper.setCurrentPage(dataWrappers.getCurrentPage());
							dataWrapper.setTotalPage(dataWrappers.getTotalPage());
							dataWrapper.setNumberPerPage(dataWrappers.getNumberPerPage());
						}
						else
						{
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}
					}
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
	public DataWrapper<Message> getMessageListById(Long id, String token) {
		DataWrapper<Message> dataWrapper = new DataWrapper<Message>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(id!=null){
				if(messageDao.getMessageListById(id)==null) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else
					dataWrapper.setData(messageDao.getMessageListById(id));
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
	}
}
