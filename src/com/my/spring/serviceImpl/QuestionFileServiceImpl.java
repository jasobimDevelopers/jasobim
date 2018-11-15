package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.QuestionFileDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.QuestionFile;
import com.my.spring.model.User;
import com.my.spring.service.FileService;
import com.my.spring.service.QuestionFileService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service("questionFileService")
public class QuestionFileServiceImpl implements QuestionFileService {
    @Autowired
    QuestionFileDao questionFileDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    FileService fileService;
    private String filePath = "/files";
    private Integer fileType=2;
    @Override
    public DataWrapper<Void> addQuestionFile(QuestionFile questionFile,String token,MultipartFile file,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory=SessionManager.getSession(token);
        if(userInMemory!=null){
        	 if(!questionFileDao.addQuestionFile(questionFile)) {
        		 String path=filePath+"/"+"questions";
				 Files newfile=fileService.uploadFile(path, file,fileType,request);
				 questionFile.setFileId(newfile.getId());
                 dataWrapper.setErrorCode(ErrorCodeEnum.Error);
             }
        }else{
        	dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteQuestionFile(Long id,String token,Long fileid,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory=SessionManager.getSession(token);
        if(userInMemory!=null){
        	if(!questionFileDao.deleteQuestionFile(id)) {
        		fileDao.deleteFiles(fileid);//删除文件表的信息
				Files files=fileDao.getById(id);
				fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
                dataWrapper.setErrorCode(ErrorCodeEnum.Error);
            }
        }else{
        	dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
        }
        return dataWrapper;
    }


    @Override
    public DataWrapper<List<QuestionFile>> getQuestionFileList(String token) {
    	DataWrapper<List<QuestionFile>> dataWrapper=new DataWrapper<List<QuestionFile>>();
    	User userInMemory=SessionManager.getSession(token);
    	if(userInMemory!=null){
    		 return questionFileDao.getQuestionFileList();
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
       return dataWrapper;
    }

	@Override
	public DataWrapper<List<QuestionFile>> getQuestionFileListByUserId(Long userId,String token) {
		DataWrapper<List<QuestionFile>> dataWrapper=new DataWrapper<List<QuestionFile>>();
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			return questionFileDao.getQuestionFileListByUserId(userId);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

}
