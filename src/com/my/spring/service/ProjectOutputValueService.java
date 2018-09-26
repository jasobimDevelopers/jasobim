package com.my.spring.service;
import com.my.spring.model.ProjectOutputValue;
import com.my.spring.utils.DataWrapper;
public interface ProjectOutputValueService {
	DataWrapper<Void> deleteProjectOutputValueById(String token,Long id);
	DataWrapper<ProjectOutputValue> addProjectOutputValue(String token,ProjectOutputValue role);
	DataWrapper<ProjectOutputValue> getProjectOutputValueById(String token, Long id);
	DataWrapper<Void> updateProjectOutputValue(String token, ProjectOutputValue ProjectOutputValue);
	DataWrapper<ProjectOutputValue> getProjectOutputValueByProjectId(Long projectId, String token);
}
