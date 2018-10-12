package com.my.spring.service;

import com.my.spring.model.ProjectTender;
import com.my.spring.model.ProjectTenderPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;
public interface ProjectTenderService {
    DataWrapper<Void> addProjectTender(ProjectTender ProjectTender, String token);
    DataWrapper<Void> deleteProjectTender(Long id,String token);
    DataWrapper<Void> updateProjectTender(ProjectTender ProjectTender,String token);
    DataWrapper<List<ProjectTender>> getProjectTenderList();
	DataWrapper<List<ProjectTenderPojo>> getProjectTenderList(Long projectId, String token, Integer pageSize,
			Integer pageIndex);
	DataWrapper<List<ProjectTenderPojo>> getProjectTendersByProjectId(Long projectId, String token, Integer pageSize,
			Integer pageIndex);
}
