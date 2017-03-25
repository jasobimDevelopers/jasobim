package com.my.spring.serviceImpl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.my.spring.DAO.DuctDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.Duct;
import com.my.spring.model.User;
import com.my.spring.service.DuctService;
import com.my.spring.service.FileService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.MD5Util;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("ductService")
public class DuctServiceImpl implements DuctService {
    @Autowired
    DuctDao DuctDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileService fileSerivce;
    @Override
    public DataWrapper<Void> addDuct(Duct Duct,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(Duct!=null){
					if(!DuctDao.addDuct(Duct)) 
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
    public DataWrapper<Void> deleteDuct(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!DuctDao.deleteDuct(id)) 
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
    public DataWrapper<Void> updateDuct(Duct Duct,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
 				if(Duct!=null){
					if(!DuctDao.updateDuct(Duct)) 
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
    public DataWrapper<List<Duct>> getDuctList() {
        return DuctDao.getDuctList();
    }

	@Override
	public DataWrapper<Duct> getDuctByProjectId(Long projectId,String token) {
		// TODO Auto-generated method stub
		DataWrapper<Duct> dataWrapper = new DataWrapper<Duct>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			
				dataWrapper=DuctDao.getDuctByProjectId(projectId);
			
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
	@Override
	public boolean batchImport(String name, MultipartFile file,String token,HttpServletRequest request,Long projectId) {
		if (file == null || name == null || name.equals("")) {
			return false;
		}
		int type=7;
		DataWrapper<Void> dataWrapper=new DataWrapper<Void>();
		boolean b = false;
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
		        String newFileName = MD5Util.getMD5String(file.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
		                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		        //创建处理EXCEL
		        ReadExcelOfDuct readExcel=new ReadExcelOfDuct();
		        
		        //解析excel，构件信息集合。
		        List<Duct> DuctList = readExcel.getExcelInfo(newFileName ,file,projectId);
		        
		        if(DuctList.size()>0){
		        	for(int i=0;i<DuctList.size();i++){
		        		String codeInformation=null;
		        		codeInformation=DuctList.get(i).getId().toString();
		        		codeInformation="http://139.224.59.3:8080/jasobim/page/ductInfo.html?id="+DuctList.get(i).getId().toString();
		        		SimpleDateFormat sdf =   new SimpleDateFormat("yyyyMMddHHmmssSSS" );
		        	   	Date d=new Date();
		        	   	String str=sdf.format(d);
		        	   	String rootPath = request.getSession().getServletContext().getRealPath("/");
		        	   	String filePath="/codeFiles";
		        	   	String imgpath=rootPath+filePath;
		        	   	String realPath=rootPath+filePath+"/"+str+".png";
		        		try{
		        		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		        	        @SuppressWarnings("rawtypes")
		        			Map hints = new HashMap();  
		        	        //内容所使用编码  
		        	        hints.put(EncodeHintType.CHARACTER_SET, "utf8");  
		        	        BitMatrix bitMatrix = multiFormatWriter.encode(codeInformation,BarcodeFormat.QR_CODE, 200, 200, hints);  
		        	        //生成二维码  
		        	        File outputFile = new File(imgpath,str+".png"); 
		        	        
		        	        MatrixToImageWriter.writeToFile(bitMatrix, "png", outputFile);  
		        		} catch (Exception e) {
		        			e.printStackTrace();
		        		}
		        		DuctList.get(i).setCodeUrl(realPath);
		        	}
		        	
		        	b = true;
		        	fileSerivce.uploadFile(name, file, type, request);
		            //迭代添加构件信息
		        	DuctDao.addDuctList(DuctList);
		        }
		      
			}else{
				dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_Error);
			}
			
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return b;
	}
}
