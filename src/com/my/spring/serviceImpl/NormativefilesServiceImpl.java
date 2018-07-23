package com.my.spring.serviceImpl;

import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.NormativefilesDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.NewsPojo;
import com.my.spring.model.Normativefiles;
import com.my.spring.model.NormativefilesPojo;
import com.my.spring.model.NormativefilesPojos;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.parameters.ProjectDatas;
import com.my.spring.service.FileService;
import com.my.spring.service.NormativefilesService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service("normativefilesService")
public class NormativefilesServiceImpl implements NormativefilesService {
    @Autowired
    NormativefilesDao normativefilesDao;
    /*@Autowired
    NormativefilesFileDao NormativefilesFileDao;*/
    @Autowired
    FileDao fileDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileService fileService;
    @Autowired
    UserLogService userLogSerivce;
    private String filePath = "files";
    @Override
    public DataWrapper<Void> addNormativefiles(Normativefiles Normativefiles,String token,MultipartFile[] fileList,Integer fileType,HttpServletRequest request) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(Normativefiles!=null){
				
				if(fileList.length >0){
					String idList="";
					for(int i=0;i<fileList.length;i++){
						String path=filePath+"/"+"normativefiles";
						Files newfile=fileService.uploadFile(path, fileList[i],fileType,request);
						if(!idList.equals("")){
							idList=idList+","+newfile.getId();
						}else{
							idList+=newfile.getId();
						}
					}
					Normativefiles.setFileIdList(idList);
				}
				Normativefiles.setSubmitUserId(userInMemory.getId());
				Normativefiles.setSubmitDate(new Date(System.currentTimeMillis()));
				if(!normativefilesDao.addNormativefiles(Normativefiles)) 
				{
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
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
    public DataWrapper<Void> deleteNormativefiles(Long id,String token,Long fileId) {
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			//if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
			if(id!=null){
				Normativefiles normativefiles=normativefilesDao.getById(id);
				if(normativefiles.getFileIdList()!=null){
					if(fileId!=null){
						String test=normativefiles.getFileIdList();
						if(fileId.toString().equals(test.split(",")[0])){
							normativefiles.setFileIdList(test.replace(fileId.toString()+",", ""));
						}else{
							normativefiles.setFileIdList(test.replace(","+fileId.toString(), ""));
						}
						fileService.deleteFileById(fileId);
					}
						
				}
				if(!normativefilesDao.updateNormativefiles(normativefiles)){
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
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
    public DataWrapper<List<NormativefilesPojo>> getNormativefilesList(String token , Integer pageIndex, Integer pageSize, Normativefiles Normativefiles) {
    	DataWrapper<List<NormativefilesPojo>> dataWrappers = new DataWrapper<List<NormativefilesPojo>>();
    	DataWrapper<List<Normativefiles>> dataWrapper = new DataWrapper<List<Normativefiles>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	if(Normativefiles.getId()!=null){
        		UserLog userLog = new UserLog();
        		userLog.setActionDate(new Date());
        		//userLog.setFileId(Normativefiles.getId());
        		if(Normativefiles.getFileIdList()!=null){
        			String[] fileids=Normativefiles.getFileIdList().split(",");
        			userLog.setFileId(Long.valueOf(fileids[0]));
        		}
        		if(userInMemory.getSystemId()!=null){
        			userLog.setSystemType(userInMemory.getSystemId());
        		}
        		userLog.setProjectPart(ProjectDatas.NormativeFile_area.getCode());
        		userLog.setUserId(userInMemory.getId());
        		//userLog.setVersion("-1");
        		userLogSerivce.addUserLog(userLog, token);
        	}
				dataWrapper=normativefilesDao.getNormativefilessList(pageIndex,pageSize,Normativefiles);
				if(!dataWrapper.getData().isEmpty()){
					List<NormativefilesPojo> NormativefilesPojoList = new ArrayList<NormativefilesPojo>();
					for(int i=0;i<dataWrapper.getData().size();i++){
						NormativefilesPojo NormativefilesPojo =new NormativefilesPojo();
						NormativefilesPojo.setContent(dataWrapper.getData().get(i).getContent());
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						NormativefilesPojo.setSubmitDate(sdf.format(dataWrapper.getData().get(i).getSubmitDate()));
						NormativefilesPojo.setId(dataWrapper.getData().get(i).getId());
						NormativefilesPojo.setDescribe(dataWrapper.getData().get(i).getDescribes());
						NormativefilesPojo.setSize(dataWrapper.getData().get(i).getSize());
						NormativefilesPojo.setStudyType(dataWrapper.getData().get(i).getStudyType());
						NormativefilesPojo.setTitle(dataWrapper.getData().get(i).getTitle());
						
						if(dataWrapper.getData().get(i).getFileIdList()!=null){
							String[] idlist = dataWrapper.getData().get(i).getFileIdList().split(",");
							String[] fileUrlList = new String[idlist.length];
							String[] fileNameList = new String[idlist.length];
							int s=0;
							do{
								Files files = fileService.getById(Long.valueOf(idlist[s]));
								if(Normativefiles.getDescribes()!=null){
									if(files.getRealName().contains(Normativefiles.getDescribes())){
										fileUrlList[s]=files.getUrl();
										fileNameList[s]=files.getRealName();
									}
								}else{
									fileUrlList[s]=files.getUrl();
									fileNameList[s]=files.getRealName();
								}
								s++;
							}while(s<idlist.length);
							NormativefilesPojo.setFileUrlList(fileUrlList);
							NormativefilesPojo.setFileNameList(fileNameList);
						}
						if(dataWrapper.getData().get(i).getSubmitUserId()!=null){
							NormativefilesPojo.setSubmitUserName(userDao.getById(dataWrapper.getData().get(i).getSubmitUserId()).getUserName());
						}
						if(NormativefilesPojo!=null){
							NormativefilesPojoList.add(NormativefilesPojo);
						}
					}
					if(NormativefilesPojoList!=null && NormativefilesPojoList.size()>0){
						dataWrappers.setData(NormativefilesPojoList);
						dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
						dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
						dataWrappers.setTotalPage(dataWrapper.getTotalPage());
						dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
					}
				}
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
			}
        if(dataWrappers.getCallStatus()==CallStatusEnum.SUCCEED && dataWrappers.getData()==null){
        	List<NormativefilesPojo> pas= new ArrayList<NormativefilesPojo>();
        	dataWrappers.setData(pas);
        }
        return dataWrappers;
    }
    
    @Override
    public DataWrapper<List<NormativefilesPojos>> getNormativefilesLists(String token , Integer pageIndex, Integer pageSize, Normativefiles Normativefiles,Long projectId) {
    	DataWrapper<List<NormativefilesPojos>> dataWrappers = new DataWrapper<List<NormativefilesPojos>>();
    	DataWrapper<List<Normativefiles>> dataWrapper = new DataWrapper<List<Normativefiles>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
        	if(userInMemory.getSystemId()!=null){
        		if(projectId!=null){
	        		UserLog userLog = new UserLog();
	        		userLog.setActionDate(new Date());
	        		if(Normativefiles.getId()!=null){
	        			userLog.setFileId(Normativefiles.getId());
	        		}
	        		userLog.setActionType(0);
	        		userLog.setProjectPart(ProjectDatas.NormativeFile_area.getCode());
	        		userLog.setUserId(userInMemory.getId());
	        		userLog.setProjectId(projectId);
	        		userLog.setSystemType(userInMemory.getSystemId());
	        		userLogSerivce.addUserLog(userLog, token);
	        	}
        	}
			dataWrapper=normativefilesDao.getNormativefilessList(pageIndex,pageSize,Normativefiles);
			if(dataWrapper.getData()!=null){
				if(!dataWrapper.getData().isEmpty()){
					List<NormativefilesPojos> NormativefilesPojoList = new ArrayList<NormativefilesPojos>();
					for(int i=0;i<dataWrapper.getData().size();i++){
						if(dataWrapper.getData().get(i).getFileIdList()!=null){
							String[] idlist = dataWrapper.getData().get(i).getFileIdList().split(",");
							for(int s=0;s<idlist.length;s++){
								NormativefilesPojos NormativefilesPojo =new NormativefilesPojos();
								NormativefilesPojo.setContent(dataWrapper.getData().get(i).getContent());
								SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
								NormativefilesPojo.setSubmitDate(sdf.format(dataWrapper.getData().get(i).getSubmitDate()));
								NormativefilesPojo.setId(dataWrapper.getData().get(i).getId());
								NormativefilesPojo.setDescribe(dataWrapper.getData().get(i).getDescribes());
								NormativefilesPojo.setSize(dataWrapper.getData().get(i).getSize());
								NormativefilesPojo.setStudyType(dataWrapper.getData().get(i).getStudyType());
								NormativefilesPojo.setTitle(dataWrapper.getData().get(i).getTitle());
								if(dataWrapper.getData().get(i).getSubmitUserId()!=null){
									NormativefilesPojo.setSubmitUserName(userDao.getById(dataWrapper.getData().get(i).getSubmitUserId()).getUserName());
								}
								Files files = fileService.getById(Long.valueOf(idlist[s]));
								if(files!=null){
									if(Normativefiles.getContent()!=null){
										if(files.getRealName().contains(Normativefiles.getContent())){
											NormativefilesPojo.setFileUrlList(files.getUrl());
											NormativefilesPojo.setFileNameList(files.getRealName());
											NormativefilesPojo.setFileId(Long.valueOf(idlist[s]));
											if(NormativefilesPojo!=null){
												NormativefilesPojoList.add(NormativefilesPojo);
											}
										
										}
									}else{
										NormativefilesPojo.setFileUrlList(files.getUrl());
										NormativefilesPojo.setFileNameList(files.getRealName());
										NormativefilesPojo.setFileId(Long.valueOf(idlist[s]));
										if(NormativefilesPojo!=null){
											NormativefilesPojoList.add(NormativefilesPojo);
										}
									}
								}
							}
						}
					}
					if(NormativefilesPojoList!=null && NormativefilesPojoList.size()>0 && !NormativefilesPojoList.isEmpty()){
						dataWrappers.setData(NormativefilesPojoList);
						dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
						dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
						dataWrappers.setTotalPage(dataWrapper.getTotalPage());
						dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
					}
				}
			}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        if(dataWrappers.getCallStatus()==CallStatusEnum.SUCCEED && dataWrappers.getData()==null){
        	List<NormativefilesPojos> pas= new ArrayList<NormativefilesPojos>();
        	dataWrappers.setData(pas);
        }
        return dataWrappers;
    }

	@Override
	public DataWrapper<List<Normativefiles>> getNormativefilesListByUserId(Long userId,String token) {
		DataWrapper<List<Normativefiles>> dataWrapper = new DataWrapper<List<Normativefiles>>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userId!=null){
				if(normativefilesDao.getNormativefilessListByUserId(userId)==null) 
		            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				else
					return normativefilesDao.getNormativefilessListByUserId(userId);
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return dataWrapper;
	}

	

	
}
