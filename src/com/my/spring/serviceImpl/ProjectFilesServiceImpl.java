package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.ProjectFilesDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.ProjectFiles;
import com.my.spring.model.ProjectFilesPojo;
import com.my.spring.model.User;
import com.my.spring.parameters.Parameters;
import com.my.spring.service.FileService;
import com.my.spring.service.ProjectFilesService;
import com.my.spring.service.UserLogService;
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

@Service("projectFilesService")
public class ProjectFilesServiceImpl implements ProjectFilesService {
 
    @Autowired
    UserDao userDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    ProjectFilesDao projectFilesDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    FileService fileService;
    @Autowired
    UserLogService userLogSerivce;
    private String filePath = "files";
    private Integer fileType=9;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /*
     * 图纸信息上传添加
     * 需要管理员身份
     * */
    
    @Override
    public DataWrapper<Void> addProjectFiles(ProjectFiles projectFiles,String token,MultipartFile file,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);////验证登录时的session
        if (userInMemory != null) {
				if(projectFiles!=null){////验证上传的实体类是不是为空
					///////1.文件的上传返回url
						
					String path=filePath+"/"+"projectData/"+projectFiles.getProjectId();
					Files newfile=fileService.uploadFile(path, file,fileType,request);
					projectFiles.setFileIds(newfile.getId());
					String originName = file.getOriginalFilename();
					if (originName.contains(".")) {
						originName = originName.substring(0, originName.lastIndexOf("."));
					}
					projectFiles.setTest(originName);
					projectFiles.setUploadUserId(userInMemory.getId());
					//projectFiles.setUploadDate(new Date());
					if(!projectFilesDao.addProjectFiles(projectFiles)) 
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
    public DataWrapper<Void> deleteProjectFiles(Long id,String token,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
				if(id!=null){
					if(!projectFilesDao.deleteProjectFiles(id)){ ///删除图纸表的信息
						projectFilesDao.deleteProjectFiles(projectFilesDao.getById(id).getFileIds());//删除文件表的信息
						Files files=fileDao.getById(id);
						fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
			            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
					else
						return dataWrapper;
			        
				}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
    }


    @Override
    public DataWrapper<List<ProjectFilesPojo>> getProjectFilesList(String token,Integer pageIndex, Integer pageSize, ProjectFiles projectFiles) {
    	DataWrapper<List<ProjectFilesPojo>> dataWrappers = new DataWrapper<List<ProjectFilesPojo>>();
    	List<ProjectFilesPojo> papers=new ArrayList<ProjectFilesPojo>();
    	
    	DataWrapper<List<ProjectFiles>> dataWrapper = new DataWrapper<List<ProjectFiles>>();
		 User userInMemory = SessionManager.getSession(token);
		
	        if (userInMemory != null) {
	        		dataWrapper= projectFilesDao.getProjectFilesList(userInMemory,pageSize, pageIndex,projectFiles);
	        		for(int i=0;i<dataWrapper.getData().size();i++){
	        			 Long projectId=dataWrapper.getData().get(i).getProjectId();
	        			ProjectFilesPojo pf=new ProjectFilesPojo();
	        			if(projectId!=null){
	        				pf.setProjectId(projectId);
	        				pf.setProjectName(projectDao.getById(projectId).getName());
	        			}
	        			
	        			pf.setId(dataWrapper.getData().get(i).getId());
	        			pf.setRemark(dataWrapper.getData().get(i).getTest());
	        			pf.setFiletype(dataWrapper.getData().get(i).getFileType());
	        			if(dataWrapper.getData().get(i).getUploadUserId()!=null){
	        				pf.setUserId(dataWrapper.getData().get(i).getUploadUserId());
	        				pf.setUploadUser(userDao.getById(dataWrapper.getData().get(i).getUploadUserId()).getRealName());
	        			}
	        			pf.setUploadDate(sdf.format(dataWrapper.getData().get(i).getUploadDate()));
	        			pf.setTypeName(Parameters.projectFilesType[dataWrapper.getData().get(i).getTypeName()]);
	        			if(dataWrapper.getData().get(i).getFileIds()!=null){
	        				pf.setFileId(dataWrapper.getData().get(i).getFileIds());
	        				Files files = fileService.getById(dataWrapper.getData().get(i).getFileIds());
	        				pf.setFileName(files.getName());
	        				pf.setUrl(files.getUrl());
	        			}
	        			if(pf.getId()!=null){
	        				papers.add(pf);
	        			}
	        		}
	        		dataWrappers.setData(papers);
	        		dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
	    			dataWrappers.setCallStatus(dataWrapper.getCallStatus());
	    			dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
	    			dataWrappers.setTotalNumber(papers.size());
	    			dataWrappers.setTotalPage(dataWrapper.getTotalPage());
	        	
			} else {
				dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
			}
        return dataWrappers;
    }


	
	


}
