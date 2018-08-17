package com.my.spring.DAO;

import com.my.spring.model.ProjectTender;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface ProjectTenderDao {
    boolean addProjectTender(ProjectTender ProjectTender);
    boolean deleteProjectTender(Long id);
    boolean updateProjectTender(ProjectTender ProjectTender);
    DataWrapper<List<ProjectTender>> getProjectTenderList();
	boolean deleteProjectTenderByProjectId(Long id);
	ProjectTender getProjectTenderById(Long id);
	DataWrapper<List<ProjectTender>> getProjectTenderByProjectId(Long projectId, Integer pageSize, Integer pageIndex);
}
