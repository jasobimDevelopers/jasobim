package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.PaperDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Paper;
import com.my.spring.model.PaperPojo;
import com.my.spring.model.User;
import com.my.spring.service.FileService;
import com.my.spring.service.PaperService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
    private String filePath = "files";
    private Integer fileType=1;
    /*
     * 图纸信息上传添加
     * 需要管理员身份
     * */
    
    @Override
    public DataWrapper<Void> addPaper(Paper paper,String token,MultipartFile file,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);////验证登录时的session
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				///////验证是不是管理员身份
				if(paper!=null){////验证上传的实体类是不是为空
					///////1.文件的上传返回url
					String path=filePath+"/"+"papers";
					Files newfile=fileService.uploadFile(path, file,fileType,request);
					paper.setFileId(newfile.getId());
					
					String originName = file.getOriginalFilename();
					if (originName.contains(".")) {
						originName = originName.substring(0, originName.lastIndexOf("."));
					}
					paper.setOriginName(originName);
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
    public DataWrapper<Void> deletePaper(Long id,Long fileid,String token,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!paperDao.deletePaper(id)){ ///删除图纸表的信息
						fileDao.deleteFiles(fileid);//删除文件表的信息
						Files files=fileDao.getById(id);
						fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
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
    public DataWrapper<List<PaperPojo>> getPaperList(Long projectId,String token,Integer pageIndex, Integer pageSize, Paper paper) {
    	DataWrapper<List<PaperPojo>> dataWrappers = new DataWrapper<List<PaperPojo>>();
    	List<PaperPojo> papers=new ArrayList<PaperPojo>();
    	
    	DataWrapper<List<Paper>> dataWrapper = new DataWrapper<List<Paper>>();
		 User userInMemory = SessionManager.getSession(token);
	        if (userInMemory != null) {
	        	
	        		dataWrapper= paperDao.getPaperList(projectId,pageSize, pageIndex,paper);
	        		for(int i=0;i<dataWrapper.getData().size();i++){
	        			PaperPojo papernew=new PaperPojo();
	        			papernew.setProjectId(projectId);
	        			papernew.setBuildingNum(dataWrapper.getData().get(i).getBuildingNum());
	        			papernew.setProfessionType(dataWrapper.getData().get(i).getProfessionType());
	        			papernew.setFloorNum(dataWrapper.getData().get(i).getFloorNum());
	        			papernew.setOriginName(dataWrapper.getData().get(i).getOriginName());
	        			Files file=fileDao.getById(dataWrapper.getData().get(i).getFileId());
	        			if(file!=null){
	        				papernew.setUrl(file.getUrl());
	        			}
	        			papers.add(i,papernew);
	        		}
	        		dataWrappers.setData(papers);
	        		dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
	    			dataWrappers.setCallStatus(dataWrapper.getCallStatus());
	    			dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
	    			dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
	    			dataWrappers.setTotalPage(dataWrapper.getTotalPage());
	        	
			} else {
				dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
			}
        return dataWrappers;
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
