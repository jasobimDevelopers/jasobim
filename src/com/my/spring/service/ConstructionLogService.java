package com.my.spring.service;

import com.my.spring.model.ConstructionLog;
import com.my.spring.model.ConstructionLogPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;


public interface ConstructionLogService {
    DataWrapper<List<ConstructionLog>> getConstructionLogListByUserId(Long userId,String token);
	DataWrapper<Void> addConstructionLog(ConstructionLog constructionLog, String token,MultipartFile[] files,HttpServletRequest request);
	//DataWrapper<Void> deleteConstructionLog(String id, String token);
	DataWrapper<List<ConstructionLogPojo>> getConstructionLogList(String token, Integer pageIndex, Integer pageSize,
			ConstructionLog ps);
	DataWrapper<Void> updateConstructionLog(ConstructionLog ct, String token, MultipartFile[] files,
			HttpServletRequest request);
	DataWrapper<Void> deleteConstructionLog(Long id, String token);
}
