package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.MessageDao;
import com.my.spring.DAO.MessageFileDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Message;
import com.my.spring.model.MessageFile;
import com.my.spring.model.User;
import com.my.spring.service.FileService;
import com.my.spring.service.MessageService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDao messageDao;
    @Autowired
    MessageFileDao messageFileDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    FileService fileService;
    private String filePath = "/files";
    private Integer fileType=3;
    @Override
    public DataWrapper<Void> addMessage(Message message,String token,MultipartFile file,HttpServletRequest request) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(message!=null){
					if(!messageDao.addMessage(message)) 
					{
						if(file!=null){
							String path=filePath+"/"+"messages";
							Files newfile=fileService.uploadFile(path, file,fileType,request);
							MessageFile messageFile=new MessageFile ();
							messageFile.setFileId(newfile.getId());
							messageFile.setMessageId(message.getId());
							messageFileDao.addMessageFile(messageFile);
						}
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
					else
						return dataWrapper;
			        
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
    public DataWrapper<Void> deleteMessage(Long id,String token ,HttpServletRequest request) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					Message message=messageDao.getById(id);
					if(!messageDao.deleteMessage(id)) 
					{
						///////////////////////////////
						DataWrapper<List<MessageFile>> dataWrappers=messageFileDao.deleteMessageFileByMessageId(message.getId());
						for(MessageFile e:dataWrappers.getData()){
							fileDao.deleteFiles(e.getFileId());//删除文件表的信息
							Files files=fileDao.getById(e.getFileId());
							fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
						}
						
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
					else
						return dataWrapper;
			        
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

   /* @Override
    public DataWrapper<Void> updateMessage(Message message,String token) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(message!=null){
					if(!messageDao.updateMessage(message)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
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
    }*/

    @Override
    public DataWrapper<List<Message>> getMessageList(String token , Long projectId, Integer pageIndex, Integer pageSize, Message message) {
    	DataWrapper<List<Message>> dataWrapper = new DataWrapper<List<Message>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			
					dataWrapper=messageDao.getMessageList(projectId,pageIndex,pageSize,message);
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
        return dataWrapper;
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
}
