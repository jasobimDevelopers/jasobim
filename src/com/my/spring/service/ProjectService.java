package com.my.spring.service;

import com.my.spring.model.Project;
import com.my.spring.model.ProjectPojo;
import com.my.spring.utils.DataWrapper;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface ProjectService {
    DataWrapper<ProjectPojo> addProject(Project project, String token, MultipartFile[] file,MultipartFile[] picfile, HttpServletRequest request);
    //DataWrapper<List<Project>> findProjectLike(Project project,String token);
	DataWrapper<ProjectPojo> getProjectDetailsByAdmin(Long projectId,String token);
	DataWrapper<Void> deleteProject(Long id, String token, HttpServletRequest request);
	DataWrapper<Void> updateProject(Project project, String token, MultipartFile[] modelFile, MultipartFile[] picFile,
			HttpServletRequest request);
	DataWrapper<Void> updateWorkHour(Long projectId, String token, Integer workHour);
	DataWrapper<String> getProjectHour(Long projectId, String token);
	DataWrapper<List<ProjectPojo>> getProjectList(Integer pageIndex, Integer pageSize, Project project, String token,
			String content, HttpServletRequest request) throws IOException;
}
