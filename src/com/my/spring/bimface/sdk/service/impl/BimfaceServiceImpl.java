package com.my.spring.bimface.sdk.service.impl;

import com.bimface.sdk.bean.request.FileUploadRequest;
import com.bimface.sdk.bean.response.CategoryBean;
import com.bimface.sdk.bean.response.FileBean;
import com.bimface.sdk.bean.response.TranslateBean;
import com.my.spring.bimface.sdk.config.Config;
import com.bimface.sdk.exception.BimfaceException;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.bimface.sdk.BimfaceClient;
import com.my.spring.bimface.sdk.service.BimfaceService;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Project;
import com.my.spring.service.FileService;
import com.my.spring.utils.DataWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class BimfaceServiceImpl implements BimfaceService {
	
	Config config = new Config();
	@Autowired
	FileService filesService;
	@Autowired
	ProjectDao projectDao;
	@Override
	public DataWrapper<String> getViewTokenByFileId(Long fileId) {
		DataWrapper<String> test = new DataWrapper<String>();
		BimfaceClient bimfaceClient =new BimfaceClient(config.getAppKey(),config.getAppSecret());
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
	public DataWrapper<Long> uploadModelFile(MultipartFile file,HttpServletRequest requestion,String token,Long projectId,Integer modeType) {
		DataWrapper<Long> test = new DataWrapper<Long>();
		
		String filepath="bimface/file";
		Files files=filesService.uploadFile(filepath, file, 0, requestion);
		FileUploadRequest fileUploadRequest = new FileUploadRequest();
		fileUploadRequest.setUrl("http://jasobim.com:8080/"+files.getUrl());
		fileUploadRequest.setName(files.getName());
		FileBean fileBean=new FileBean();
		Config configs = new Config();
		BimfaceClient bimfaceClient =new BimfaceClient(configs.getAppKey(),configs.getAppSecret());
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
        		project.setModelType(modeType);
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
				Config configs = new Config();
				String str="";
				try {
					BimfaceClient bimfaceClient =new BimfaceClient(configs.getAppKey(),configs.getAppSecret());
					str = bimfaceClient.getViewTokenByIntegrateId(integrateId);
					test.setData(str);
				} catch (BimfaceException e) {
					test.setErrorCode(ErrorCodeEnum.Error);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return test;
	}
	@Override
	public DataWrapper<List<CategoryBean>> getCategory(Long fileId, String token, Long projectId) {
		DataWrapper<List<CategoryBean>> result = new DataWrapper<List<CategoryBean>>();
		List<CategoryBean> getResult = new ArrayList<CategoryBean>();
		try {
			BimfaceClient bimfaceClient =new BimfaceClient(config.getAppKey(),config.getAppSecret());
			getResult = bimfaceClient.getCategory(fileId);
			result.setData(getResult);
		} catch (BimfaceException e) {
			result.setErrorCode(ErrorCodeEnum.Error);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}


   
   


}
