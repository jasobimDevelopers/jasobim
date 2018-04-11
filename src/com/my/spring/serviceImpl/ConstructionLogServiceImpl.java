package com.my.spring.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.DAO.ConstructionLogDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.ConstructionLog;
import com.my.spring.model.ConstructionLogPojo;
import com.my.spring.model.Files;
import com.my.spring.model.Project;
import com.my.spring.model.User;
import com.my.spring.service.ConstructionLogService;
import com.my.spring.service.FileService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;
@Service("constructionLogService")
public class ConstructionLogServiceImpl implements ConstructionLogService {
	
    @Autowired
    ConstructionLogDao constructionLogDao;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    ProjectDao projectDao;
    
    @Autowired
    FileService fileService;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     
    
    private String filePath = "files";
	@Override
	public DataWrapper<List<ConstructionLogPojo>> getConstructionLogList(String token, Integer pageIndex,
			Integer pageSize, ConstructionLog constructionLog) {
		// TODO Auto-generated method stub
		DataWrapper<List<ConstructionLogPojo>> clp = new DataWrapper<List<ConstructionLogPojo>>();
		List<ConstructionLogPojo> clpsss = new ArrayList<ConstructionLogPojo>();
		DataWrapper<List<ConstructionLog>> clps = new DataWrapper<List<ConstructionLog>>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			clps = constructionLogDao.getConstructionLogsList(pageIndex, pageSize, constructionLog);
			if(clps.getData()!=null){
				if(clps.getData().size()>0)
				{
					for(ConstructionLog cl:clps.getData())
					{
						ConstructionLogPojo constructionLogPojo = new ConstructionLogPojo();
						constructionLogPojo.setConstructionDate(cl.getConstructionDate());
						constructionLogPojo.setContent(cl.getContent());
						constructionLogPojo.setCreateDate(sdf.format(cl.getCreateDate()));
						constructionLogPojo.setId(cl.getId());
						constructionLogPojo.setProjectId(cl.getProjectId());
						constructionLogPojo.setUserId(cl.getUserId());
						constructionLogPojo.setWeather(cl.getWeather());
						if(cl.getUserId()!=null){
							User user = userDao.getById(cl.getId());
							if(user!=null){
								constructionLogPojo.setCreateUserName(user.getRealName());
							}
						}
						if(cl.getProjectId()!=null){
							Project project = projectDao.getById(cl.getProjectId());
							if(project!=null){
								constructionLogPojo.setProjectName(project.getName());
							}
						}
						if(cl.getPicId()!=null){
							String[] picIds = cl.getPicId().split(",");
							String[] picUrls = new String[picIds.length];
							for(int i=0;i<picIds.length;i++){
								Files file = fileService.getById(Long.valueOf(picIds[i]));
								if(file!=null){
									picUrls[i]=file.getUrl();
								}
							}
							constructionLogPojo.setPicUrl(picUrls);
						}
						clpsss.add(constructionLogPojo);
					}
					clp.setData(clpsss);
				}
			}
		}else{
			clp.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return clp;
	}

	@Override
	public DataWrapper<List<ConstructionLog>> getConstructionLogListByUserId(Long userId, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataWrapper<Void> addConstructionLog(ConstructionLog constructionLog, String token, MultipartFile[] files,
			HttpServletRequest request) {
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(files!=null)
			{
				String picIds="";
				for(int i=0;i<files.length;i++){
					Files file = fileService.uploadFile(filePath+"/"+constructionLog.getId()+"/constructionLog", files[i], 5, request);
					if(file!=null){
						if(picIds.equals("")){
							picIds=file.getId().toString();
						}else{
							picIds=picIds+","+file.getId();
						}
					}
				}
				if(!picIds.equals("")){
					constructionLog.setPicId(picIds);
				}
			}
			constructionLog.setCreateDate(new Date());
			constructionLog.setUserId(userInMemory.getId());
			if(!constructionLogDao.addConstructionLog(constructionLog)){
				result.setErrorCode(ErrorCodeEnum.Error);
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> deleteConstructionLog(String id, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> result = new DataWrapper<Void>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(id!=null){
				String[] ids = id.split(",");
				if(!constructionLogDao.deleteConstructionLogList(ids)){
					result.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		}else{
			result.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return result;
	}

	@Override
	public DataWrapper<Void> updateConstructionLog(ConstructionLog ct, String token,MultipartFile[] files,HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<Void> str1 = new DataWrapper<Void>();
		User userInMemory =SessionManager.getSession(token);
		if(userInMemory!=null){
			if(ct!=null){
				if(userInMemory.getUserType()==UserTypeEnum.Admin.getType() || userInMemory.getId()==ct.getUserId()){
					if(ct.getId()!=null){
						ConstructionLog cl=constructionLogDao.getById(ct.getId());
						if(cl!=null){
							ct.setUpdateDate(new Date());
							ct.setUserId(cl.getUserId());
							ct.setProjectId(cl.getProjectId());
							ct.setCreateDate(cl.getCreateDate());
							if(files!=null)
							{
								String pics = cl.getPicId();
								if(pics!=null){
									String[] picids=pics.split(",");
									for(String s:picids){
										fileService.deleteFileByReal(Long.valueOf(s), request);
									}
								}
								String picIds="";
								for(int i=0;i<files.length;i++){
									Files file = fileService.uploadFile(filePath+"/"+ct.getId()+"/constructionLog", files[i], 5, request);
									if(file!=null){
										if(picIds.equals("")){
											picIds=file.getId().toString();
										}else{
											picIds=picIds+","+file.getId();
										}
									}
								}
								if(!picIds.equals("")){
									ct.setPicId(picIds);
								}
							}
							if(!constructionLogDao.updateConstructionLog(ct)){
								str1.setErrorCode(ErrorCodeEnum.Error);
							}
						}
						
						
					}else{
						str1.setErrorCode(ErrorCodeEnum.Empty_Inputs);
					}
					
				}
			}else{
				str1.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		}else{
			str1.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return str1;
	}

}
