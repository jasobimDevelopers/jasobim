package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.DAO.UserLogDao;
import com.my.spring.DAO.VideoDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Video;
import com.my.spring.model.VideoPojo;
import com.my.spring.model.Files;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.service.FileService;
import com.my.spring.service.UserLogService;
import com.my.spring.service.VideoService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Service("VideoService")
public class VideoServiceImpl implements VideoService {
    @Autowired
    VideoDao videoDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserLogDao userLogDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    FileService fileService;
    @Autowired
    UserLogService userLogService;
    private String filePath="files";
    private Integer fileType=4;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    @Override
    public DataWrapper<Void> addVideo(Video video,String token,MultipartFile file,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			/*if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){*/
				if(video!=null){
					if(file!=null){
						String path=filePath+"/"+"videos/"+video.getProjectId();
						Files newfile=fileService.uploadFile(path, file,fileType,request);
						video.setFileId(newfile.getId());
					}
					String originName = file.getOriginalFilename();
					if (originName.contains(".")) {
						originName = originName.substring(0, originName.lastIndexOf("."));
					}
					video.setUploadDate(new Date(System.currentTimeMillis()));
					video.setUploadUserId(userInMemory.getId());
					video.setSize(file.getSize()/1024);
					video.setOriginName(originName);
					if(!videoDao.addVideo(video)) 
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					else
						return dataWrapper;
			        
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
			/*}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}*/
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteVideo(Long id,String token,Long fileid,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(id!=null){
				if(videoDao.deleteVideo(id)) {
					Files files=fileDao.getById(fileid);
					fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
					fileDao.deleteFiles(fileid);//删除文件表的信息
				}
				else
				{
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					return dataWrapper;
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
    public DataWrapper<List<VideoPojo>> getVideoList(String token, Long projectId, Integer pageIndex, Integer pageSize, Video video,String beginDate,String endDate) {
    	DataWrapper<List<VideoPojo>> dataWrapper=new DataWrapper<List<VideoPojo>>();
    	List<VideoPojo> videopojo=new ArrayList<VideoPojo>();
    	
    	DataWrapper<List<Video>> dataWrappers=new DataWrapper<List<Video>>();
    	User userInMemory=SessionManager.getSession(token);
    	if(userInMemory!=null){
	    		if(userInMemory.getSystemId()==0 || userInMemory.getSystemId()==1){
	    			UserLog userLog = new UserLog();
	    			userLog.setProjectPart(3);
	    			userLog.setActionDate(new Date());
	    			userLog.setUserId(userInMemory.getId());
	    			userLog.setSystemType(userInMemory.getSystemId());
	    			userLog.setVersion("3.0");
	    			if(projectId!=null){
	    				userLog.setProjectId(projectId);
	    			}
	    			if(video.getId()!=null){
	    				userLog.setFileId(video.getId());
	    			}
	    			userLogDao.addUserLog(userLog);
	    		}
    			dataWrappers=videoDao.getVideoList(projectId, pageIndex, pageSize, video,beginDate,endDate);
    			for(int i=0;i<dataWrappers.getData().size();i++){
    				
    				VideoPojo videoPojo=new VideoPojo();
    				Integer temp=dataWrappers.getData().get(i).getBuildingNum();
    				Integer professiontype=dataWrappers.getData().get(i).getProfessionType();
    				if(dataWrappers.getData().get(i).getUploadDate()!=null){
    					videoPojo.setUploadDate(sdf.format(dataWrappers.getData().get(i).getUploadDate()));
    				}
    				if(dataWrappers.getData().get(i).getUploadUserId()!=null){
    					videoPojo.setUploadUserName(userDao.getById(dataWrappers.getData().get(i).getUploadUserId()).getRealName());
    				}
    				videoPojo.setVideoGrade(dataWrappers.getData().get(i).getVideoGrade());
    				videoPojo.setId(dataWrappers.getData().get(i).getId());
    				videoPojo.setProjectId(projectId);
    				videoPojo.setFileId(dataWrappers.getData().get(i).getFileId());
    				videoPojo.setBuildingNum(temp);
    				videoPojo.setVideoType(dataWrappers.getData().get(i).getVideoType());
    				videoPojo.setOriginName(dataWrappers.getData().get(i).getOriginName());
    				videoPojo.setProfessionType(professiontype);
    				videoPojo.setIntro(dataWrappers.getData().get(i).getIntro());
    				videoPojo.setSize(dataWrappers.getData().get(i).getSize());
    				Files file=fileDao.getById(dataWrappers.getData().get(i).getFileId());
    				if(file!=null){
    					videoPojo.setUrl(file.getUrl());
    				}
    				videopojo.add(i,videoPojo);
    			}
    			dataWrapper.setData(videopojo);
    			dataWrapper.setCurrentPage(dataWrappers.getCurrentPage());
    			dataWrapper.setCallStatus(dataWrappers.getCallStatus());
    			dataWrapper.setNumberPerPage(dataWrappers.getNumberPerPage());
    			dataWrapper.setTotalNumber(dataWrappers.getTotalNumber());
    			dataWrapper.setTotalPage(dataWrappers.getTotalPage());
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
