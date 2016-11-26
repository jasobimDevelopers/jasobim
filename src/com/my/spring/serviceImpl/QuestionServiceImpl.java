package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.QuestionDao;
import com.my.spring.DAO.QuestionFileDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Question;
import com.my.spring.model.QuestionFile;
import com.my.spring.model.QuestionPojo;
import com.my.spring.model.User;
import com.my.spring.service.FileService;
import com.my.spring.service.QuestionService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
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
    FileDao fileDao;
    @Autowired
    FileService fileService;
    private String filePath = "files";
    private Integer fileType=2;
    @Override
    public DataWrapper<Void> addQuestion(Question question,String token,MultipartFile[] fileList,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	question.setUserId(userInMemory.getId());
        	if(question.getQuestionDate()==null){
				question.setQuestionDate(new Date(System.currentTimeMillis()));
			}
        	
			if(question!=null){
				if(!questionDao.addQuestion(question)){ 
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					return dataWrapper;
				}else if(fileList.length>0)
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
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteQuestion(Long id,String token,HttpServletRequest request, Long projectId) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			User userInDB = userDao.getById(userInMemory.getId());
			if (userInDB != null) {
				if(userInDB.getUserType()==UserTypeEnum.Admin.getType()){
					if(id!=null){
						QuestionFile questionFile=new QuestionFile();
						if(!questionDao.deleteQuestion(id,projectId)) 
						{
							DataWrapper<List<QuestionFile>> dataWrappers=questionFileDao.deleteQuestionFileByQuestionId(questionFile.getId());
							for(QuestionFile e:dataWrappers.getData()){
								fileDao.deleteFiles(e.getFileId());//删除文件表的信息
								Files files=fileDao.getById(e.getFileId());
								fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
							}
				            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}
						else
							return dataWrapper;
				        
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
    public DataWrapper<Void> updateQuestion(Question question,String token,MultipartFile[] file,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			User userInDB = userDao.getById(userInMemory.getId());
			if (userInDB != null) {
				if(userInDB.getUserType()==UserTypeEnum.Admin.getType()){
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
							questionDao.updateQuestion(question);
						}
						else
						{
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
							return dataWrapper;
						}
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
    public DataWrapper<List<QuestionPojo>> getQuestionList(Long projectId,String token, Integer pageIndex, Integer pageSize, Question question) {
    	DataWrapper<List<QuestionPojo>> datawrapper=new DataWrapper<List<QuestionPojo>>();
    	User userInMemory=SessionManager.getSession(token);
    	if(userInMemory!=null){
    		if(userInMemory.getUserType() == UserTypeEnum.Admin.getType()){
    			datawrapper= questionDao.getQuestionList(projectId,pageIndex,pageSize,question);
    		}else{
    			datawrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
    		}
    	}else{
    		datawrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
    	return datawrapper;
    }

	@Override
	public DataWrapper<Question> getQuestionDetailsByAdmin(Long questionId, String token) {
		DataWrapper<Question> dataWrapper = new DataWrapper<Question>();
		 User userInMemory = SessionManager.getSession(token);
	        if (userInMemory != null) {
				User userInDB = userDao.getById(userInMemory.getId());
				if (userInDB != null) {
					if(userInDB.getUserType()==UserTypeEnum.Admin.getType()){
						if(questionId!=null){
							Question Question=questionDao.getById(questionId);
							if(Question==null) 
					            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
							else
								dataWrapper.setData(Question);
					        
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
}
