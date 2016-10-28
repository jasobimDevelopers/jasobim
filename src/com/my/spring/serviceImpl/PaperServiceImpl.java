package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.PaperDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Paper;
import com.my.spring.model.User;
import com.my.spring.service.FileService;
import com.my.spring.service.PaperService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("paperService")
public class PaperServiceImpl implements PaperService {
    @Autowired
    PaperDao paperDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    FileService fileService;
    private String filePath="D:\\fileupload";
    private Integer fileType=1;
    /*
     * 图纸信息上传添加
     * 需要管理员身份
     * */
    
    @Override
    public DataWrapper<Void> addPaper(Paper paper,String token,MultipartFile file) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);////验证登录时的session
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				///////验证是不是管理员身份
				if(paper!=null){////验证上传的实体类是不是为空
					///////1.文件的上传返回url
					String path=filePath+"/"+"papers"+"/";
					String newfilename=fileService.uploadFile(path, file,fileType);
					DataWrapper<Files> dataWrappers=fileDao.getByName(newfilename);
					paper.setFileId(dataWrappers.getData().getId());
					if(!paperDao.addPaper(paper)) 
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
    }

    @Override
    public DataWrapper<Void> deletePaper(Long id,Long fileid,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!paperDao.deletePaper(id)){ ///删除图纸表的信息
						fileDao.deleteFiles(fileid);//删除文件表的信息
						Files files=fileDao.getById(id);
						fileService.deleteFileByPath(files.getUrl());///删除实际文件
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
					else
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
    public DataWrapper<Void> updatePaper(Paper paper,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(paper!=null){
					if(!paperDao.updatePaper(paper)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
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
    public DataWrapper<List<Paper>> getPaperList(String token) {
    	DataWrapper<List<Paper>> dataWrapper = new DataWrapper<List<Paper>>();
		 User userInMemory = SessionManager.getSession(token);
	        if (userInMemory != null) {
	        	return paperDao.getPaperList();
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
			}
        return dataWrapper;
    }

	@Override
	public DataWrapper<Paper> getPaperDetailsByAdmin(Long paperId, String token) {
		DataWrapper<Paper> dataWrapper = new DataWrapper<Paper>();
		 User userInMemory = SessionManager.getSession(token);
	        if (userInMemory != null) {
				if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
					if(paperId!=null){
						Paper paper=paperDao.getById(paperId);
						if(paper==null) 
				            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
						else
							dataWrapper.setData(paper);
				        
					}
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
				}
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
			}
		return dataWrapper;
	}
}
