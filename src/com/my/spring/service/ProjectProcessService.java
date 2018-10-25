package com.my.spring.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.ProjectProcess;
import com.my.spring.model.ProjectProcessPojo;
//import com.my.spring.model.ProjectProcessPojo;
import com.my.spring.utils.DataWrapper;
public interface ProjectProcessService {
	DataWrapper<Void> deleteProjectProcessById(String token,Long id);
	DataWrapper<Void> importProjectProcess(String token,MultipartFile file,HttpServletRequest request,Long projectId);
	DataWrapper<ProjectProcess> addProjectProcess(String token,ProjectProcess role);
	DataWrapper<Void> deleteProjectProcessByIdList(String token,String[] id);
	/*DataWrapper<List<ProjectProcessPojo>> getProjectProcessList(Integer pageIndex, Integer pageSize, ProjectProcess ProjectProcess,
			String token);*/
	DataWrapper<Void> updateProjectProcess(String token, ProjectProcess ProjectProcess);
	DataWrapper<List<ProjectProcessPojo>> getProjectProcessList(Integer pageIndex, Integer pageSize,
			ProjectProcess projectProcess, String token);
}
