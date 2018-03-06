package com.my.spring.serviceImpl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.my.spring.DAO.BuildingDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.DAO.PaperDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Building;
import com.my.spring.model.Files;
import com.my.spring.model.Paper;
import com.my.spring.model.PaperPojo;
import com.my.spring.model.User;
import com.my.spring.model.UserLog;
import com.my.spring.service.FileService;
import com.my.spring.service.PaperService;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Service("paperService")
public class PaperServiceImpl implements PaperService {
    @Autowired
    PaperDao paperDao;
    @Autowired
    UserDao userDao;
    @Autowired
    BuildingDao buildingDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    FileService fileService;
    @Autowired
    UserLogService userLogSerivce;
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
        Building buliding = new Building(); 
        if (userInMemory != null) {
			/*if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){*/
				///////验证是不是管理员身份
				if(paper!=null){////验证上传的实体类是不是为空
					///////1.文件的上传返回url
					String path=filePath+"/"+"papers/"+paper.getProjectId();
					Files newfile=fileService.uploadFile(path, file,fileType,request);
					paper.setFileId(newfile.getId());
					
					String originName = file.getOriginalFilename();
					if (originName.contains(".")) {
						originName = originName.substring(0, originName.lastIndexOf("."));
					}
					long fileSize=file.getSize()/1024;////文件大小kb单位 
					paper.setSize(fileSize);
					paper.setOriginName(originName);
					if(!paperDao.addPaper(paper)) 
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
    public DataWrapper<Void> deletePaper(Long id,Long fileid,String token,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
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
	        		
	        		dataWrapper= paperDao.getPaperList(projectId,pageSize, pageIndex,paper,null);
	        		//String nameArray=null;
	        		String temp="标准层_4096X3072";
	        		int flag=-1;
	        		for(int i=0;i<dataWrapper.getData().size();i++){
	        			PaperPojo papernew=new PaperPojo();
	        			
	        			if(temp.equals(dataWrapper.getData().get(i).getOriginName())){
	        				if(flag==-1){
	        					papernew.setProjectId(projectId);
			        			papernew.setId(dataWrapper.getData().get(i).getId());
			        			papernew.setBuildingNum(dataWrapper.getData().get(i).getBuildingNum());
			        			papernew.setProfessionType(dataWrapper.getData().get(i).getProfessionType());
			        			papernew.setFloorNum(dataWrapper.getData().get(i).getFloorNum());
			        			papernew.setOriginName(dataWrapper.getData().get(i).getOriginName());
			        			Files file=fileDao.getById(dataWrapper.getData().get(i).getFileId());
			        			if(file!=null){
			        				papernew.setUrl(file.getUrl());
			        			}
	        				}
	        			}else{
	        				papernew.setProjectId(projectId);
		        			papernew.setId(dataWrapper.getData().get(i).getId());
		        			papernew.setBuildingNum(dataWrapper.getData().get(i).getBuildingNum());
		        			papernew.setProfessionType(dataWrapper.getData().get(i).getProfessionType());
		        			papernew.setFloorNum(dataWrapper.getData().get(i).getFloorNum());
		        			papernew.setOriginName(dataWrapper.getData().get(i).getOriginName());
		        			Files file=fileDao.getById(dataWrapper.getData().get(i).getFileId());
		        			if(file!=null){
		        				papernew.setUrl(file.getUrl());
		        			}
	        			}
	        			if(temp.equals(dataWrapper.getData().get(i).getOriginName())){
	        				flag=0;
	        			}
	        			if(papernew.getId()!=null){
	        				papers.add(papernew);
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

	@Override
	public DataWrapper<Void> deletePaperByAdmin(Long id, String token, HttpServletRequest request) {
		DataWrapper<Void> dataWrapper=new DataWrapper<Void>();
		User userInMemory=SessionManager.getSession(token);
		if(userInMemory!=null)
		{
				if(id!=null)
				{
					Paper papers=paperDao.getById(id);
					Long fileid=papers.getFileId();
					if(paperDao.deletePaper(id))
					{ ///删除图纸表的信息
						Files files=fileDao.getById(fileid);
						fileService.deleteFileByPath(files.getUrl(),request);///删除实际文件
						fileDao.deleteFiles(papers.getFileId());//删除文件表的信息
						
					}else{
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
				}else{
					dataWrapper.setErrorCode(ErrorCodeEnum.Empty_Inputs);
				}
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<PaperPojo>> getPaperLists(Long projectId, String token, Integer pageIndex, Integer pageSize,
			Paper paper,String content) {
		DataWrapper<List<PaperPojo>> dataWrappers = new DataWrapper<List<PaperPojo>>();
    	List<PaperPojo> papers=new ArrayList<PaperPojo>();
    	
    	DataWrapper<List<Paper>> dataWrapper = new DataWrapper<List<Paper>>();
		 User userInMemory = SessionManager.getSession(token);
	        if (userInMemory != null) {
		        	if(userInMemory.getSystemId()!=null){
		        		if(projectId!=null){
			        		UserLog userLog = new UserLog();
			        		userLog.setActionDate(new Date());
			        		if(paper.getId()!=null){
			        			userLog.setFileId(paper.getId());
			        		}
			        		userLog.setProjectPart(10);
			        		userLog.setUserId(userInMemory.getId());
			        		userLog.setSystemType(userInMemory.getSystemId());
			        		userLog.setVersion("3.0");
			        		userLogSerivce.addUserLog(userLog, token);
			        	}
		        	}
		        	
	        		dataWrapper= paperDao.getPaperList(projectId,pageSize, pageIndex,paper,content);
	        		for(int i=0;i<dataWrapper.getData().size();i++){
	        			PaperPojo papernew=new PaperPojo();
        				papernew.setProjectId(projectId);
	        			papernew.setId(dataWrapper.getData().get(i).getId());
	        			papernew.setBuildingNum(dataWrapper.getData().get(i).getBuildingNum());
	        			papernew.setProfessionType(dataWrapper.getData().get(i).getProfessionType());
	        			papernew.setFloorNum(dataWrapper.getData().get(i).getFloorNum());
	        			papernew.setOriginName(dataWrapper.getData().get(i).getOriginName());
	        			papernew.setSize(dataWrapper.getData().get(i).getSize());
	        			Files file=fileDao.getById(dataWrapper.getData().get(i).getFileId());
	        			if(file!=null){
	        				papernew.setUrl(file.getUrl());
	        				papernew.setUploadDate(file.getIntro());
	        			}
	        			if(papernew.getId()!=null){
	        				papers.add(papernew);
	        			}
	        		}
	        		dataWrappers.setData(papers);
	        		if(dataWrapper.getCurrentPage()==-1){
	        			dataWrappers.setCurrentPage(1);
	        		}else{
	        			dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
	        		}
	    			dataWrappers.setCallStatus(dataWrapper.getCallStatus());
	    			dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
	    			dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
	    			dataWrappers.setTotalPage(dataWrapper.getTotalPage());
	        	
			} else {
				dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
			}
        return dataWrappers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<PaperPojo>> getPapers(HttpServletRequest request,Long projectId, String token, Integer pageIndex, Integer pageSize, Paper paper) {
		// TODO Auto-generated method stub
		DataWrapper<List<PaperPojo>> dataWrappers = new DataWrapper<List<PaperPojo>>();
    	List<PaperPojo> papers=new ArrayList<PaperPojo>();
    	
    	DataWrapper<List<Paper>> dataWrapper = new DataWrapper<List<Paper>>();
		 User userInMemory = SessionManager.getSession(token);
	        if (userInMemory != null) {
	        	String projectIdList=null;
	        	if(userInMemory.getUserType()==3){
	        		projectIdList=userInMemory.getProjectList();
	        	}
	        	if(projectIdList==null){
	        		projectIdList="79";
	        	}	
	        		dataWrapper= paperDao.getPaperLists(projectIdList,pageSize, pageIndex,paper);
	        		for(int i=0;i<dataWrapper.getData().size();i++){
	        			PaperPojo papernew=new PaperPojo();
        				papernew.setProjectId(projectId);
	        			papernew.setId(dataWrapper.getData().get(i).getId());
	        			papernew.setBuildingNum(dataWrapper.getData().get(i).getBuildingNum());
	        			papernew.setProfessionType(dataWrapper.getData().get(i).getProfessionType());
	        			papernew.setFloorNum(dataWrapper.getData().get(i).getFloorNum());
	        			papernew.setOriginName(dataWrapper.getData().get(i).getOriginName());
	        			Files file=fileDao.getById(dataWrapper.getData().get(i).getFileId());
	        			if(file!=null){
	        				SimpleDateFormat sdf =   new SimpleDateFormat("yyyyMMddHHmmssSSS" );
	        			   	Date d=new Date();
	        			   	String str=sdf.format(d);
	        			   	String rootPath = request.getSession().getServletContext().getRealPath("/");
	        			   	String filePath="";
	        			   	if(projectId!=null){
	        			   		filePath="/codeFiles/"+projectId;
	        			   	}else{
	        			   		filePath="/codeFiles";
	        			   	}
	        			   	String imgpath=rootPath+filePath;
	        			   	try{
	        				MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
	        			        @SuppressWarnings("rawtypes")
	        					Map hints = new HashMap();  
	        			        //内容所使用编码  
	        			        hints.put(EncodeHintType.CHARACTER_SET, "utf8");  
	        			        BitMatrix bitMatrix = multiFormatWriter.encode("http://jasobim.com.cn/"+file.getUrl(),BarcodeFormat.QR_CODE, 200, 200, hints);  
	        			        //生成二维码  
	        			        File outputFile = new File(imgpath,str+".png"); 
	        			        
	        			        MatrixToImageWriter.writeToFile(bitMatrix, "png", outputFile);  
	        				} catch (Exception e) {
	        					e.printStackTrace();
	        				}
	        			   	String url="";
	        			   	if(projectId!=null){
	        			   		url="codeFiles/"+projectId+"/"+str+".png";
	        			   	}else{
	        			   		url="codeFiles/"+str+".png";
	        			   	}
	        				papernew.setUrl(url);
	        			}
	        			if(papernew.getId()!=null){
	        				papers.add(papernew);
	        			}
	        		}
	        		dataWrappers.setData(papers);
	        		if(dataWrapper.getCurrentPage()==-1){
	        			dataWrappers.setCurrentPage(1);
	        		}else{
	        			dataWrappers.setCurrentPage(dataWrapper.getCurrentPage());
	        		}
	    			dataWrappers.setCallStatus(dataWrapper.getCallStatus());
	    			dataWrappers.setNumberPerPage(dataWrapper.getNumberPerPage());
	    			dataWrappers.setTotalNumber(dataWrapper.getTotalNumber());
	    			dataWrappers.setTotalPage(dataWrapper.getTotalPage());
	        	
			} else {
				dataWrappers.setErrorCode(ErrorCodeEnum.User_Not_Logined);
			}
        return dataWrappers;
	}
}
