package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.VideoDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Video;
import com.my.spring.model.Files;
import com.my.spring.model.User;
import com.my.spring.service.FileService;
import com.my.spring.service.VideoService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("VideoService")
public class VideoServiceImpl implements VideoService {
    @Autowired
    VideoDao videoDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    FileService fileService;
    private String filePath="/files";
    private Integer fileType=4;
    @Override
    public DataWrapper<Void> addVideo(Video video,String token,MultipartFile file) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(video!=null){
					String path=filePath+"/"+"videos"+"/";
					Files newfile=fileService.uploadFile(path, file,fileType);
					video.setFileId(newfile.getId());
					if(!videoDao.addVideo(video)) 
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
    public DataWrapper<Void> deleteVideo(Long id,String token,Long fileid) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!videoDao.deleteVideo(id)) {
						fileDao.deleteFiles(fileid);//删除文件表的信息
						Files files=fileDao.getById(id);
						fileService.deleteFileByPath(files.getUrl());///删除实际文件
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
    public DataWrapper<List<Video>> getVideoList(String token) {
    	DataWrapper<List<Video>> dataWrapper=new DataWrapper<List<Video>>();
    	User userInMemory=SessionManager.getSession(token);
    	if(userInMemory!=null){
    		 return videoDao.getVideoList();
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
    	}
       return dataWrapper;
    }

	@Override
	public DataWrapper<Video> getVideoDetails(Long id, String token) {
		DataWrapper<Video> dataWrapper=new DataWrapper<Video>();
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null){
			return videoDao.getById(id);
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
}
