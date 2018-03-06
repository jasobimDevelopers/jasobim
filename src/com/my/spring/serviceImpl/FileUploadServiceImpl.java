package com.my.spring.serviceImpl;


import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.User;
import com.my.spring.service.FileService;
import com.my.spring.service.FileUploadService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	FileService fileSerivce;

	@Override
	public DataWrapper<List<String>> batchImport(String name, MultipartFile[] file,String token,HttpServletRequest request) {
		DataWrapper<List<String>> urllist= new DataWrapper<List<String>>();
		List<String> temp = new ArrayList<String>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if (file != null && file.length>0) {
				name=name+"/"+userInMemory.getId();
				for(int i=0;i<file.length;i++){
					Files files=fileSerivce.uploadFile(name, file[i], 8, request);
					if(files!=null){
        			   	String urlReal=files.getUrl();
						temp.add(urlReal);
					}else{
						temp.add(file[i].getOriginalFilename()+" format error");
					}
				}
				urllist.setData(temp);
			}
		}else{
			urllist.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return urllist;
	}
}
