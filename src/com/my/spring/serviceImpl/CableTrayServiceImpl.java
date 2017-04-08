package com.my.spring.serviceImpl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.my.spring.DAO.CableTrayDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.CableTray;
import com.my.spring.model.User;
import com.my.spring.service.CableTrayService;
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
@Service("cableTrayService")
public class CableTrayServiceImpl implements CableTrayService {
    @Autowired
    CableTrayDao CableTrayDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FileService fileSerivce;
    @Override
    public DataWrapper<Void> addCableTray(CableTray CableTray,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(CableTray!=null){
					if(!CableTrayDao.addCableTray(CableTray)) 
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
    public DataWrapper<Void> deleteCableTray(Long id,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
				if(id!=null){
					if(!CableTrayDao.deleteCableTray(id)) 
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
    public DataWrapper<Void> updateCableTray(CableTray CableTray,String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			if(userInMemory.getUserType()==UserTypeEnum.Admin.getType()){
 				if(CableTray!=null){
					if(!CableTrayDao.updateCableTray(CableTray)) 
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
    public DataWrapper<List<CableTray>> getCableTrayList() {
        return CableTrayDao.getCableTrayList();
    }

	@Override
	public DataWrapper<CableTray> getCableTrayByProjectId(Long projectId,String token) {
		// TODO Auto-generated method stub
		DataWrapper<CableTray> dataWrapper = new DataWrapper<CableTray>();
        User userInMemory = SessionManager.getSession(token);
        if (userInMemory != null) {
			
				dataWrapper=CableTrayDao.getCableTrayByProjectId(projectId);
			
		}else{
			dataWrapper.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
		return dataWrapper;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean batchImports(String name, MultipartFile file,String token,HttpServletRequest request,Long projectId) {
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
		        ReadExcelOfCableTray readExcel=new ReadExcelOfCableTray();
		        
		        //解析excel，构件信息集合。
		        List<CableTray> CableTrayList = readExcel.getExcelInfo(newFileName ,file,projectId);
		        
		        if(CableTrayList.size()>0){
		        	for(int i=0;i<CableTrayList.size();i++){
		        		String codeInformation=null;
		        		codeInformation=CableTrayList.get(i).getId().toString();
		        		codeInformation="http://139.224.59.3:8080/jasobim/page/CableTrayInfo.html?id="+CableTrayList.get(i).getId().toString();
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
		        		CableTrayList.get(i).setCodeUrl(realPath);
		        	}
		        	
		        	b = true;
		        	fileSerivce.uploadFile(name, file, type, request);
		            //迭代添加构件信息
		        	CableTrayDao.addCableTrayList(CableTrayList);
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
