package com.my.spring.service;

import com.my.spring.model.Project;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface ProjectService {
    DataWrapper<Void> addProject(Project project, String token, MultipartFile file);
    DataWrapper<Void> updateProject(Project project,String token);
    DataWrapper<List<Project>> getProjectList(String token);
	DataWrapper<Project> getProjectDetailsByAdmin(Long projectId,String token);
	DataWrapper<Void> deleteProject(Long id, String token, Long modelid);
}
