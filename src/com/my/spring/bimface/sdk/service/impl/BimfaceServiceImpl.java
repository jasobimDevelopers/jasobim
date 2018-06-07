package com.my.spring.bimface.sdk.service.impl;

import com.bimface.sdk.bean.request.FileUploadRequest;
import com.bimface.sdk.bean.response.FileBean;
import com.bimface.sdk.bean.response.TranslateBean;
import com.my.spring.bimface.sdk.config.Config;
import com.bimface.sdk.exception.BimfaceException;
import com.bimface.sdk.service.UploadService;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.bimface.sdk.BimfaceClient;
import com.my.spring.bimface.sdk.service.BimfaceService;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Project;
import com.my.spring.service.FileService;
import com.my.spring.utils.DataWrapper;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class BimfaceServiceImpl implements BimfaceService {
	BimfaceClient bimfaceClient;
	Config config;
	@Autowired
	FileService filesService;
	@Autowired
	ProjectDao projectDao;
	@Override
	public DataWrapper<String> getViewTokenByFileId(Long fileId) {
		DataWrapper<String> test = new DataWrapper<String>();
		bimfaceClient = new BimfaceClient(config.APP_KEY,config.APP_SECRET);
		String str="";
		try {
			str = bimfaceClient.getViewTokenByFileId(fileId);
			test.setData(str);
		} catch (BimfaceException e) {
			test.setErrorCode(ErrorCodeEnum.Error);
			e.printStackTrace();
		}
		return test;
	}
	@Override
	public DataWrapper<Long> uploadModelFile(MultipartFile file,HttpServletRequest requestion,String token,Long projectId) {
		DataWrapper<Long> test = new DataWrapper<Long>();
		String filepath="bimface/file";
		bimfaceClient = new BimfaceClient(config.APP_KEY,config.APP_SECRET);
		Files files=filesService.uploadFile(filepath, file, 0, requestion);
		FileUploadRequest fileUploadRequest = new FileUploadRequest();
		fileUploadRequest.setUrl("http://jasobim.com:8080/"+files.getUrl());
		fileUploadRequest.setName(files.getName());
		FileBean fileBean=new FileBean();
		try {
			fileBean=bimfaceClient.upload(fileUploadRequest);
		} catch (BimfaceException e1) {
			e1.printStackTrace();
		}
		String status=fileBean.getStatus();
		  // 获取fileId
        Long fileId = fileBean.getFileId();
        if(fileId!=null){
        	Project project = projectDao.getById(projectId);
        	if(project!=null){
        		project.setModeFileId(fileId.toString());
            	projectDao.updateProject(project);
        	}else{
        		test.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
        	}
        }
        test.setData(fileId);
        // 发起文件转换
        TranslateBean translateBean = null;
        try {
            translateBean = bimfaceClient.translate(fileId);
        } catch (BimfaceException e) {
        }
		return test;
	}

	@Override
	public DataWrapper<String> getModeViewTokenByIntegrateId(Long integrateId, HttpServletRequest request, String token,
			Long projectId) {
				DataWrapper<String> test = new DataWrapper<String>();
				bimfaceClient = new BimfaceClient(config.APP_KEY,config.APP_SECRET);
				String str="";
				try {
					str = bimfaceClient.getViewTokenByIntegrateId(integrateId);
					test.setData(str);
				} catch (BimfaceException e) {
					test.setErrorCode(ErrorCodeEnum.Error);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return test;
	}


   
   


}
