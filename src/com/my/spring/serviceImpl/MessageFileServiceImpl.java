package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.MessageFileDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.MessageFile;
import com.my.spring.model.MessageFilePojo;
import com.my.spring.model.User;
import com.my.spring.service.FileService;
import com.my.spring.service.MessageFileService;
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
@Service("messageFileService")
public class MessageFileServiceImpl implements MessageFileService {
    @Autowired
    MessageFileDao messageFileDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    FileService fileService;
    private String filePath = "/files";
    private Integer fileType=3;
    @Override
    public DataWrapper<MessageFilePojo> addMessageFile(MessageFile messageFile,String token,MultipartFile file,HttpServletRequest request) {
    	DataWrapper<MessageFile> dataWrapper = new DataWrapper<MessageFile>();
    	DataWrapper<MessageFilePojo> dataWrappers = new DataWrapper<MessageFilePojo>();
        User userInMemory = SessionManager.getSession(token);////验证登录时的session
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				///////验证是不是管理员身份
				if(messageFile!=null){////验证上传的实体类是不是为空
					///////1.文件的上传返回url
					if(file!=null){
						MessageFilePojo messageFilePojo=new MessageFilePojo();
						String path=filePath+"/"+"messages";
						Files newfile=fileService.uploadFile(path, file,fileType,request);
						messageFile.setFileId(newfile.getId());
						if(!messageFileDao.addMessageFile(messageFile)) 
				            dataWrappers.setErrorCode(ErrorCodeEnum.Error);
						else{
							messageFilePojo.setFileId(messageFile.getFileId());
							messageFilePojo.setId(messageFile.getId());
							messageFilePojo.setMessageId(messageFile.getMessageId());
							messageFilePojo.setOriginName(messageFile.getOriginName());
							messageFilePojo.setUrlList(newfile.getUrl());
							dataWrappers.setData(messageFilePojo);
						}
					}
					
					
			        
				}else{
					dataWrappers.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
			}else{
				dataWrappers.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
		} else {
			dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrappers;
    }

    @Override
    public DataWrapper<Void> deleteMessageFile(Long id,Long fileid,String token,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory=SessionManager.getSession(token);
        if(userInMemory!=null){
        	if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				///////验证是不是管理员身份
				if(id!=null){////验证上传的实体类是不是为空
					if(!messageFileDao.deleteMessageFile(id)) {
						fileDao.deleteFiles(fileid);//删除文件表的信息
						Files files=fileDao.getById(id);
						fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
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
    public DataWrapper<List<MessageFile>> getMessageFileList(String token ) {
    	DataWrapper<List<MessageFile>> dataWrapper = new DataWrapper<List<MessageFile>>();
    	User userInMemory=SessionManager.getSession(token);
        if(userInMemory!=null){
        	dataWrapper=messageFileDao.getMessageFileList();
        	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }else{
        	dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
        }
        return dataWrapper;
    }

	@Override
	public DataWrapper<List<MessageFile>> getMessageFileListByUserId(Long userId,String token) {
		// TODO Auto-generated method stub
		return messageFileDao.getMessageFileListByUserId(userId);
	}

}
