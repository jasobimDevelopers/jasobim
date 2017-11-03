package com.my.spring.service;

import com.my.spring.model.ConstructionTask;
import com.my.spring.model.ConstructionTaskPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;


public interface ConstructionTaskService {
    DataWrapper<List<ConstructionTaskPojo>> getConstructionTaskList(String token, Integer pageIndex, Integer pageSize, ConstructionTask constructionTask, Integer state);
    DataWrapper<List<ConstructionTask>> getConstructionTaskListByUserId(Long userId,String token);
	DataWrapper<Void> addConstructionTask(ConstructionTask constructionTask, String token,MultipartFile[] files,HttpServletRequest request);
	DataWrapper<Void> deleteConstructionTask(Long id, String token);
	DataWrapper<Void> updateConstructionTask(ConstructionTask ct,String token);
	DataWrapper<ConstructionTaskPojo> getConstructionTaskById(Long id, String token, String weixin);
	DataWrapper<String> exportConstructionTaskById(Long id, String token);
}
