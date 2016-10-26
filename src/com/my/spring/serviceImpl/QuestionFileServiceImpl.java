package com.my.spring.serviceImpl;

import com.my.spring.DAO.QuestionFileDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.QuestionFile;
import com.my.spring.model.User;
import com.my.spring.service.QuestionFileService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("questionFileService")
public class QuestionFileServiceImpl implements QuestionFileService {
    @Autowired
    QuestionFileDao questionFileDao;
    @Override
    public DataWrapper<Void> addQuestionFile(QuestionFile questionFile,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory=SessionManager.getSession(token);
        if(userInMemory!=null){
        	 if(!questionFileDao.addQuestionFile(questionFile)) {
                 dataWrapper.setErrorCode(ErrorCodeEnum.Error);
             }
        }else{
        	dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteQuestionFile(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory=SessionManager.getSession(token);
        if(userInMemory!=null){
        	if(!questionFileDao.deleteQuestionFile(id)) {
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
