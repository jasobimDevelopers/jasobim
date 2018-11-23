package com.my.spring.DAO;

import com.my.spring.model.ProjectOutputValue;

public interface ProjectOutputValueDao {
	ProjectOutputValue getById(Long id);
	boolean deleteProjectOutputValue(Long id);
	boolean addProjectOutputValue(ProjectOutputValue role);
	boolean updateProjectOutputValue(ProjectOutputValue dp);
	ProjectOutputValue getByProjectId(Long projectId);
}
